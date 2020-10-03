package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * <summary>This String defines the regex to which the method is mapped.</summary>
     * By default, the regex is determined by the actual name of the method.
     *
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * <summary>This String will be outputted into the console when the method-keyword parsed from the input
     * string matches the keyword of the method, but the keyword-arguments parsed do not match the keyword-arguments
     * defined by the method.</summary>
     *
     * @return the message.
     */
    String argsDoNotMatchMessage() default "";
}
