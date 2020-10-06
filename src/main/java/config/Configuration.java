package config;

import data.Controller;
import data.MethodBundle;
import data.ParamBundle;
import exception.DefaultedValueException;
import exception.NoSuchDelimiterException;
import exception.ParseException;
import iface.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import static util.AnnotationUtils.*;
import static util.StringUtils.splitAfterFirst;
import static util.StringUtils.permute;
import static util.ObjectUtils.toObject;
import static util.ObjectUtils.toDefaultValue;

/**
 * <summary>Class is responsible for initialization, and configuration of the the underlying ReflectionMap --
 * consisting of String, IReflection entries. Also holds references for both the IConsole, and ReflectionMap.
 * </summary>
 */
public final class Configuration implements IConfiguration {

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
     * Is used to check for distinctiveness between regex patterns,
     * so that no duplicates are put into the underlying map.</summary>
     */
    private final Set<String> regexSet = new HashSet<>();

    /**
     * Constructor, which initializes the IConsole, and ReflectionMap fields.
     *
     * @param console the IConsole instance to be assigned to the console field.
     */
    public Configuration(IConsole console) {
        this.console = console;
        this.map = new ReflectionMap();
    }

    /**
     * Default Constructor, not to be invoked.
     *
     * @throws UnsupportedOperationException if Constructor is invoked.
     */
    public Configuration() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "this Constructor is not be used."
        );
    }

    /**
     * <summary>The heavy-lifting config method, which uses the java.reflection api to create regex
     * patterns from the 'properties' of annotated instance methods belonging to the classes passed
     * to this method as an argument. </summary>
     *
     * @param controllers the collection of classes containing annotated methods to be configured.
     * @throws IllegalArgumentException if insufficient annotations are present, or if
     * an annotated method does not have the modifier void, or if an annotated method is a class method,
     * or if the same regex pattern was derived from two or more annotated methods.
     */
    @Override
    public void addControllers(final @NotNull Collection<Object> controllers) throws IllegalArgumentException {
        for (Object object : controllers) {
            Class<?> objectClass = object.getClass();

            if (!hasAnnotation(objectClass))
                throw new IllegalArgumentException(
                        "class is not annotated with @Controller."
                );

            for (Method method : objectClass.getDeclaredMethods()) {
                method.setAccessible(true);
                StringBuilder regex = new StringBuilder();
                StringBuilder setRegex = new StringBuilder();

                if (isStatic(method)) {
                    throw new IllegalArgumentException(
                            "method annotated with @Command may not be static."
                    );
                }

                if (!isVoid(method)) {
                    throw new IllegalArgumentException(
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
                                throw new IllegalArgumentException(
                                        "parameter of method annotated with @Command must be primitive."
                                );
                            }

                            if (isOptional(param)) {
                                args.add("(" + "\\s" + getKeyword(param) + "\\s" +
                                        defaultValueRegex + "|" + ")");
                            }
                            else {
                                args.add("\\s" + getKeyword(param) + "\\s" +
                                        defaultValueRegex);
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
                        throw new IllegalArgumentException(
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

                    if (hasPartialMatchMessage(method)) {
                        String key = "^" + (hasIgnoreKeyword(objectClass) ? "" : getKeyword(objectClass)
                                .concat("\\s")).concat(getKeyword(method)).concat("(|.*)$");
                        map.append(key, (input) -> console.printerr(getPartialMatchMessage(method)));
                    }
                }
            }
        }
    }

    /**
     * <summary>Get method for this class's IConsole instance.</summary>
     *
     * @return the IConsole instance.
     */
    public IConsole console() {
        return console;
    }

    /**
     * <summary>GET method for this class's ReflectionMap instance.</summary>
     *
     * @return the ReflectionMap instance.
     */
    public ReflectionMap map() {
        return map;
    }

    /**
     * <summary>Invokes the method passed to it as an argument,
     * with the proper argument values, parsed from this method's argument input String.</summary>
     *
     * @param methodBundle object which holds a bundle of information pertaining to the Method to be invoked.
     * @param input the user input.
     * @throws Exception if the invocation of the underlying method fails.
     */
    private void invoke(@NotNull MethodBundle methodBundle, String input)
            throws Exception
    {
        Object[] args = new Object[methodBundle.getParams().length];
        Object element = null;

        if (methodBundle.getObject().getClass().getSuperclass() == Controller.class) {
            ((Controller) methodBundle.getObject()).invokeState()
                    .clear();
        }

        int i = 0;
        for (ParamBundle paramBundle : methodBundle.getParams()) {
            try {
                element = toObject(paramBundle.getType(), splitAfterFirst(input, paramBundle.getName()));
            }

            catch (ParseException | NoSuchDelimiterException ex) {
                element = toDefaultValue(paramBundle.getType());

                if (methodBundle.getObject().getClass().getSuperclass() == Controller.class) {
                    ((Controller) methodBundle.getObject()).invokeState()
                            .append(paramBundle.getName(), ex)
                            .append(paramBundle.getName(), new DefaultedValueException
                                    ("'" + paramBundle.getName() + "' was defaulted."));
                }
            }

            catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            finally {
                args[i++] = element;
            }
        }
        methodBundle.getMethod().invoke(methodBundle.getObject(), args);
    }
}