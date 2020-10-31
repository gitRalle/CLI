package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import config.Configuration;
import config.Builder;
import iface.IConsole;

/**
 * Annotate your methods with this annotation if you would like them to be conveniently accessible at runtime.<br>
 * They are generated through the calling of one of the {@link Builder#build()} methods on an
 * instance of the builder class, and will be accessible at runtime through the {@link Configuration#map()} method.<br>
 * Here are some examples:<br><br>
 * <code>
 *  {@literal @}Command<br>
 *  public void foo() {<br>
 *  <BLOCKQUOTE>console.println("text");</BLOCKQUOTE>
 *  }<br>
 * </code>
 * <p>Here the annotation's keyword field is implicitly set to the name of the method (foo).</p>
 * <br>
 * <code>
 *  {@literal @}Command(keyword = "foo")<br>
 *  public void foo() {<br>
 *  <BLOCKQUOTE>console.println("text")</BLOCKQUOTE>
 *  }<br>
 * </code>
 * <p>Here the annotation's keyword field is explicitly set to foo.</p>
 * <br>
 * <code>
 *  {@literal @}Command(noMatch = "message")<br>
 *  public void foo(String input) {<br>
 *  <BLOCKQUOTE>console.println(input);</BLOCKQUOTE>
 *  }<br>
 * </code>
 * <p>Here the annotation's noMatch field is set to "message", this string will be appended to the console
 * by the {@link IConsole#printerr(String)} method, called on the class passed to the
 * {@link Builder#Builder(IConsole)} constructor in the event of a partial match ->
 * i.e. when the keyword of the method is successfully matched, but not all of its arguments.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * Use this annotation field to explicitly set the keyword to which the runtime invocation of
     * this method maps to.<br>
     * If this annotation field is omitted; it defaults to <code>method.getName().toLowerCase();</code>
     * @return the keyword.
     */
    String keyword() default "";

    /**
     * Use this annotation field to specify a message which should be appended to the console using
     * using the {@link IConsole#printerr(String)} method on the class passed to the
     * {@link Builder#Builder(IConsole)} constructor in the event of a partial match ->
     * i.e. when the keyword of the method is successfully matched, but not all of its arguments.
     *
     *
     * @return the message.
     */
    String noMatch() default "";
}
