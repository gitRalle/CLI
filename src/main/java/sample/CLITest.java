package sample;


import config.ConfigurationBuilder;
import model.Console;


public class CLITest {

    public static void main(String[] args) throws Exception
    {
        StartCLI.launch(new ConfigurationBuilder(new Console())
                .build().getConfig());
    }


}

