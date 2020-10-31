# CLI
java-library
## About
This package is a java library which can help you to easily build a functional
command-line-interface.
## Build
Add the [-parameters] argument to the java compiler to 
save the names of any local parameters in the relevant 
java files.  
Doing so means you are not required to explicitly set the 
keyword annotation field for any parameters annotated with Arg, 
unless you'd like for their names to be overridden.
#### Gradle 
    compileJava {
        options.compilerArgs.add("-parameters")
    }
## Configuration
#### Build your configuration by calling one of the build methods
##### Scan the entire classpath for annotated types
<code>Builder builder = new Builder(new Console()).build();</code>
##### Scan the specified package for annotated types
<code>Builder builder = new Builder(new Console()).build("my.package");</code> 
 
The class passed to the builder's constructor may be of any type so long as 
it implements the interface IConsole, or an interface which extends the 
IConsole interface. 

<br></br>    
#### Access to the relevant objects through the Builder class
##### Access to the ReflectionMap (which holds references for your @Command annotated methods)
<code>builder.configuration().map();</code>  
##### Access to the IConsole (which represents your console)
<code>builder.configuration().console();</code>

<br></br>  
#### Connect your IConsole to your ReflectionMap
##### Auto  
<code>StartCLI.launch(builder.configuration());</code>  

Encloses the manual implementation in an infinite loop.  
Is only a suitable approach if the default implementation of the IConsole 
interface is being used.
##### Manual
    String input = builder.configuration().console().read();
    IReflection reflection = config.map().match(input);
    (reflection != null ? reflection : new IReflection() {
        @Override
        public void invoke(String input) {
            config.console().printerr(
                "'" + input + "' is not recognized as an internal command."
            );
        }
    }).invoke(input);
## Sample
To see the full documentation on all of the 
available annotations and their annotation fields, click here!
(coming soon).

#### Annotate your classes
    @Controller(ignoreKeyword = true)
    public class SampleController extends AbstractController {

    private final IConsole console;

    public SampleController(IConsole console) {
        this.console = console;
    }
    } 
The above Controller annotation needs to be present in a class if it declares any 
Command annotated methods.

The above constructor can be used to inject the same 
instance of the IConsole interface which was passed 
to the Builder's constructor into any Controller 
annotated classes. 

#### Annotate your methods
    @Command(noMatch = "date (-f:str)")
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
The value of the noMatch annotation field will be appended to the console in the 
case of a partial match, i.e. when the input matches the name of the method, 
but not any of its arguments.
## Documentation
link coming soon
## Licence
tbd