package iface;

/**
 * This interface declares methods for appending text to a console,
 * and for reading text from a console.
 */
public interface IConsole {

    /**
     * Appends the specified text to the console.
     * @param text the text to be appended.
     */
    void println(String text);

    /**
     * Appends the specified error text to the console.
     * @param text the error text to be appended.
     */
    void printerr(String text);

    /**
     * Reads and returns the last paragraph of text from the console.
     * @return the last paragraph of text from the console.
     */
    String read();
}
