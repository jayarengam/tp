package seedu.taskmaster.logic.commands;

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

public class ChangeSessionCommandTest {

    private final Session existingSession = new Session(
            new SessionName("Existing Session"),
            new SessionDateTime(LocalDateTime.of(2020, 11, 1, 12, 0)),
            getTypicalStudents());

    @Test
    public void constructor_nullSessionName_exceptionThrown() {
        assertThrows(NullPointerException.class, ()
            -> new ChangeSessionCommand(null));
    }

    @Test
    public void execute_validSession_success() {
        Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        model.addSession(existingSession);
        String expectedMessage = String.format(ChangeSessionCommand.MESSAGE_SUCCESS, existingSession.getSessionName());
        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        expectedModel.addSession(existingSession);
        expectedModel.changeSession(existingSession.getSessionName());
        ChangeSessionCommand changeSessionCommand = new ChangeSessionCommand(existingSession.getSessionName());
        assertCommandSuccess(changeSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sessionNotInModel_exceptionThrown() {
        Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        ChangeSessionCommand changeSessionCommand =
                new ChangeSessionCommand(new SessionName("This session does not exist"));
        assertCommandFailure(changeSessionCommand, model, ChangeSessionCommand.MESSAGE_SESSION_NOT_FOUND);
    }
}
