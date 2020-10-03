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

    @Command(argsDoNotMatchMessage = "test")
    public void test() {
        console.println("test");
    }

    @Command(argsDoNotMatchMessage = "test [b:bool]")
    public void test(boolean b) {
        console.println(String.valueOf(invokeState().successful()));
    }

    @Command(keyword = "test")
    public void testy(boolean b) {

    }

    /*
    @Command(keyword = "test", argsDoNotMatchMessage = "test [b:bool] (i:int)")
    public void test(boolean b, @Arg(optional = true) int i) {
        console.println(String.format(
                "successful: %s%ndefaulted(i): %s",
                invokeState().successful(), invokeState().defaulted("i")
        ));

    }
     */
}
