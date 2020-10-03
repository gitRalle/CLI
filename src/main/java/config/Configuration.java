package config;

import data.Controller;
import data.MethodBundle;
import data.ParamBundle;
import exception.DefaultedValueException;
import exception.NoSuchDelimiterException;
import exception.ParseException;
import iface.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;

import static util.AnnotationUtils.*;
import static util.StringUtils.splitAfterFirst;
import static util.StringUtils.permute;
import static util.ObjectUtils.toObject;
import static util.ObjectUtils.toDefaultValue;

public final class Configuration implements IConfiguration {

    private final IConsole console;
    private final ReflectionMap<String, IReflection> map;

    private final Set<String> set = new HashSet<>();

    public Configuration(IConsole console) {
        this.console = console;
        this.map = new ReflectionMap<>();
    }

    @Override
    public void addControllers(final @NotNull Collection<Object> controllers) {
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

                    if (!set.add(setRegex.toString())) {
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

                    if (hasArgsDoNotMatchMessage(method)) {
                        String key = "^" + (hasIgnoreKeyword(objectClass) ? "" : getKeyword(objectClass)
                                .concat("\\s")).concat(getKeyword(method)).concat("(|.*)$");
                        map.append(key, (input) -> console.printerr(getArgsDoNotMatchMessage(method)));
                    }
                }
            }
        }
    }

    public IConsole console() {
        return console;
    }

    public ReflectionMap<String, IReflection> map() {
        return map;
    }

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

            catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            finally {
                args[i++] = element;
            }
        }
        methodBundle.getMethod().invoke(methodBundle.getObject(), args);
    }
}