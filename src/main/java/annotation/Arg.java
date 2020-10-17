package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your command annotated method's parameters with this annotation if you'd like to explicitly set their keyword,
 * and/or flag/un-flag them as optional parameters.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Arg {

    /**
     * Use this annotation field to explicitly set a keyword for this parameter.
     * If the compilerArg -parameters is set then the value of this annotation field defaults to parameter.getName().
     *
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * Use this annotation field to flag/un-flag this parameter as being optional.
     *
     * @return the boolean value.
     */
    boolean optional() default false;
}
