package model;

import annotation.Controller;
import config.MethodInvocationState;

/**
 * This abstract class may be used as a base class for any {@link Controller} annotated classes,
 * to gain access to this class's {@link MethodInvocationState} object.
 */
public abstract class AbstractController {

    private final MethodInvocationState invokeState;

    /**
     * Constructs a new object and initializes it's {@link MethodInvocationState} field.
     */
    public AbstractController() {
        invokeState = new MethodInvocationState();
    }

    /**
     * Returns this class's <code>MethodInvocationState</code> field.
     *
     * @return the <code>MethodInvocationState</code> field associated with this class.
     */
    public MethodInvocationState invokeState() {
        return invokeState;
    }
}
