package iface;

/**
 * <summary>Interface which declares abstract methods which need to be
 * implemented by a Console class.</summary>
 */
public interface IConsole {

    /**
     * <summary>Abstract Method for printing text to a console.</summary>
     *
     * @param text the text to be printed.
     */
    void println(String text);

    /**
     * <summary>Abstract Method for printing error-text to a console.</summary>
     *
     * @param text the text to be printed.
     */
    void printerr(String text);

    /**
     * <summary>Abstract Method for reading from a console.</summary>
     *
     * @return the most recently added line of text from a console.
     */
    String read();
}
