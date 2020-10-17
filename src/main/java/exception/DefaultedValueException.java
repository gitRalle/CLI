package exception;

/**
 * Class represents an exception to be thrown in the event of the config.Configuration class
 * forcefully having to assign a default value to a primitive datatype when in the process of invoking a method
 * reflectively.
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
