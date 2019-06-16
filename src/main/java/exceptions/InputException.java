package exceptions;

public class InputException extends Exception {

    public InputException() {
        super("Inputs are invalid!");
    }

    public InputException(String msg) {
        super(msg);
    }
}
