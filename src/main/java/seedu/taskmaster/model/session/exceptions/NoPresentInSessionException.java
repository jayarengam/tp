package seedu.taskmaster.model.session.exceptions;

public class NoPresentInSessionException extends SessionException {
    private final String message = "The session list has no present students!";

    public String getMessage() {
        return message;
    }
}
