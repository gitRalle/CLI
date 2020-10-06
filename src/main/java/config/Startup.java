package config;

import iface.IConfiguration;
import iface.IReflection;

/**
 * <summary>This class presents a sample means to start/run your console application.</summary>
 */
public class Startup {

    /**
     * <summary>The IConfiguration field.</summary>
     */
    private final IConfiguration config;

    /**
     * Constructor, sets the IConfiguration field.
     *
     * @param configuration the IConfiguration instance to be used by this class.
     */
    public Startup(IConfiguration configuration) throws IllegalArgumentException {
        if (configuration == null)
            throw new IllegalArgumentException(
                    "configuration must not be null."
            );
        this.config = configuration;
    }

    public Startup() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * <summary>Matches the supplied String input with the keys of the underlying,
     * configured ReflectionMap. The respective IReflection object is invoked if a match is found.
     * Otherwise a default invocation is called, resulting in a default String being
     * outputted into the console.</summary>
     *
     * @param input the String to be matched against.
     */
    public void run(String input) {
            IReflection method = config.map().match(input);
            (method != null ? method : new IReflection() {
                @Override
                public void invoke(String input) {
                    config.console().printerr(
                            "'" + input + "' is not recognized as an internal command."
                    );
                }
            }).invoke(input);
    }


    /**
     * <summary>GET method for the this class's IConfiguration instance.</summary>
     *
     * @return the IConfiguration instance.
     */
    public IConfiguration getConfig()
    {
        return config;
    }
}