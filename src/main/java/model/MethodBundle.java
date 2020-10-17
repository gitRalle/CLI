package model;

import java.lang.reflect.Method;

/**
 * This class is responsible for bundling relevant information pertaining to an instance method.
 */
public class MethodBundle {

    /**
     * The class to which the method belong.
     */
    private final Object object;

    /**
     * The method itself.
     */
    private final Method method;

    /**
     * The method's parameters.
     */
    private final ParamBundle[] params;

    /**
     * Constructs a new methodBundle object using the specified object, method, and paramBundle.
     *
     * @param object the object to which the method belongs to.
     * @param method the method.
     * @param params the method's parameters.
     */
    public MethodBundle(Object object, Method method, ParamBundle[] params) {
        this.object = object;
        this.method = method;
        this.params = params;
    }

    /**
     * Returns this class's method.
     * @return this class's method.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Returns this class's paramBundle.
     * @return this class's paramBundle.
     */
    public ParamBundle[] getParams() {
        return params;
    }

    /**
     * Returns this class's object.
     * @return this class's object.
     */
    public Object getObject() {
        return object;
    }
}
