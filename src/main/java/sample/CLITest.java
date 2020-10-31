package sample;

import config.Builder;
import iface.IReflection;
import model.Console;

public class CLITest {

    public static void main(String[] args) throws Exception
    {
        Builder builder = new Builder(new Console()).build();

        String input = builder.configuration().console().read();

    }

}

