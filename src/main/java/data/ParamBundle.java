package data;

import java.lang.reflect.Type;

/**
 * <summary>Class is responsible for bundling relevant information pertaining to an instance of the
 * Parameter.class.</summary>
 */
public class ParamBundle {

    /**
     * <summary>The Type of the Parameter.</summary>
     */
    private final Type type;

    /**
     * <summary>The relevant name to save.</summary>
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param type the Type of the Parameter.
     * @param name the name to save.
     */
    public ParamBundle(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * <summary>Returns the Type instance passed to the Constructor.</summary>
     *
     * @return the Type instance.
     */
    public Type getType() {
        return type;
    }

    /**
     * <summary>Returns the String instance passed to the Constructor.</summary>
     *
     * @return the String instance.
     */
    public String getName() {
        return name;
    }
}
