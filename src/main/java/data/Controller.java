package data;

public abstract class Controller {

    private final InvokeState invokeState = new InvokeState();

    public InvokeState invokeState() {
        return invokeState;
    }
}
