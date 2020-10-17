package sample;

import annotation.Arg;
import annotation.Command;
import annotation.Controller;
import model.BaseController;
import iface.*;
import model.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Annotate your class with this annotation if it declares command annotated methods.
 *
 * We explicitly set the ignoreKeyword annotation field to true so that we won't require a prefix when identifying
 * a declared command annotated method at runtime.
 */
@Controller(ignoreKeyword = true)

/**
  Having your controller annotated classes extend the BaseController class gives access to it's invokeState object,
  which holds thrown exceptions related information regarding the most recently invoked command annotated method.

  This can be useful if you have any command annotated methods with any !nullable optional arguments.
 */
public class SampleController extends BaseController
{
    /**
     * This is the field for our console interface.
     * Will be used to output text into our console.
     */
    private final IConsole console;

    /**
     * If you declare a constructor which takes an instance of the IConsole interface as its only parameter,
     * the same instance that was used to construct the configurationBuilder will be passed to this constructor.
     */
    public SampleController(IConsole console) {
        this.console = console;
    }

    /**
     * If the preceding constructor was not declared, either an un-parameterized constructor needs to be declared,
     * or a default one.
     */
    public SampleController()
    {
        this.console = new Console();
    }

    /**
     * Annotate your method with this annotation if you'd like to access it at runtime.
     * All of your command annotated methods will be accessible at runtime through the
     * configurationBuilder.getConfig().map() method -- after the configurationBuilder class has been instantiated.
     *
     * <Command>
     * keyword: this method has its keyword annotation field set to the name of the method implicitly.
     * noMatch: this message will be appended to the console using the interface passed to the
     * builder's constructor, in case we at runtime successfully match some user-input against
     * the keyword of the method, but we do not successfully match against its argument(s).
     * </Command>
     *
     *
     * <Arg>
     * keyword: this method's only arg has its keyword annotation field explicitly set to "-f".
     * optional: this arg is optional, which means that this command method may be matched against
     * some user-input at runtime and invoked with or without supplying an arg.
     * </Arg>
     */
    @Command(noMatch = "date (-f:bool)")
    public void date(@Arg(keyword = "-f", optional = true) String format)
    {
        if (format != null)
        {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                console.println(LocalDateTime.now().format(formatter));
            }
            catch (Exception ex) {
                console.printerr("-f is not a valid format.");
            }
        }
        else
        {
            console.println(LocalDateTime.now().toString());
        }
    }

    @Command(keyword = "echo", noMatch = "echo [a:int] [b:int]")
    public void function(int a, @Arg(keyword = "-b") int b)
    {
        /**
         * wasSuccessful() returns true if no ParseExceptions were thrown during the invocation of this method
         * -- i.e. there were no thrown NumberFormatExceptions or other similar Exceptions when parsing the
         * user-input into two integers.
         */
        if (invokeState().wasSuccessful())
        {
            console.println(String.format("a: %d, -b: %d%n", a, b));
        }
        else
        {
            /**
             * initialisationOf().wasSuccessful() works the same way as the previously shown one,
             * the only difference is it works on individual arguments.
             */
            if (invokeState().initializationOf("a").wasSuccessful())
            {
                console.println("a: " + a);
            }
            else if (invokeState().initializationOf("-b").wasSuccessful())
            {
                console.println("-b: " + b);
            }
            else {
                console.println("could not parse the user-input into two integers.");
            }
        }
    }

    @Command(keyword = "echo bool")
    public void bool(@Arg(keyword = "-b", optional = true) boolean b)
    {
        /**
         * invokeState().initialisationOf().wasDefaulted() returns true if a certain value for an arg could
         * not be parsed into the desired datatype and had to have its value be defaulted,
         * or if an arg had its optional annotation field set to true, and the value was omitted from the user-input.
         */
        if (!invokeState().initializationOf("-b").wasDefaulted())
        {
            console.println("entered value for -b: " + b);
        }
        else
        {
            console.println("value for -b was omitted, so the value has been defaulted to false.");
        }
    }

}
