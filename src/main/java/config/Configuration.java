package config;

import iface.*;
import model.BaseController;
import model.MethodBundle;
import model.ParamBundle;
import exception.DefaultedValueException;
import exception.NoSuchDelimiterException;
import exception.ParseException;
import exception.IllegalAnnotationException;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.*;
import java.util.*;

import static util.AnnotationUtils.*;
import static util.StringUtils.splitAfterFirst;
import static util.StringUtils.permute;
import static util.ObjectUtils.toObject;
import static util.ObjectUtils.toDefaultValue;

/**
 * This class's protected and private methods are responsible for carrying out the runtime annotation processing for
 * elements annotated with annotation.@Controller, annotation.@Command, and annotation.@Parameter.
 *
 * This class can be used to access the configured ReflectionMap, and the IConsole interface.
 */
public final class Configuration {

    /**
     * <summary>The IConsole field.</summary>
     */
    private final IConsole console;

    /**
     * <summary>The ReflectionMap field.</summary>
     */
    private final ReflectionMap map;

    /**
     * <summary>A Set which is used to store copies of the regex patterns;
     * minus the sub-regex patterns derived from any optional arguments.
     *
     * Is used to check for distinctiveness between regex patterns,
     * so that no duplicates are put into the underlying map.</summary>
     */
    private final Set<String> regexSet = new HashSet<>();

    /**
     * This constructor sets this class's IConsole field using the parameterized console,
     * and constructs and sets this class's ReflectionMap field.
     *
     * @param console this IConsole instance will be assigned to this class' IConsole field.
     */
    protected Configuration(IConsole console) {
        this.console = console;
        this.map = new ReflectionMap();
    }

    /**
     * This constructor should not be invoked.
     *
     * @throws UnsupportedOperationException if this constructor is invoked.
     */
    protected Configuration() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "this Constructor is not be used."
        );
    }

    /**
     * The heavy-lifting processing method, it uses the java.reflection api to create regular expressions
     * from the properties of command-annotated methods.
     *
     * @param controllers a collection of instantiated controller-annotated classes to be processed.
     * @throws IllegalAnnotationException in the event of insufficient annotations being present, or in the event of
     * an annotated method not having the void modifier, or in the event of an annotated method not being an instance method,
     * or in the event of the same regex pattern being derived from two or more annotated methods,
     * or in the event of a annotated-method's argument not being a primitive datatype/wrapper class.
     */
    protected void addControllers(final @NotNull Collection<Object> controllers) throws IllegalAnnotationException
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
                        for (Parameter param : method.getParameters()) {
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
                      map.put(regex.toString(), (input) -> {
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
                        map.append(key, (input) -> console.printerr(getNoMatch(method)));
                    }
                }
            }
        }
    }

    /**
     * GET method for this class's IConsole field.
     *
     * @return the IConsole instance.
     */
    public IConsole console() {
        return console;
    }

    /**
     * GET method for this class's ReflectionMap field.
     *
     * @return the ReflectionMap instance.
     */
    public ReflectionMap map() {
        return map;
    }

    /**
     * When a match occurs at runtime this method is called,
     * the annotated-method's arguments are then parsed and instantiated from the input string,
     * and then the annotated-method is invoked.
     *
     * @param methodBundle object which holds a bundle of information pertaining to the to be invoked annotated-method.
     * @param input a user input.
     * @throws Exception in the event of an unforeseen failure in the invocation of the annotated-method.
     */
    private void invoke(@NotNull MethodBundle methodBundle, String input)
            throws Exception
    {
        Object[] args = new Object[methodBundle.getParams().length];
        Object arg = null;

        if (methodBundle.getObject().getClass().getSuperclass() == BaseController.class) {
            ((BaseController) methodBundle.getObject()).invokeState()
                    .clear();
        }

        int i = 0;
        for (ParamBundle paramBundle : methodBundle.getParams()) {
            try
            {
                arg = toObject(paramBundle.getType(), splitAfterFirst(input, paramBundle.getName()));
            }
            catch (ParseException | NoSuchDelimiterException ex) {
                arg = toDefaultValue(paramBundle.getType());

                if (methodBundle.getObject().getClass().getSuperclass() == BaseController.class) {
                    ((BaseController) methodBundle.getObject()).invokeState()
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