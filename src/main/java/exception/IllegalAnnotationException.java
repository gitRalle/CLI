package exception;

/**
 * Class represents an exception to be thrown by the config.Configuration class in the event of
 * a processed annotated element not meeting certain requirements.
 */
public class IllegalAnnotationException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public IllegalAnnotationException(String message) {
        super(message);
    }
}
