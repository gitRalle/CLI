package iface;

/**
 * <summary>Function interface.</summary>
 */
public interface IReflection {

    /**
     * <summary>Abstract Method for invoking the body of a method. The values of the method's
     * arguments are to be parsed from the input.</summary>
     *
     * @param input the user input.
     */
    void invoke(String input);
}
