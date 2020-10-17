package config;

import exception.ParseException;

import java.util.*;

/**
 * This class is used for the storing and querying of exceptions thrown during the most recent
 * reflective invocation of a command annotated method.
 */
public final class MethodInvocationState {

    /**
     * This arg initialization state map stores all of the potential exceptions thrown during the most recent
     * reflective invocation of a command annotated method.
     */
    private final HashMap<String, ArgumentInitializationState> argInitStateMap;

    /**
     * Constructs a new object and initialises its arg initialization state map with default map settings.
     */
    public MethodInvocationState() {
        argInitStateMap = new HashMap<>(16, 0.75f);
    }

    /**
     * Determines whether any ParseExceptions were NOT thrown during the the most recent
     * invocation of a command annotated method.
     *
     * @return true if there were no ParseExceptions thrown during the most recent invocation of a
     * command annotated method, or false if there were one or more ParseExceptions thrown during the
     * most recent invocation of a command annotated method.
     */
    public boolean wasSuccessful() {
        return argInitStateMap.values().stream().noneMatch(arg -> arg.exceptions().stream()
                .anyMatch(ex -> ex instanceof ParseException));
    }

    /**
     * Returns the initialization state value associated with the specified argName.
     *
     * @param argName the key associated with the initialization state value.
     * @return the initialization state value associated with the specified argName,
     * if no initialization state value is associated with the specified argName,
     * a new initialization state object with zero exceptions is constructed and returned.
     */
    public ArgumentInitializationState initializationOf(String argName) {
        if (!argInitStateMap.containsKey(argName)) {
            return new ArgumentInitializationState();
        }
        return argInitStateMap.get(argName);
    }

    /**
     * Adds the specified exception to the list associated with the specified argName.
     *
     * @param argName the name or keyword of an argument belonging to the most recently invoked command annotated method.
     * @param ex the exception to add to the list associated with the specified argName.
     * @return the invokeState object.
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