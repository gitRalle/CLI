package config;

import data.CLIController;
import data.Map;
import data.MethodBundle;
import data.ParamBundle;
import exception.DefaultedValueException;
import exception.NoSuchDelimiterException;
import exception.ParseException;
import iface.*;
import java.lang.reflect.*;
import java.util.*;
import static util.AnnotationUtils.*;
import static util.StringUtils.splitAfterFirst;
import static util.StringUtils.permute;
import static util.ObjectUtils.toObject;
import static util.ObjectUtils.toDefaultValue;

public final class Configuration implements IConfiguration {

    private final IConsole console;
    private final IMap<String, IReflection> map;

    public Configuration(IConsole console) {
        this.console = console;
        this.map = new Map();
    }

    public IConsole getConsole() {
        return console;
    }

    public IMap<String, IReflection> getMap() {
        return map;
    }

    @Override
    public void addControllers(final Collection<Object> controllers) {
        for (Object object : controllers) {
            Class<?> objectClass = object.getClass();

            if (!hasAnnotation(objectClass))
                throw new IllegalArgumentException(
                        "class is not annotated with @Controller."
                );

            if (!hasIgnoreKeyword(objectClass) && !hasDistinctKeywords(objectClass))
                throw new IllegalArgumentException(
                        "class, method annotated with @Controller, @Command " +
                                "must have distinct names/keywords."
                );

            for (Method method : objectClass.getDeclaredMethods()) {
                method.setAccessible(true);
                StringBuilder regex = new StringBuilder();

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

                if (!hasIgnoreKeyword(objectClass)) {
                    regex.append(getKeyword(objectClass)).append("\\s");
                }

                if (hasAnnotation(method)) {
                    regex.append(getKeyword(method));
                    ParamBundle[] params = new ParamBundle[method.getParameterCount()];

                    if (method.getParameterCount() > 0) {
                        LinkedList<String> args = new LinkedList<>();

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
                            }
                            params[i++] = new ParamBundle(param.getType(), getKeyword(param));
                        }
                        regex.append(permute(args));
                    }
                    regex.append("$");
                    map.put(regex.toString(), (input) -> {
                        try {
                            invoke(new MethodBundle(object, method, params), input);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    if (hasNoMatchMessage(method)) {
                        map.put("^" + (hasIgnoreKeyword(objectClass) ?
                                        "" : getKeyword(objectClass).concat("\\s")) + getKeyword(method) + "(|.*)$",
                                (input) -> console.println(getNoMatchMessage(method)));
                    }
                }
            }
        }
    }

    public void invoke(MethodBundle methodBundle, String userInput)
            throws Exception
    {
        Object[] args = new Object[methodBundle.getParams().length];
        Object element = null;

        if (methodBundle.getObject().getClass().getSuperclass() == CLIController.class) {
            ((CLIController) methodBundle.getObject()).argState()
                    .getExceptionListMap().clear();
        }

        int i = 0;
        for (ParamBundle paramBundle : methodBundle.getParams()) {
            try {
                element = toObject(paramBundle.getType(), splitAfterFirst(userInput, paramBundle.getName()));
            }

            catch (ParseException | NoSuchDelimiterException ex) {
                element = toDefaultValue(paramBundle.getType());

                if (methodBundle.getObject().getClass().getSuperclass() == CLIController.class) {
                    ((CLIController) methodBundle.getObject()).argState()
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