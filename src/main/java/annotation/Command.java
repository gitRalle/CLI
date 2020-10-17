package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your methods with this annotation if you would like them to be stored in a map, to be conveniently accessed at runtime.
 * They are generated through the calling of the config.ConfigurationBuilder.build() method,
 * and will be accessible at runtime through the config.ConfigurationBuilder.getConfig().map() method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * Use this annotation field to specify a keyword to use at runtime to access this method.
     * Defaults to method.getName().
     *
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * Use this annotation field to specify a message which should be appended to the console using a specified
     * implementation of the iface.IConsole.printerr() method, in the case of a user-input only matching the value of
     * the optional controller prefix annotation field plus this annotation's keyword annotation field.
     *
     * @return the message.
     */
    String noMatch() default "";
}
