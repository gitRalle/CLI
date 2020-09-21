package sample;

import annotation.Arg;
import annotation.Command;
import annotation.Controller;
import data.Console;
import iface.*;

@Controller(ignoreKeyword = true)
public class TestController {

    private final IConsole console;

    public TestController()

    {
        console = new Console();
    }

    @Command(notFoundMessage = "test")
    public void test() {
        console.println("test");
    }

    @Command(notFoundMessage = "test [b:bool]")
    public void test(boolean b) {
        console.println("test b:bool");
    }

    @Command(notFoundMessage = "test [b:bool] (i:int)")
    public void test(boolean b, @Arg(optional = true) int i) {
        console.println("test b:bool, (i:int)");
    }

    /*
    // Todo: 1. print out all notFoundMessage(s) when match occurs.
       Todo: 2. rm optional arg part of regex and put into set -> throw exception if not distinct.
     */

}
