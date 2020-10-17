package config;

import exception.DefaultedValueException;
import exception.ParseException;

import java.util.LinkedList;

/**
 * This class represents the state of a reflectively initialized argument belonging to
 * the most recently reflectively invoked command annotated method.
 */
public final class ArgumentInitializationState {

    /**
     * Any exceptions thrown during the initialization of the pertaining
     * argument are stored in this exceptions map.
     */
    private final LinkedList<Exception> exceptions;

    /**
     * Constructs a new object with an empty exceptions list.
     */
    protected ArgumentInitializationState() {
        exceptions = new LinkedList<>();
    }

    /**
     * Constructs a new object and initializes its exceptions list,
     * and adds the specified exception to the list.
     *
     * @param ex the exception to be added to this class's exceptions list.
     */
    protected ArgumentInitializationState(Exception ex) {
        exceptions = new LinkedList<>() {{add(ex);}};
    }

    /**
     * Evaluates and determines whether any DefaultedValueExceptions were thrown during initialization of this
     * argument.
     *
     * @return true in the event of a thrown DefaultedValueException during initialisation,
     * false if a DefaultedValueException was not thrown during initialization.
     */
    public boolean wasDefaulted()
    {
        return exceptions.stream().anyMatch(ex -> ex instanceof DefaultedValueException);
    }

    /**
     * Evaluates and determines whether any a ParseException was NOT thrown during initialization of this
     * argument.
     *
     * @return true if no ParseExceptions were thrown during initialization,
     * false in the event of a thrown ParsException during initialization.
     */
    public boolean wasSuccessful()
    {
        return exceptions.stream().noneMatch(ex -> ex instanceof ParseException);
    }

    /**
     * GET method for this class's exceptions list.
     * @return the exception list.
     */
    protected LinkedList<Exception> exceptions() {
        return exceptions;
    }

}
