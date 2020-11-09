package seedu.taskmaster.model.session.exceptions;

public class NoPresentInSessionException extends SessionException {
    private final String message = "The student record list has no students who are present!";

    public String getMessage() {
        return message;
    }
}
