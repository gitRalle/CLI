package exception;

/**
 * This class represents an exception to be thrown by the util.ObjectUtils.toObject() method in the event of
 * a failure in parsing a parameterized string input into a parameterized type.
 */
public class ParseException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public ParseException(String errorMessage) {
        super(errorMessage);
    }
}
