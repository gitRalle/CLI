package data;

import exception.DefaultedValueException;
import exception.ParseException;

import java.util.*;
import java.util.Map;

public class InvokeState {

    private final Map<String, LinkedList<Exception>> exceptions;

    public InvokeState() {
        exceptions = new HashMap<>(16, 0.75f);
    }

    /**
     * <summary>Determines whether any ParseExceptions were thrown during the the most recent
     * invocation of a method annotated with @Command</summary>
     *
     * @return a boolean value.
     */
    public boolean successful() {
        return exceptions.entrySet().stream().noneMatch(kv -> kv.getValue().stream()
                .anyMatch(ex -> ex instanceof ParseException));
    }

    /**
     * <summary>Determines whether a DefaultedValueException was thrown during initialization of an
     * argument, belonging to the most recently invoked method annotated with @Command.</summary>
     *
     * @param argName the name/keyword of the argument.
     * @return a boolean value.
     */
    public boolean defaulted(String argName)  {
        return exceptions.get(argName) != null &&
                exceptions.get(argName).stream().anyMatch(ex -> ex instanceof DefaultedValueException);
    }

    /**
     * <summary>Appends an Exception to the underlying Map.</summary>
     *
     * @param argName the key to use.
     * @param ex: the exception to append.
     * @return the InvokeState object on which append was called.
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

    /**
     * <summary>Clears the underlying Map of any Exceptions.</summary>
     *
     */
    public void clear() {
        exceptions.clear();
    }
}
