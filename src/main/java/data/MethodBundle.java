package data;

import java.lang.reflect.Method;

/**
 * <summary>Class is responsible for bundling relevant information pertaining to an instance of the
 * Method.class.</summary>
 */
public class MethodBundle {

    /**
     * <summary>The Object to which the Method belongs to.</summary>
     */
    private final Object object;

    /**
     * <summary>The Method.</summary>
     */
    private final Method method;

    /**
     * <summary>The Method's Parameters,
     * in the structure of an array of ParamBundle Objects.</summary>
     */
    private final ParamBundle[] params;

    /**
     * Constructor.
     *
     * @param object the Object to which the Method belongs to.
     * @param method the Method.
     * @param params the Method's Parameters.
     */
    public MethodBundle(Object object, Method method, ParamBundle[] params) {
        this.object = object;
        this.method = method;
        this.params = params;
    }

    /**
     * <summary>Returns the Method instance passed to the Constructor. </summary>
     *
     * @return the Method instance.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * <summary>Returns the ParamBundle instance passed to the Constructor.</summary>
     *
     * @return the ParamBundle instance.
     */
    public ParamBundle[] getParams() {
        return params;
    }

    /**
     * <summary>Returns the Object instance passed to the Constructor</summary>.
     *
     * @return the Object instance.
     */
    public Object getObject() {
        return object;
    }
}
