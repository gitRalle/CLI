package config;

import iface.IConfiguration;
import iface.IReflection;

public class Startup {

    private final IConfiguration config;

    public Startup(IConfiguration configuration) {
        if (configuration == null)
            throw new IllegalArgumentException(
                    "configuration must not be null."
            );
        this.config = configuration;
    }

    /**
     * <summary>Matches the input with the contents of the configured IMap.
     * If a match occurs; the respective functional interface implementation is invoked.
     * If no match occurs; a default String will be outputted into the console,
     * using the printerr method of the configured IConsole class.</summary>
     *
     * @param input the user input from the console.
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


    public IConfiguration getConfig()
    {
        return config;
    }
}