package data;

import java.lang.reflect.Type;

public class ParamBundle {

    private final Type type;
    private final String name;

    public ParamBundle(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
