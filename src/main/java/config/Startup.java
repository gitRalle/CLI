package config;

import iface.IConfiguration;
import iface.IReflection;

/**
 * <summary>This class presents a sample way to start/run your console application.</summary>
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
     * <summary>Matches the input with the contents of the configured ReflectionMap.
     * If a match occurs; the respective Method is invoked.
     * If no match occurs; a default String will be print in the console,
     * using the printerr method of the configured IConsole class.</summary>
     *
     * @param input the user input to be evaluated.
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


    // Todo: unnecessary?
    /**
     * <summary>Returns the reference for the IConfiguration instance passed to the Constructor.</summary>
     *
     * @return the IConfiguration instance.
     */
    public IConfiguration getConfig()
    {
        return config;
    }
}