package sample;

import config.AbstractConfiguration;
import config.ReflectionMap;
import iface.IReflection;

/**
 * Class represents a sample way to start an application using the com.github.wnebyte.cli library.
 */
public class StartCLI
{
    private final AbstractConfiguration config;

    /**
     * Constructs a new object using the specified configuration.
     * @param config the configuration to use.
     */
    public StartCLI(AbstractConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException(
                    "config must not be null."
            );
        }
        this.config = config;
    }

    /**
     * do not use.
     */
    public StartCLI() {
        throw new UnsupportedOperationException(
                "do not use this constructor."
        );
    }

    /**
     * This run method fetches the {@link IReflection} value associated with the specified input through a call to the
     * {@link ReflectionMap#match(String)} method.<br>If no <code>IReflection</code> value is associated with the
     * specified input, then a default not found message is appended to the <code>IConsole</code>.
     * @param input the string -> <code>Pattern</code> to match against.
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
     * Quick launch.
     * @param config the configuration to use.
     */
    public static void launch(AbstractConfiguration config)
    {
        StartCLI startup = new StartCLI(config);
        while (true)
        {
            startup.run(config.console().read());
        }
    }
}