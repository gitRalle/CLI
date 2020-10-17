package iface;

/**
 * This functional interface declares a method for invoking a java method reflectively at runtime.
 */
public interface IReflection {

    /**
     * Calling this method will initialize the underlying method's parameters with values parsed from the
     * specified input, and subsequently invoke the underlying method reflectively.
     *
     * @param input the input appended to a console by a user, the contents of this input will determine which
     *              parameters, and which parameter values are initialized before invocation of the underlying method.
     */
    void invoke(String input);
}
