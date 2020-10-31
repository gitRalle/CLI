package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your {@link Command} annotated method's parameters with this annotation
 * if you'd like to explicitly set their keyword, and/or flag/un-flag them as optional parameters.<br>
 * Here are some examples:<br><br>
 * <code>
 * {@literal @}Command<br>
 * public void foo({@literal @}Arg String input) {<br>
 * <BLOCKQUOTE>console.println(input);</BLOCKQUOTE>
 * }<br>
 * </code>
 * <p>Here the annotation's keyword field is implicitly set to input,
 * and the optional field is implicitly set to false.</p>
 * <br>
 * <code>
 * {@literal @}Command<br>
 * public void foo({@literal @}Arg(keyword = "input") String input) {<br>
 * <BLOCKQUOTE>console.println(input);</BLOCKQUOTE>
 * }<br>
 * </code>
 * <p>Here the annotation's keyword field is explicitly set to input,
 * and the optional field is implicitly set to false.</p>
 * <br>
 * <code>
 * {@literal @}Command<br>
 * public void foo({@literal @}Arg(optional = true) String input) {<br>
 * <BLOCKQUOTE>console.println(input != null ? input : "");</BLOCKQUOTE>
 * }<br>
 * </code>
 * <p>Here the annotation's keyword field is implicitly set to input,
 * and the optional field is explicitly set to true.
 * In this case; the command may be matched with or without specifying the input arg.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Arg {

    /**
     * Use this annotation field to explicitly set the keyword for this parameter.<br>
     * If the compiler arg [-parameters] is present, then the value of this annotation field defaults to
     * <code>parameter.getName().toLowerCase();</code><br>If [-parameters] is not present, it defaults to arg[n].
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
