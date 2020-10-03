package sample;


import config.Configuration;
import config.Startup;
import data.Console;
import iface.IConfiguration;
import java.util.ArrayList;

public class CLITest {

    public static void main(String[] args) {
        CLITest app = new CLITest();
        app.start(app.configure(new Configuration(new Console())));
    }

    public IConfiguration configure(IConfiguration config) {
        config.addControllers(new ArrayList<>() {{
            add(new TestController());
        }});

        return config;
    }

    public void start(IConfiguration config) {
        Startup startup = new Startup(config);
        while (true) {
            startup.run(config.console().read());
        }
    }
}

