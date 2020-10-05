package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <summary>Annotate your @Command-annotated Method's Parameters with this annotation, if you'd like to
 * explicitly set their keyword, or map them as optional Parameters.</summary>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Arg {

    /**
     * <summary>String which defines the regex pattern, to which an argument belonging to a @Command-annotated
     * method maps to.</summary>
     *
     * Defaults to the name of the Parameter (which is only recorded in the java file, if
     * the -parameters compiler argument has been supplied).
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * <summary>Boolean value which determines whether or not the annotated Parameter should be optionally
     * mapped to the underlying regex pattern.</summary>
     *
     * @return the boolean value.
     */
    boolean optional() default false;
}
