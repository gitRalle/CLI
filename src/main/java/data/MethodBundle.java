package data;

import java.lang.reflect.Method;

public class MethodBundle {

    private final Object object;
    private final Method method;
    private final ParamBundle[] params;

    public MethodBundle(Object object, Method method, ParamBundle[] params) {
        this.object = object;
        this.method = method;
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public ParamBundle[] getParams() {
        return params;
    }

    public Object getObject() {
        return object;
    }
}
