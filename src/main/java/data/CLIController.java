package data;

public abstract class CLIController {

    private final ArgState argState = new ArgState();

    public ArgState argState() {
        return argState;
    }
}
