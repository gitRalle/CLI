package config;

import exception.DefaultedValueException;
import exception.ParseException;
import annotation.Command;

import java.util.LinkedList;

/**
 * This class represents the state of a reflectively initialized argument belonging to
 * the most recently reflectively invoked {@link Command} annotated method.
 */
public final class ArgumentInitializationState {

    /**
     * Any <code>Exceptions</code> thrown during the initialization of the
     * argument are stored in this list of exceptions.
     */
    private final LinkedList<Exception> exceptions;

    /**
     * Constructs a new object with an empty list of exceptions, with default list settings.
     */
    protected ArgumentInitializationState() {
        exceptions = new LinkedList<>();
    }

    /**
     * Constructs a new object and initializes its list of exceptions field with default list settings,<br>
     * and adds the specified <code>Exception</code> to the list.
     *
     * @param ex the <code>Exception</code> to be added to this object's newly initialized exceptions list.
     */
    protected ArgumentInitializationState(Exception ex) {
        exceptions = new LinkedList<>() {{add(ex);}};
    }

    /**
     * Evaluates and determines whether a {@link DefaultedValueException} was thrown during
     * the initialization of the argument.
     *
     * @return <code>true</code> in the event of a thrown <code>DefaultedValueException</code> during the initialization
     * of the argument,<br>
     * <code>false</code> if a <code>DefaultedValueException</code> was not thrown during the initialization of
     * the argument.
     */
    public final boolean wasDefaulted()
    {
        return exceptions.stream().anyMatch(ex -> ex instanceof DefaultedValueException);
    }

    /**
     * Evaluates and determines whether a {@link ParseException} was <b>NOT</b> thrown during the initialization of
     * the argument.
     *
     * @return <code>true</code> if no <code>ParseException</code> was thrown during the initialization of the argument,<br>
     * <code>false</code> in the event of a thrown <code>ParsException</code> during the initialization of the argument.
     */
    public final boolean wasSuccessful()
    {
        return exceptions.stream().noneMatch(ex -> ex instanceof ParseException);
    }

    /**
     * Returns this class's list of exceptions field.
     * @return the list of exceptions field associated with this class.
     */
    protected final LinkedList<Exception> exceptions() {
        return exceptions;
    }

}
