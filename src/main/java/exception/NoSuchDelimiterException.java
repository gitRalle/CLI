package exception;

/**
 * This class represents an exception to be thrown by the util.StringUtils.splitAfterFirst() method in the event of
 * a parameterized string delimiter input not being present as a substring in another parameterized string input.
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
