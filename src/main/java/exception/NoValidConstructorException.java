package exception;

/**
 * Thrown to indicate a processed annotation element not having a default/un-parameterized constructor,
 * and not having a constructor which takes as its only parameter,
 * an instance of a class which implements the {@link iface.IConsole} interface.
 */
public class NoValidConstructorException extends Exception {

    /**
     * Constructs a new exception with null as its detail message.
     */
    public NoValidConstructorException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public NoValidConstructorException(String message)
    {
        super(message);
    }
}
