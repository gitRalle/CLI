package model;

import java.lang.reflect.Type;
import java.lang.reflect.Parameter;

/**
 * This class bundles relevant information pertaining to a {@link java.lang.reflect.Parameter},<br>
 * such as the {@link java.lang.reflect.Type} of the parameter, and the relevant name of the parameter.
 */
public class ParamBundle {

    private final Type type;

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
     * Returns this class's <code>Type</code> field.
     * @return the <code>Type</code> field associated with this class.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns this class's name field.
     * @return the name field associated with this class.
     */
    public String getName() {
        return name;
    }
}
