package config;

import annotation.*;
import exception.IllegalAnnotationException;
import exception.NoValidConstructorException;
import iface.IConsole;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class when built scans for {@link Controller} annotated types, instantiates them, and
 * initiates the population of this class's {@link AbstractConfiguration} field's {@link ReflectionMap}
 * by calling it's {@link AbstractConfiguration#process(Set)} method.
 */
public final class Builder
{

    private final AbstractConfiguration config;

    /**
     * Constructs a new <code>Builder</code> object and initializes its {@link Configuration} field by
     * passing the specified <code>IConsole</code> object to it's constructor.
     * @param console a class which implements the <code>IConsole</code> interface.
     */
    public Builder(IConsole console)
    {
        config = new Configuration(console);
    }

    /**
     * Unsupported constructor.
     */
    protected Builder()
    {
        throw new UnsupportedOperationException(
                "do not use this constructor."
        );
    }

    /**
     * Builds the configuration by scanning the entire class path for {@link Controller} annotated classes.
     *
     * @return this <code>ConfigurationBuilder</code> object.
     *
     * @throws IllegalAnnotationException in the event of a scanned set of annotated classes having an invalid set of
     * {@link Controller} annotations present, and/or declared methods with an invalid
     * set of {@link Command} and/or {@link Arg} annotations present.
     * @throws NoValidConstructorException in the event of a scanned annotated class lacking a default/un-parameterized
     * constructor, and an "injected" instructor, see {@linkplain Controller}.
     */
    public final Builder build() throws IllegalAnnotationException, NoValidConstructorException
    {
        Set<Class<?>> types = new Reflections("").getTypesAnnotatedWith(Controller.class);
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                    "no controller annotated types were found on the class path."
            );
        }
        try
        {
            config.process(instantiate(types));
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Builds the configuration by scanning the specified package for {@link Controller} annotated classes.
     *
     * @return this <code>ConfigurationBuilder</code> object.
     * @param prefix a package prefix.
     *
     * @throws IllegalAnnotationException in the event of a scanned set of annotated classes having an invalid set of
     * {@link Controller} annotations present, and/or declared methods with an invalid
     * set of {@link Command} and/or {@link Arg} annotations present.
     * @throws NoValidConstructorException in the event of a scanned annotated class lacking a default/un-parameterized
     * constructor, and an "injected" instructor, see {@linkplain Controller}.
     * @throws NullPointerException in the event of a <code>null</code> specified prefix.
     */
    public final Builder build(String prefix)
            throws IllegalAnnotationException, NoValidConstructorException, NullPointerException
    {
        Set<Class<?>> types = new Reflections(Objects.requireNonNull(prefix)).getTypesAnnotatedWith(Controller.class);
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                    "no controller annotated types were found in the specified package."
            );
        }
        try
        {
            config.process(instantiate(types));
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Builds the configuration by manually adding a set of instantiated classes annotated with
     * {@link Controller}.
     *
     * @param controllers a set of instantiated classes annotated with <code>Controller</code>.
     * @return this <code>ConfigurationBuilder</code> object.
     * @throws IllegalAnnotationException in the event of a scanned set of annotated classes having an invalid set of
     * {@link Controller} annotations present, and/or declared methods with an invalid
     * set of {@link Command} and/or {@link Arg} annotations present.
     */
    public final Builder build(Set<Object> controllers) throws IllegalAnnotationException
    {
        config.process(controllers);
        return this;
    }

    /**
     * Returns this class's <code>AbstractConfiguration</code> field.
     * @return the <code>AbstractConfiguration</code> field associated with this class.
     */
    public final AbstractConfiguration configuration()
    {
        return config;
    }

    /**
     * Private method which instantiates and returns a set of classes using one of two predefined constructors.
     *
     * @param types the classes to be instantiated.
     * @return the instantiated classes.
     * @throws InstantiationException in the event of an instantiation related exception.
     * @throws IllegalAccessException in the event of an instantiation related exception.
     * @throws IllegalArgumentException in the event of an instantiation related exception.
     * @throws InvocationTargetException in the event of an instantiation related exception.
     * @throws NoValidConstructorException in the event of a specified class not having one or two of the predefined
     * constructors declared.
     */
    private Set<Object> instantiate(Set<Class<?>> types) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoValidConstructorException
    {
        Set<Object> instances = new HashSet<>();

        for (Class<?> type : types)
        {
            try
            {
                instances.add(type.getConstructor(config.console().getClass()).newInstance(config.console()));
            }
            catch (NoSuchMethodException | SecurityException ex)
            {
                instances.add(Arrays.stream(type.getConstructors())
                        .filter(cons -> cons.getParameterCount() == 0)
                        .findFirst()
                        .orElseThrow(() -> new NoValidConstructorException(
                                type.getSimpleName() + " could not be instantiated due to the fact it " +
                                        "neither has a default/un-parameterized constructor, nor a constructor " +
                                        "which takes an instance of a class which implements the IConsole interface " +
                                        "as its only parameter."
                        ))
                        .newInstance()
                );
            }
        }
        return instances;
    }
}
