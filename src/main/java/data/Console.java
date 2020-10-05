package data;

import iface.IConsole;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * <summary>Class is the 'default' implementation of the IConsole interface,
 * and is usable for console applications.</summary>>
 */
public class Console implements IConsole {

    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public void println(String text) {
        out.println(text);
    }

    @Override
    public void printerr(String text) {
        err.println(text);
    }

    @Override
    public String read()
    {
        return scanner.nextLine();
    }
}