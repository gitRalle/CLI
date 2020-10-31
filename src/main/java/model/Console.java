package model;

import iface.IConsole;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * This class implements methods for printing text to a console using {@link System#out},<br>
 * and reading text from a console using {@link System#in},
 * and an instance of the {@link Scanner} class.
 */
public class Console implements IConsole
{
    private final Scanner scanner;

    /**
     * Constructs a new <code>Console</code> object.
     */
    public Console()
    {
        this.scanner = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     * @param text the text to be println.
     */
    @Override
    public void println(String text) {
        out.println(text);
    }

    /**
     * {@inheritDoc}
     * @param text the error text to be println.
     */
    @Override
    public void printerr(String text) {
        err.println(text);
    }

    /**
     * {@inheritDoc}
     * @return the last line of text.
     */
    @Override
    public String read()
    {
        return scanner.nextLine();
    }

}