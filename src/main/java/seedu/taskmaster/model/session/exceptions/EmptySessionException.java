package seedu.taskmaster.model.session.exceptions;

public class EmptySessionException extends SessionException {
    private final String message = "The session list has no students!";

    public String getMessage() {
        return message;
    }
}
