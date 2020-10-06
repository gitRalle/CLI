package sample;

import annotation.Arg;
import annotation.Command;
import data.Controller;
import data.Console;
import iface.*;

@annotation.Controller(ignoreKeyword = true)
public class TestController extends Controller {

    private final IConsole console;

    public TestController()

    {
        console = new Console();
    }

    @Command(partialMatchMessage = "test")
    public void test() {
        console.println("test");
    }

    @Command(keyword = "test", partialMatchMessage = "test [b:bool] (i:int)")
    public void test(boolean b, @Arg(optional = true) int i) {
        console.println(String.format(
                "successful: %s%ndefaulted(i): %s",
                invokeState().successful(), invokeState().defaulted("i")
        ));
    }

    @Command
    public void function(@Arg(keyword = "-s") String s) {
        console.println(s);
    }


}
