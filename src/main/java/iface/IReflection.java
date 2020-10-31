package iface;

import java.lang.reflect.Method;
/**
 * This functional interface declares the following abstract method: {@link IReflection#invoke(String)}.
 */
public interface IReflection {

    /**
     * Calling this abstract method is meant to initialize the parameters of and start a reflective invocation
     * of an underlying {@link java.lang.reflect.Method}.
     *
     * @param input the values of the underlying method's parameters are meant to be parsed from this string.
     */
    void invoke(String input);
}
