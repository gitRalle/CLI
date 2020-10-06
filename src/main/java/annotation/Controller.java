package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <summary>Annotate your class with this annotation, if it declares @Command-annotated methods.</summary>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    /**
     * <summary>String which determines the initial part of the regex pattern, which @Command-annotated
     * methods map to.</summary>
     *
     * Defaults to the name-of-the-java-file.split('controller')[0];
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * <summary>Boolean value which determines if the keyword should be omitted in the mapping
     * process.</summary>
     *
     * @return the boolean value.
     */
    boolean ignoreKeyword() default false;

    /**
     * <summary>A message to be appended to the console, in the case of a partial match.</summary>
     *
     * @return the message.
     */
    String partialMatchMessage() default "";
}
