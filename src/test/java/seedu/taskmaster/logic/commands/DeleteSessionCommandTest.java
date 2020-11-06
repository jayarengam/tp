package seedu.taskmaster.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskmaster.testutil.Assert.assertThrows;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalStudents;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalTaskmaster;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ModelManager;
import seedu.taskmaster.model.UserPrefs;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;

public class DeleteSessionCommandTest {

    private final SessionName sessionName = new SessionName("Session To Delete");

    private final SessionDateTime sessionDateTime =
            new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30));

    private final Session toDelete = new Session(sessionName, sessionDateTime, getTypicalStudents());

    private final Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());

    @Test
    public void constructor_nullSessionName_exceptionThrown() {
        assertThrows(NullPointerException.class, ()
            -> new DeleteSessionCommand(null));
    }

    @Test
    public void execute_sessionFound_success() {
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(sessionName);
        Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        model.addSession(toDelete);
        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_SUCCESS, sessionName);
        assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_sessionNotFound_exceptionThrown() {
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(toDelete.getSessionName());
        assertCommandFailure(deleteSessionCommand, model, DeleteSessionCommand.MESSAGE_MISSING_SESSION);
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(toDelete.getSessionName());
        assertEquals(deleteSessionCommand, deleteSessionCommand);
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        DeleteSessionCommand deleteSessionCommand1 = new DeleteSessionCommand(toDelete.getSessionName());
        DeleteSessionCommand deleteSessionCommand2 = new DeleteSessionCommand(toDelete.getSessionName());
        assertEquals(deleteSessionCommand1, deleteSessionCommand2);
    }

    @Test
    public void equals_differentSessionName_returnsFalse() {
        DeleteSessionCommand deleteSessionCommand1 = new DeleteSessionCommand(toDelete.getSessionName());
        DeleteSessionCommand deleteSessionCommand2 = new DeleteSessionCommand(new SessionName("Different name"));
        assertNotEquals(deleteSessionCommand1, deleteSessionCommand2);
    }
}
