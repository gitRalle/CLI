package exception;

import java.lang.reflect.Type;
/**
 * Thrown to indicate a failure in the parsing of a {@link java.lang.String} into a {@link java.lang.reflect.Type}.
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
