package config;

import java.lang.reflect.*;
import annotation.*;
import iface.*;
import model.AbstractController;
import model.MethodBundle;
import model.ParamBundle;
import exception.DefaultedValueException;
import exception.NoSuchDelimiterException;
import exception.ParseException;
import exception.IllegalAnnotationException;

import org.jetbrains.annotations.NotNull;
import java.util.*;

import static util.AnnotationUtils.*;
import static util.StringUtils.splitAfterFirst;
import static util.StringUtils.permute;
import static util.ObjectUtils.toObject;
import static util.ObjectUtils.toDefaultValue;

/**
 * This class's protected methods are responsible for populating this class's {@link ReflectionMap},<br>
 * by carrying out runtime annotation processing of elements annotated with the annotations declared in
 * the {@link annotation} package.
 */
public final class Configuration extends AbstractConfiguration {

    // Todo: refactor the regexSet field into the ReflectionMap class.
    /**
     * This set is used to store distinct regular expressions created by this class's
     * process method.
     */
    private final Set<String> regexSet = new HashSet<>();

    /**
     * Constructs a new object and initializes this class's IConsole field and {@link ReflectionMap} field.
     * @param console a class which implements the IConsole interface.
     */
    protected Configuration(IConsole console) {
        super(console);
    }

    // Todo: introduce a separate method/class/interface which validates the input received by this method.
    /**
     * This process method uses the {@link java.lang.reflect} api to create [Regular Expression,
     * {@link iface.IReflection}] entries
     * from the properties of elements annotated with the annotations declared in the
     * {@link annotation} package.
     *
     * @param controllers a set of instantiated {@link Controller} annotated classes to be processed.
     * @throws IllegalAnnotationException in the event of insufficient annotations, or in the event of
     * an annotated method not having the void modifier, or in the event of an annotated method not being an instance method,
     * or in the event of the same regular expression being derived from two or more annotated elements,
     * or in the event of an annotated method's argument not being of a primitive datatype or wrapper class.
     */
    protected void process(Set<Object> controllers) throws IllegalAnnotationException
    {
        for (Object object : controllers) {
            Class<?> objectClass = object.getClass();

            if (!hasAnnotation(objectClass)) {
                throw new IllegalAnnotationException(
                        "class is not annotated with @Controller."
                );
            }

            for (Method method : objectClass.getDeclaredMethods()) {
                method.setAccessible(true);
                StringBuilder regex = new StringBuilder();
                StringBuilder setRegex = new StringBuilder();

                if (isStatic(method)) {
                    throw new IllegalAnnotationException(
                            "method annotated with @Command may not be static."
                    );
                }

                if (!isVoid(method)) {
                    throw new IllegalAnnotationException(
                            "method annotated with @Command must be void."
                    );
                }

                regex.append("^");
                setRegex.append("^");

                if (!hasIgnoreKeyword(objectClass)) {
                    regex.append(getKeyword(objectClass)).append("\\s");
                    setRegex.append(getKeyword(objectClass)).append("\\s");
                }

                if (hasAnnotation(method)) {
                    regex.append(getKeyword(method));
                    setRegex.append(getKeyword(method));
                    ParamBundle[] params = new ParamBundle[method.getParameterCount()];

                    if (method.getParameterCount() > 0) {
                        LinkedList<String> args = new LinkedList<>();
                        LinkedList<String> nonOptionalArgs = new LinkedList<>();

                        int i = 0;
                        for (Parameter param : method.getParameters())
                        {
                            if (!isPrimitive(param.getType())) {
                                throw new IllegalAnnotationException(
                                        "parameter of method annotated with @Command must be primitive."
                                );
                            }

                            if (isOptional(param)) {
                                args.add("(" + "\\s" + getKeyword(param) + "\\s" +
                                        "\\S*" + "|" + ")");
                            }
                            else {
                                args.add("\\s" + getKeyword(param) + "\\s" +
                                        "\\S*");
                                nonOptionalArgs.add(args.get(args.size() - 1));
                            }
                            params[i++] = new ParamBundle(param.getType(), getKeyword(param));
                        }
                        setRegex.append(permute(nonOptionalArgs));
                        regex.append(permute(args));
                    }
                    regex.append("$");
                    setRegex.append("$");

                    if (!regexSet.add(setRegex.toString())) {
                        throw new IllegalAnnotationException(
                                "the final regex pattern derived from a method annotated with @Command, " +
                                        "and it's non-optional arguments must be distinct."
                        );
                    }
                      super.map().put(regex.toString(), (input) -> {
                        try {
                            invoke(new MethodBundle(object, method, params), input);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    if (hasNoMatch(method)) {
                        String key = "^" + (hasIgnoreKeyword(objectClass) ? "" : getKeyword(objectClass)
                                .concat("\\s")).concat(getKeyword(method)).concat("(|.*)$");
                        super.map().append(key, (input) -> super.console().printerr(getNoMatch(method)));
                    }
                }
            }
        }
    }

    /**
     * This method parses the specified input string and uses its content to initialize the arguments of
     * the specified method, and then invokes the specified method.<br>
     * If the delimiter for one or more of the specified method's arguments are not present as a
     * substring of the input, then the value of that parameters is defaulted.
     * The value of the defaulted parameter is determined by the type of the parameter.
     *
     * @param methodBundle the <code>MethodBundle</code> object.
     * @param input the <code>String</code> from which the specified method's arguments are to be parsed.
     * @throws Exception in the event of an <code>Exception</code> being thrown during the call to
     * {@link java.lang.reflect.Method#invoke(Object, Object...)} on the specified method.
     */
    private void invoke(@NotNull MethodBundle methodBundle, String input)
            throws Exception
    {
        Object[] args = new Object[methodBundle.getParams().length];
        Object arg = null;

        if (methodBundle.getObject().getClass().getSuperclass() == AbstractController.class) {
            ((AbstractController) methodBundle.getObject()).invokeState().clear();
        }

        int i = 0;
        for (ParamBundle paramBundle : methodBundle.getParams()) {
            try
            {
                arg = toObject(paramBundle.getType(), splitAfterFirst(input, paramBundle.getName()));
            }
            catch (ParseException | NoSuchDelimiterException ex)
            {
                arg = toDefaultValue(paramBundle.getType());

                if (methodBundle.getObject().getClass().getSuperclass() == AbstractController.class) {
                    ((AbstractController) methodBundle.getObject()).invokeState()
                            .append(paramBundle.getName(), ex)
                            .append(paramBundle.getName(), new DefaultedValueException
                                    ("'" + paramBundle.getName() + "' was defaulted."));
                }
            }

            catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            finally {
                args[i++] = arg;
            }
        }
        methodBundle.getMethod().invoke(methodBundle.getObject(), args);
    }
}