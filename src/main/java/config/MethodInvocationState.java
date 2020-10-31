package config;

import exception.ParseException;
import annotation.Command;

import java.util.*;

/**
 * This class is used for the querying and storing of any manageable <code>Exceptions</code> thrown during the most recent
 * invocation of a {@link Command} annotated method.
 */
public final class MethodInvocationState {

    /**
     * This arg initialization state map stores all of the potential <code>Exceptions</code> thrown during the most recent
     * invocation of a <code>Command</code> annotated method.
     */
    private final HashMap<String, ArgumentInitializationState> argInitStateMap;

    /**
     * Constructs a new object and initialises its arg initialization state map with default map settings.
     */
    public MethodInvocationState() {
        argInitStateMap = new HashMap<>(16, 0.75f);
    }

    /**
     * Determines whether any {@link ParseException} were <b>NOT</b> thrown during the the most recent
     * invocation of a {@link Command} annotated method.
     *
     * @return <code>true</code> if there were no <code>ParseExceptions</code> thrown during the most recent
     * invocation of a <code>Command</code> annotated method, or<br>
     * <code>false</code> if there were one or more <code>ParseExceptions</code> thrown during the most
     * recent invocation of a <code>Command</code> annotated method.
     */
    public boolean wasSuccessful() {
        return argInitStateMap.values().stream().noneMatch(arg -> arg.exceptions().stream()
                .anyMatch(ex -> ex instanceof ParseException));
    }

    /**
     * Returns the arg initialization state value associated with the specified argName.
     *
     * @param argName the key associated with the desired arg initialization state value.
     * @return the arg initialization state value associated with the specified argName,
     * if no arg initialization state value is associated with the specified argName,
     * a new arg initialization state object with zero <code>Exceptions</code> is constructed and returned.
     */
    public ArgumentInitializationState initializationOf(String argName) {
        if (!argInitStateMap.containsKey(argName)) {
            return new ArgumentInitializationState();
        }
        return argInitStateMap.get(argName);
    }

    /**
     * Adds the specified <code>Exception</code> to the list associated with the specified argName.
     *
     * @param argName the name or keyword of an argument belonging to the most recently invoked <code>Command</code>
     * annotated method.
     * @param ex the <code>Exception</code> to add to the list associated with the specified argName.
     * @return this <code>MethodInvocationState</code> object.
     */
    protected MethodInvocationState append(String argName, Exception ex) {
        if (argInitStateMap.containsKey(argName)) {
            argInitStateMap.get(argName).exceptions().add(ex);
        }
        else {
            argInitStateMap.put(argName, new ArgumentInitializationState(ex));
        }
        return this;
    }

    /**
     * Removes all of the mappings from this class's state initialization map.
     * The map will be empty after this call returns.
     */
    protected void clear() {
        argInitStateMap.clear();
    }
}