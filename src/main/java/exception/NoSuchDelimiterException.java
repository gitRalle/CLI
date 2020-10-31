package exception;

/**
 * Thrown to indicate a string delimiter not being present as a substring in another string.
 */
public class NoSuchDelimiterException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public NoSuchDelimiterException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
