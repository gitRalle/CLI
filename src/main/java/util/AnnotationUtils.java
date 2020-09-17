package util;

import annotation.Arg;
import annotation.Command;
import annotation.Controller;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

import static util.StringUtils.isEmptyOrNull;

public class AnnotationUtils {

    public static final String defaultValueRegex = "\\S*";

    public static boolean hasAnnotation(Class<?> controller) {
        assert controller != null;
        return controller.isAnnotationPresent(Controller.class);
    }

    public static boolean hasAnnotation(Method method) {
        assert method != null;
        return method.isAnnotationPresent(Command.class);
    }

    public static boolean hasAnnotation(Parameter param) {
        assert param != null;
        return param.isAnnotationPresent(Arg.class);
    }

    public static boolean hasDistinctKeywords(Class<?> controller) {
        assert controller != null;
        Set<String> keywords = new HashSet<>();
        keywords.add(getKeyword(controller));

        for (Method method : controller.getDeclaredMethods()) {
            if (hasAnnotation(method)) {
                if (!keywords.add(getKeyword(method))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isStatic(Method method) {
        assert method != null;
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isVoid(Method method) {
        assert method != null;
        return method.getReturnType().equals(Void.TYPE);
    }

    public static boolean hasIgnoreKeyword(Class<?> controller) {
        assert controller != null;
        return controller.isAnnotationPresent(Controller.class) &&
                controller.getAnnotation(Controller.class).ignoreKeyword();
    }

    public static boolean isOptional(Parameter param) {
        assert param != null;
        return param.isAnnotationPresent(Arg.class) &&
                param.getAnnotation(Arg.class).optional();
    }

    public static boolean hasNoMatchMessage(Method method) {
        assert method != null;
        return method.isAnnotationPresent(Command.class) &&
                !isEmptyOrNull(method.getAnnotation(Command.class).notFoundMessage());
    }

    public static String getNoMatchMessage(Method method) {
        assert method != null;
        String errorMessage = method.getAnnotation(Command.class).notFoundMessage();
        return isEmptyOrNull(errorMessage) ? null : errorMessage;
    }

    public static String getKeyword(Class<?> controller) {
        assert controller != null;
        String keyword = controller.getAnnotation(Controller.class).keyword().toLowerCase();
        return isEmptyOrNull(keyword) ? controller.getSimpleName().toLowerCase()
                .split("controller", 2)[0] : keyword;
    }

    public static String getKeyword(Method method) {
        assert method != null;
        String keyword = method.getAnnotation(Command.class).keyword().toLowerCase();
        return isEmptyOrNull(keyword) ? method.getName().toLowerCase() : keyword;
    }

    public static String getKeyword(Parameter param) {
        assert param != null;
        if (hasAnnotation(param)) {
            String keyword = param.getAnnotation(Arg.class).keyword().toLowerCase();
            return isEmptyOrNull(keyword) ? param.getName().toLowerCase() : keyword;
        }
        return param.getName().toLowerCase();
    }

    public static boolean isPrimitive(Class<?> object) {
        return new HashSet<Class<?>>() {
            {
                add(String.class);
                add(Boolean.class);
                add(Character.class);
                add(Byte.class);
                add(Short.class);
                add(Integer.class);
                add(Long.class);
                add(Float.class);
                add(Double.class);
            }
        }.contains(object) || object.isPrimitive();
    }
}