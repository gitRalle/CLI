package sample;


import config.Configuration;
import config.Startup;
import data.Console;
import iface.IConfiguration;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.start(app.configure(new Configuration(new Console())));
    }

    public IConfiguration configure(IConfiguration config) {
        config.addControllers(new ArrayList<>() {{
        //    add(new CrawlController());
            add(new TestController());
        }});
        return config;
    }

    public void start(IConfiguration config) {
        new Startup(config).run();
    }
}

