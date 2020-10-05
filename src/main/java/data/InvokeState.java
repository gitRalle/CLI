package data;

import exception.DefaultedValueException;
import exception.ParseException;

import java.util.*;
import java.util.Map;

// accessed by data.Controller, config.Configuration, controller.*
/**
 * <summary>Class is responsible for saving and querying information, gathered from the possible
 * Exceptions thrown, during the most recent invocation of a method annotated with @Command.</summary>
 */
public class InvokeState {

    /**
     * <summary>A Map which holds all of the exceptions thrown during the most recent
     * invocation of a method annotated with @Command.</summary>
     */
    private final Map<String, LinkedList<Exception>> exceptions;

    // accessed only by data.Controller.
    /**
     * Default Constructor, which initializes the exceptions Map with default settings.
     */
    InvokeState() {
        exceptions = new HashMap<>(16, 0.75f);
    }

    // accessed by controller.*
    /**
     * <summary>Determines whether any ParseExceptions were not thrown during the the most recent
     * invocation of a method annotated with @Command</summary>
     *
     * @return a boolean value; True for not thrown, or False for thrown.
     */
    public boolean successful() {
        return exceptions.entrySet().stream().noneMatch(kv -> kv.getValue().stream()
                .anyMatch(ex -> ex instanceof ParseException));
    }

    // accessed by controller.*
    /**
     * <summary>Determines whether a DefaultedValueException was thrown during initialization of an
     * argument, belonging to the most recently invoked method annotated with @Command.</summary>
     *
     * @param argName the name/keyword of the argument.
     * @return a boolean value; True if the initialization of the specified argument did indeed
     * throw a DefaultedValueException, False if not.
     */
    public boolean defaulted(String argName)  {
        return exceptions.get(argName) != null &&
                exceptions.get(argName).stream().anyMatch(ex -> ex instanceof DefaultedValueException);
    }

    // accessed only by config.Configuration.
    /**
     * <summary>Appends an Exception to the underlying Map.</summary>
     *
     * @param argName the key to use
     *                (which is the name of an argument belonging to the most recently invoked method annotated
     *                with @Command).
     * @param ex: the exception to be appended.
     * @return the InvokeState object on which this method was called.
     */
    public InvokeState append(String argName, Exception ex) {
        if (exceptions.containsKey(argName)) {
            exceptions.get(argName).add(ex);
        }
        else {
            exceptions.put(argName, new LinkedList<>(){{add(ex);}});
        }
        return this;
    }

    // accessed only by config.Configuration.
    /**
     * <summary>Clears the underlying Map of any Exceptions.</summary>
     */
    public void clear() {
        exceptions.clear();
    }
}
