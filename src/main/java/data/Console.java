package data;

import iface.IConsole;

import java.util.Scanner;

public class Console implements IConsole {


    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public void printerr(String text) {
        System.out.println(text);
    }

    @Override
    public String read()
    {
        return new Scanner(System.in).nextLine();
    }
}