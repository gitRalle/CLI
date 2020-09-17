package data;

import iface.IConsole;

public class Console implements IConsole {

    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public void printerr(String text) {
        System.out.println(text);
    }

}