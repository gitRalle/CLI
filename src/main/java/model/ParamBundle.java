package model;

import java.lang.reflect.Type;

/**
 * This class is responsible for bundling relevant information pertaining to a parameter of an instance method.
 */
public class ParamBundle {

    /**
     * The type of the parameter.
     */
    private final Type type;

    /**
     * The name/keyword of the parameter.
     */
    private final String name;

    /**
     * Constructs a new paramBundle object using the specified type, and name.
     *
     * @param type the type of the parameter.
     * @param name the name/keyword of the parameter.
     */
    public ParamBundle(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Returns the type of the parameter.
     * @return the type of the parameter.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the name/keyword of the parameter.
     * @return the name/keyword of the parameter.
     */
    public String getName() {
        return name;
    }
}
