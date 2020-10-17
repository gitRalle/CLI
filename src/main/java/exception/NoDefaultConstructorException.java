package exception;

/**
 * This class represents an exception to be thrown by the config.ConfigurationBuilder class in the event of a processed
 * annotation element not having a default/un-parameterized constructor.
 */
public class NoDefaultConstructorException extends Exception {

    /**
     * Constructs a new exception with null as its detail message.
     */
    public NoDefaultConstructorException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public NoDefaultConstructorException(String message)
    {
        super(message);
    }
}
