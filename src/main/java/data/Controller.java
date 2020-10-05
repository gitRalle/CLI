package data;

/**
 * <summary>Abstract Controller Class.</summary>
 */
public abstract class Controller {

    /**
     * The InvokeState field.
     */
    private final InvokeState invokeState;

    /**
     * Default Constructor, initializes the InvokeState field,
     */
    public Controller() {
        invokeState = new InvokeState();
    }

    /**
     * Returns the InvokeState instance.
     *
     * @return the InvokeState instance.
     */
    public final InvokeState invokeState() {
        return invokeState;
    }
}
