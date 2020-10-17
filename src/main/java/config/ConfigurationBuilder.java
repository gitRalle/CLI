package config;

import annotation.Controller;
import exception.IllegalAnnotationException;
import exception.NoDefaultConstructorException;
import iface.IConsole;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ConfigurationBuilder {

    private final Configuration config;

    /**
     * This constructor initializes this class's Configuration field with the parameterized IConsole instance.
     * @param console an IConsole instance.
     */
    public ConfigurationBuilder(IConsole console)
    {
        config = new Configuration(console);
    }

    /**
     * Unsupported constructor.
     */
    protected ConfigurationBuilder()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds the configuration by scanning the entire class path for controller-annotated classes.
     *
     * @return the ConfigurationBuilder.
     * @throws IllegalAnnotationException in the event of a scanned annotated-class having incorrectly attributed
     * class-annotations, and/or method-annotations.
     * @throws NoDefaultConstructorException in the event of a scanned annotated-class lacking a default/un-parameterized
     * constructor.
     */
    public ConfigurationBuilder build() throws IllegalAnnotationException, NoDefaultConstructorException
    {
        Set<Class<?>> types = new Reflections("").getTypesAnnotatedWith(Controller.class);
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                    "no controller-annotated types were found on the current class path."
            );
        }
        try
        {
            config.addControllers(instantiate(types));
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Builds the configuration by scanning the specified package for controller-annotated classes.
     *
     * @return the ConfigurationBuilder.
     * @throws IllegalAnnotationException in event of a scanned annotated-class having incorrectly attributed
     * class-annotations, and/or method-annotations.
     * @throws NoDefaultConstructorException in event of a scanned annotated-class lacking a default/un-parameterized
     * constructor.
     */
    public ConfigurationBuilder build(String prefix) throws IllegalAnnotationException,
            NoDefaultConstructorException
    {
        Set<Class<?>> types = new Reflections(Objects.requireNonNull(prefix)).getTypesAnnotatedWith(Controller.class);
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                    "no controller-annotated types were found in the specified package."
            );
        }
        try
        {
            config.addControllers(instantiate(types));
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Builds the configuration by manually adding instances of the controller annotated classes.
     * @param controllers a collection of instances of controller annotated classes.
     * @return the ConfigurationBuilder.
     */
    public ConfigurationBuilder build(Collection<Object> controllers) throws IllegalAnnotationException
    {
        config.addControllers(controllers);
        return this;
    }

    /**
     * GET method for the builder class's IConfiguration instance.
     * @return the IConfiguration instance.
     */
    public Configuration getConfig()
    {
        return config;
    }

    /*
    private List<Object> instantiate(Set<Class<?>> types)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoDefaultConstructorException
    {
        List<Object> instances = new ArrayList<>();

        for (Class<?> type : types) {
            Constructor<?>[] constructors = type.getConstructors();
            instances.add(Arrays.stream(constructors)
                    .filter(cons -> cons.getParameterCount() == 0)
                    .findFirst()
                    .orElseThrow(() -> new NoDefaultConstructorException(
                            "failed to instantiate " + type.getSimpleName() + " due to the fact " +
                                    "class has no declared/default constructor with zero parameters."
                    ))
                    .newInstance()
            );
        }
        return instances;
    }
     */

    private List<Object> instantiate(Set<Class<?>> types) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoDefaultConstructorException
    {
        List<Object> instances = new ArrayList<>();

        for (Class<?> type : types)
        {
            try
            {
                instances.add(type.getConstructor(IConsole.class).newInstance(config.console()));
            }
            catch (NoSuchMethodException | SecurityException ex)
            {
                instances.add(Arrays.stream(type.getConstructors())
                        .filter(cons -> cons.getParameterCount() == 0)
                        .findFirst()
                        .orElseThrow(() -> new NoDefaultConstructorException(
                                type.getSimpleName() + " could not be instantiated due to the fact it " +
                                        "neither has a default/un-parameterized constructor, nor a constructor which takes " +
                                        "an instance of the IConsole interface as its only parameter."
                        ))
                        .newInstance()
                );
            }
        }
        return instances;
    }
}
