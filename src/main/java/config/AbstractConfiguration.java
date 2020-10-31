package config;

import exception.IllegalAnnotationException;
import iface.IConsole;
import annotation.Controller;

import java.util.Set;

/**
 * Abstract class for populating a {@link ReflectionMap} by processing a set of elements annotated
 * with the annotations declared in the {@link annotation} package.
 */
public abstract class AbstractConfiguration
{
    private final ReflectionMap reflectionMap;

    private final IConsole console;

    /**
     * Constructs a new object and initializes this class's IConsole field and {@link config.ReflectionMap} field.
     * @param console class which implements the IConsole interface.
     */
    protected AbstractConfiguration(IConsole console)
    {
        this.console = console;
        this.reflectionMap = new ReflectionMap();
    }

    /**
     * Unsupported Constructor.
     */
    protected AbstractConfiguration()
    {
        throw new UnsupportedOperationException(
                "this constructor is not available."
        );
    }

    /**
     * Abstract method for processing a set of {@link Controller} annotated types.
     *
     * @param annotatedTypes the set of annotated types.
     * @throws IllegalAnnotationException in the event of an invalid set of annotations.
     */
    protected abstract void process(Set<Object> annotatedTypes) throws IllegalAnnotationException;

    /**
     * Returns this class's <code>ReflectionMap</code> field.
     * @return the <code>ReflectionMap</code> field associated with this class.
     */
    public final ReflectionMap map()
    {
        return reflectionMap;
    }

    /**
     * Returns this class's <code>IConsole</code> field.
     * @return the <code>IConsole</code> field associated with this class.
     */
    public final IConsole console()
    {
        return console;
    }
}
