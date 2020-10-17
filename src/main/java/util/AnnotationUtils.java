package util;

import annotation.Arg;
import annotation.Command;
import annotation.Controller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashSet;

import static util.StringUtils.isEmptyOrNull;

/**
 * This class declares utility methods used by the config.Configuration class for working with the annotations
 * declared in the annotation package.
 */
public class AnnotationUtils {

    /**
     * Evaluates and determines whether the specified class is annotated with the controller annotation.
     *
     * @param controller the class to be evaluated.
     * @return true if the specified class is annotated with the controller annotation,
     * false if the type is not annotated with the controller annotation.
     */
    public static boolean hasAnnotation(Class<?> controller) {
        assert controller != null;
        return controller.isAnnotationPresent(Controller.class);
    }

    /**
     * Evaluates and determines whether the specified method is annotated with the command annotation.
     *
     * @param method the method to be evaluated.
     * @return true if the specified method is annotated with the command annotation,
     * false if the method is not annotated with the command annotation.
     */
    public static boolean hasAnnotation(Method method) {
        assert method != null;
        return method.isAnnotationPresent(Command.class);
    }

    /**
     * Evaluates and determines whether the specified parameter is annotated with the arg annotation.
     *
     * @param param the parameter to be evaluated.
     * @return true if the specified parameter is annotated with the arg annotation,
     * false if the parameter is not annotated with the arg annotation.
     */
    public static boolean hasAnnotation(Parameter param) {
        assert param != null;
        return param.isAnnotationPresent(Arg.class);
    }

    /**
     * Evaluates and determines whether the specified method has the modifier static.
     *
     * @param method the method to be evaluated.
     * @return true if the specified method does have the modifier static,
     * false if the method does not have the modifier static.
     */
    public static boolean isStatic(Method method) {
        assert method != null;
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * Evaluates and determines whether the specified method has the return type void.
     *
     * @param method the method to be evaluated.
     * @return true if the specified method does have the return type void,
     * false if the method does not have the return type void.
     */
    public static boolean isVoid(@NotNull Method method) {
        return method.getReturnType().equals(Void.TYPE);
    }

    /**
     * Evaluates and determines whether the specified class is annotated with the controller annotation and
     * has its annotation field ignoreKeyword flagged as true.
     *
     * @param controller the class to be evaluated.
     * @return true if the specified class is annotated with the controller annotation and
     * has its annotation field ignoreKeyword flagged as true,
     * false if the class is not annotated with the controller annotation, or the class is annotated with the controller
     * annotation and has its annotation field ignoreKeyword flagged as false.
     */
    public static boolean hasIgnoreKeyword(Class<?> controller) {
        assert controller != null;
        return controller.isAnnotationPresent(Controller.class) &&
                controller.getAnnotation(Controller.class).ignoreKeyword();
    }

    /**
     * Evaluates and determines whether the specified parameter is annotated with the arg annotation and has its
     * annotation field optional flagged as true.
     *
     * @param param the parameter to be evaluated.
     * @return true if the specified parameter is annotated with the arg annotation and has its
     * annotation field optional flagged as true,
     * false if the parameter is not annotated with the arg annotation, or the parameter is annotated with the arg
     * annotation and has its annotation field optional flagged as false.
     */
    public static boolean isOptional(Parameter param) {
        assert param != null;
        return param.isAnnotationPresent(Arg.class) &&
                param.getAnnotation(Arg.class).optional();
    }

    /**
     * Evaluates and determines whether a specified method is annotated with the command annotation and has a
     * non-default valued noMatch annotation field.
     *
     * @param method the method to be evaluated.
     * @return true if the specified method is annotated with the command annotation and has its
     * annotation field noMatch with a non-default value,
     * false if the method is not annotated with the command annotation, or the method is annotated with the command
     * annotation and has a non-default valued noMatch annotation field.
     */
    public static boolean hasNoMatch(Method method) {
        assert method != null;
        return method.isAnnotationPresent(Command.class) &&
                !isEmptyOrNull(method.getAnnotation(Command.class).noMatch());
    }

    /**
     * Returns the specified method's noMatch annotation field.
     *
     * @param method the method who's noMatch annotation field is to be returned.
     * @return the noMatch field of the specified method.
     */
    public static @Nullable String getNoMatch(Method method) {
        assert method != null;
        String noMatch = method.getAnnotation(Command.class).noMatch();
        return isEmptyOrNull(noMatch) ? null : noMatch;
    }

    /**
     * Returns the specified class's keyword annotation field if the class is annotated
     * with the controller annotation.
     *
     * @param controller the controller annotated class whose keyword annotation field is to be returned.
     * @return the keyword annotation field of the specified controller annotated class.
     */
    public static String getKeyword(Class<?> controller) {
        assert controller != null && controller.isAnnotationPresent(Controller.class);
        String keyword = controller.getAnnotation(Controller.class).keyword().toLowerCase();
        return isEmptyOrNull(keyword) ? controller.getSimpleName().toLowerCase()
                .split("controller", 2)[0] : keyword;
    }

    /**
     * Returns the specified method's keyword annotation field if the method is annotated
     * with the command annotation.
     *
     * @param method the command annotated method whose keyword annotation field is to be returned.
     * @return the keyword annotation field of the specified command annotated method.
     */
    public static String getKeyword(Method method) {
        assert method != null && method.isAnnotationPresent(Command.class);
        String keyword = method.getAnnotation(Command.class).keyword().toLowerCase();
        return isEmptyOrNull(keyword) ? method.getName().toLowerCase() : keyword;
    }

    /**
     * Returns the specified parameter's keyword annotation field if the parameter is annotated
     * with the arg annotation, otherwise returns the local name of the parameter in lower case.
     *
     * @param param the arg annotated parameter whose keyword annotation field is to be returned.
     * @return the keyword annotation field if the specified parameter is annotated with the arg
     * annotation, otherwise returns the local name of the parameter in lower case.
     */
    public static @NotNull String getKeyword(Parameter param) {
        assert param != null;
        if (hasAnnotation(param)) {
            String keyword = param.getAnnotation(Arg.class).keyword().toLowerCase();
            return isEmptyOrNull(keyword) ? param.getName().toLowerCase() : keyword;
        }
        return param.getName().toLowerCase();
    }

    /**
     * Evaluates whether a specified class is a primitive datatype/wrapper class.
     *
     * @param object the class to be evaluated.
     * @return true if the specified class is a primitive datatype/wrapper class,
     * false if the specified class is not a primitive datatype/wrapper class.
     */
    public static boolean isPrimitive(Class<?> object) {
        assert object != null;
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