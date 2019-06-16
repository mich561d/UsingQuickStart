package exceptions;

public class HistoryException extends Exception {

    public HistoryException() {
        super("History is empty!");
    }

    public HistoryException(String msg) {
        super(msg);
    }
}
