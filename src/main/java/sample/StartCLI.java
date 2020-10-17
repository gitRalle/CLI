package sample;

import config.Configuration;
import config.ConfigurationBuilder;
import iface.IReflection;
import model.Console;

/**
 * <summary>This class presents a sample means to start/run your console application.</summary>
 */
public class StartCLI {

    /**
     * <summary>The IConfiguration field.</summary>
     */
    private final Configuration config;

    public StartCLI(Configuration config)
    {
        this.config = config;
    }

    public StartCLI() throws UnsupportedOperationException {
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
            IReflection reflection = config.map().match(input);
            (reflection != null ? reflection : new IReflection() {
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
    private Configuration getConfig()

    {
        return config;
    }

    public static void launch() throws Exception
    {
        StartCLI startup = new StartCLI(new ConfigurationBuilder(new Console())
                .build()
                .getConfig());
        while (true)
        {
            startup.run(startup.getConfig().console().read());
        }
    }

    public static void launch(Configuration config)
    {
        StartCLI startup = new StartCLI(config);
        while (true)
        {
            startup.run(startup.getConfig().console().read());
        }
    }
}