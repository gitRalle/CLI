package model;

import config.MethodInvocationState;

/**
 * This abstract class can be used as a base class for any controller annotated classes,
 * to gain access to its invocation state object.
 */
public abstract class BaseController {

    /**
     * This class's invocation state field.
     */
    private final MethodInvocationState invokeState;

    /**
     * Constructs new a BaseController object and initializes its invocation state field.
     */
    public BaseController() {
        invokeState = new MethodInvocationState();
    }

    /**
     * Returns this class's invocation state object.
     *
     * @return this class's invocation state object.
     */
    public MethodInvocationState invokeState() {
        return invokeState;
    }
}
