package exception;

import java.util.Set;

/**
 * Thrown to indicate a processed set of annotation elements not meeting a certain set of requirements.
 * @see config.Configuration#process(Set)
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
