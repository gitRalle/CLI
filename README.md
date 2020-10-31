# CLI
java-library
## About
This java library uses the java reflection api to enable you to easily create a functional command-line-interface.  
Annotate any methods you need accessed at runtime through the supply of some user input through a console with the proper annotations, 
and when built will be processed and placed in map for easily retrieval.   

Look at [Sample](#sample) for examples on how to annotate your elements.  

Look at [Configuration](#configuration) to learn how to have your annotated elements processed, and for examples on how to use them in conjunction with your implemented console. 

## Sample
### Annotate your methods
    @Command(keyword = "foo", noMatch = "foo []")
    public void foo()
    {
        // code
    }
Here the keyword property of the method is explictly set to "foo",  
and the noMatch property of the method, which is a message to be appended to the console in the event of a partial match, is set to "foo []"

    @Command
    public void foo()
    {
        // code
    }
Here the keyword property of the method foo is implicitly set to "foo", and the noMatch property is omitted.
### Annotate your arguments
Annotate your arguments if you'd like to explicitly set their keyword property, or if you'd like to flag them as being optional.  

    @Command(keyword = "foo")
    public void foo(@Arg(keyword = "input", optional = true) String input)
    {
        // code
    }
Here the keyword property of the argument is explicitly set to "input", and the optional property is explicitly set to true.
    
    @Command(keyword = "foo")
    public void foo(@Arg(keyword = "input") String input)
    {
        // code
    }
Here the keyword property of the argument is explicitly set to "input", and the optional property is implicitly set to false.

    @Command(keyword = "foo")
    public void foo(String input)
    {
        // code
    }
Here the keyword property of the argument is implicitly set to "input", and the optional property is implicitly set to false.
### Annotate your classes
Annotate your classes if they declare any annotated methods.

    @Controller(keyword = "sample")
    public class SampleController
    {
    
    }
Here the keyword property of the class, which adds a prefix to the keyword properties of any annotated methods, is explicitly set to "sample". 

    @Controller
    public class SampleController
    {
    }
Here the keyword property of the class is implicitly set to "sample".

    @Controller(ignoreKeyword = true)
    public class SampleController
    {
    }
Here the ignoreKeyword property of the class is explicitly set to true. In this case, no prefix will be added to any annotated methods declared in this class.
## Configuration
### Build your configuration by calling one of the build methods
#### Scan the entire classpath for annotated types
<code>Builder builder = new Builder(new Console()).build();</code>
#### Scan the specified package for annotated types
<code>Builder builder = new Builder(new Console()).build("my.package");</code> 
 
The class passed to the builder's constructor may be of any type so long as 
it implements the interface IConsole, or an interface which extends the 
IConsole interface. 
### Access to the relevant objects through the Builder class
#### Access to the ReflectionMap (which holds references for your @Command annotated methods)
<code>builder.configuration().map();</code>  
#### Access to the IConsole (which represents your console)
<code>builder.configuration().console();</code>
### Connect your IConsole to your ReflectionMap
#### Auto  
<code>StartCLI.launch(builder.configuration());</code>  

Encloses the manual implementation in an infinite loop.  
Is only a suitable approach if the default implementation of the IConsole 
interface is being used.
#### Manual
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
## Build
Add the [-parameters] argument to the java compiler to 
save the names of any local parameters in the relevant 
java files.  
Doing so means you are not required to explicitly set the 
keyword annotation field for any parameters annotated with Arg, 
unless you'd like for their names to be overridden.
### Gradle 
    compileJava {
        options.compilerArgs.add("-parameters")
    }
## Documentation
link coming soon
## Licence
tbd
