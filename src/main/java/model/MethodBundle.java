package model;

import java.lang.reflect.Method;

/**
 * This class bundles relevant information pertaining to a {@link java.lang.reflect.Method},<br>
 * such as the method itself, the instantiated class on which it acts, and information pertaining to its
 * parameters in the form of an array of {@link model.ParamBundle} objects.
 */
public class MethodBundle {

    private final Object object;

    private final Method method;

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
     * Returns this class's <code>Method</code> field.
     * @return the <code>Method</code> field associated with this class.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Returns this class's <code>ParamBundle</code> field.
     * @return the <code>ParamBundle</code> field associated with this class.
     */
    public ParamBundle[] getParams() {
        return params;
    }

    /**
     * Returns this class's <code>Object</code> field.
     * @return the <code>Object</code> field associated with this class.
     */
    public Object getObject() {
        return object;
    }
}
