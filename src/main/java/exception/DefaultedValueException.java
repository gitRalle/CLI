package exception;

import java.lang.reflect.Method;
/**
 * Thrown to indicate a process having to default the value of one of the parameters of a soon to be reflectively invoked
 * {@link java.lang.reflect.Method}.
 */
public class DefaultedValueException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public DefaultedValueException(String message) {
        super(message);
    }
}
