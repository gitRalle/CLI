package iface;

/**
 * This interface declares abstract methods for printing, and reading text to and from a console.
 */
public interface IConsole
{
    /**
     * Println the specified text to the implemented console.
     * @param text the text to be println.
     */
    void println(String text);

    /**
     * Println the specified error text to the implemented console.
     * @param text the error text to be println.
     */
    void printerr(String text);

    /**
     * Reads the last line of text from the implemented console.
     * @return the last line of text.
     */
    String read();
}
