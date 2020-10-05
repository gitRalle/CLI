package iface;

import config.ReflectionMap;

import java.util.Collection;

/**
 * <summary>Interface which declares abstract methods which need to be implemented by a
 * Configuration class.</summary>
 */
public interface IConfiguration {

    /**
     * The console.
     * @return The console.
     */
    IConsole console();

    /**
     * The ReflectionMap.
     * @return The ReflectionMap.
     */
    ReflectionMap map();

    /**
     * Method for adding controllers/methods to the underlying Map.
     *
     * @param controllers the controllers to be used in the configuration.
     */
    void addControllers(Collection<Object> controllers);
}
