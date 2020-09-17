package sample;

import annotation.Arg;
import annotation.Command;
import annotation.Controller;
import data.CLIController;
import data.Console;
import iface.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller(ignoreKeyword = true)
public class TestController extends CLIController {

    private final IConsole console;

    public TestController()
    {
        console = new Console();
    }

    @Command
    public void clear() {
        console.println("\n\n\n\n\n");
    }

    @Command
    public void date(@Arg(keyword = "-s", optional = true) String s) {
        /*
        if (s == null || s.equals("now")) {
            console.println(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        else if (s.equals("yesterday")) {
            console.println(LocalDateTime.now().minusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
         */
        System.out.println(s + " : " + argState().isValid());
    }

    @Command
    public void time() {
        console.println(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @Command
    public void test(@Arg(keyword = "-b", optional = false) boolean b) {
        console.println("isValid: " + argState().isValid() + "\n" +
                "-b isDefaulted: " + argState().isDefaulted("-b") + "\n" +
        "-b exceptions: " + argState().getExceptions("-b"));
    }
}
