package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your types with this annotation if they declare command annotated methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    /**
     * Use this annotation field to specify an optional prefix for any declared command annotated methods.
     * Defaults to className.split("controller")[0].
     *
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * Use this annotation field to flag/un-flag the use of this annotation's keyword annotation field.
     *
     * @return the ignoreKeyword.
     */
    boolean ignoreKeyword() default false;

    /**
     * Use this annotation field to specify a message which should be appended to the console using a specified
     * implementation of the iface.IConsole.printerr() method, in the case of a user-input only matching the value
     * of this annotation's (optional) keyword annotation field.
     *
     * @return the message.
     */
    String noMatch() default "";
}
