package model;

import iface.IConsole;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * This class declares methods for appending text to a console using java.lang.System.out,
 * and for reading text from a console using java.lang.System.in and a scanner.
 */
public class Console implements IConsole {

    /**
     * This class's scanner object.
     */
    private final static Scanner scanner = new Scanner(System.in);

    /**
     * Appends the specified text to the console.
     * @param text the text to be appended.
     */
    @Override
    public void println(String text) {
        out.println(text);
    }

    /**
     * Appends the specified error text to the console.
     * @param text the error text to be appended.
     */
    @Override
    public void printerr(String text) {
        err.println(text);
    }

    /**
     * Reads and returns the last paragraph of text from the console.
     * @return the last paragraph of text from the console.
     */
    @Override
    public String read()
    {
        return scanner.nextLine();
    }

}