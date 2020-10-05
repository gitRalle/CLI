package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <summary>Annotate your method with this annotation, if you'd like it mapped to a regex pattern
 * command. Later to be matched against, and called from your console.</summary>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * <summary>This String defines the whole, or part of the regex pattern to which a Method
     * annotated with @Command maps to.</summary>
     *
     * Defaults to the name of the Method.
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * <summary>A String to be appended to the console, in the case of a partial keyword match.</summary>
     *
     * @return the String.
     */
    String partialMatchMessage() default "";
}
