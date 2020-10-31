package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import exception.NoValidConstructorException;
import iface.IConsole;
import config.Builder;

/**
 * Annotate your classes with this annotation if they declare any {@link Command} annotated methods.<br>
 * In addition, one of the following constructors needs to be declared, otherwise a {@link NoValidConstructorException}
 * will be thrown when the annotated class is reflectively constructed.<br><br>
 * <code>
 * public SampleController(IConsole) {<br>
 * <BLOCKQUOTE>// code;</BLOCKQUOTE>
 * }<br>
 * </code>
 * <p>If your annotated class declares this constructor, the same instance of the {@link IConsole} interface
 * which was passed to the {@link Builder#Builder(IConsole)} constructor will be
 * injected into this constructor.</p><br>
 * <code>
 * public SampleController() {<br>
 * <BLOCKQUOTE>// code;</BLOCKQUOTE>
 * }<br>
 * </code>
 * <p>If the first constructor is not present, a default/un-parameterized constructor needs to be
 * implicitly or explicitly declared.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    /**
     * Use this annotation field to specify an optional prefix for any declared {@link Command}
     * annotated methods.<br>
     * If this annotation field is omitted, and this annotation's ignoreKeyword field is set to false;
     * it defaults to <code>class.getSimpleName().toLowerCase().split("controller")[0];</code>
     *
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * Use this annotation field to flag/un-flag the use of this annotation's keyword field.
     *
     * @return the ignoreKeyword.
     */
    boolean ignoreKeyword() default false;

    /**
     * @deprecated
     * Use this annotation field to specify a message which should be appended to the console using a specified
     * implementation of the iface.IConsole.printerr() method, in the case of a user-input only matching the value
     * of this annotation's (optional) keyword annotation field.
     *
     * @return the message.
     */
    String noMatch() default "";
}
