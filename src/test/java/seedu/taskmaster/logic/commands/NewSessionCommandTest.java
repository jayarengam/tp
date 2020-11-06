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
import seedu.taskmaster.model.record.StudentRecordListManager;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.testutil.TypicalStudents;

public class NewSessionCommandTest {

    private final SessionName newSessionName = new SessionName("New Session");

    private final SessionDateTime newSessionDateTime =
            new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30));

    private final Session newSession = new Session(newSessionName, newSessionDateTime, getTypicalStudents());

    private final Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());

    @Test
    public void constructor_nullSessionName_exceptionThrown() {
        assertThrows(NullPointerException.class, ()
            -> new NewSessionCommand(null, newSessionDateTime));
    }

    @Test
    public void constructor_nullSessionDateTime_exceptionThrown() {
        assertThrows(NullPointerException.class, ()
            -> new NewSessionCommand(newSessionName, null));
    }

    @Test
    public void execute_validNewSession_success() {
        NewSessionCommand newSessionCommand = new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        expectedModel.addSession(newSession);
        String expectedMessage = String.format(NewSessionCommand.MESSAGE_SUCCESS, newSession);
        assertCommandSuccess(newSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentRecordsNotSet_exceptionThrown() {
        NewSessionCommand newSessionCommand = new NewSessionCommand(newSessionName, newSessionDateTime);
        assertThrows(NullPointerException.class, ()
            -> newSessionCommand.execute(model));
    }

    @Test
    public void execute_sessionAlreadyInTaskmaster_exceptionThrown() {
        Session existingSession = TypicalStudents.getTypicalSession();
        NewSessionCommand newSessionCommand = new NewSessionCommand(
                existingSession.getSessionName(), existingSession.getSessionDateTime());
        newSessionCommand.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertCommandFailure(newSessionCommand, model, NewSessionCommand.MESSAGE_DUPLICATE_SESSION);
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        NewSessionCommand newSessionCommand = new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertEquals(newSessionCommand, newSessionCommand);
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        NewSessionCommand newSessionCommand1 = new NewSessionCommand(newSessionName, newSessionDateTime);
        NewSessionCommand newSessionCommand2 = new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand1.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        newSessionCommand2.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertEquals(newSessionCommand1, newSessionCommand2);
    }

    @Test
    public void equals_differentSessionName_returnsFalse() {
        NewSessionCommand newSessionCommand1 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand1.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        NewSessionCommand newSessionCommand2 =
                new NewSessionCommand(new SessionName("Different name"), newSessionDateTime);
        newSessionCommand2.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertNotEquals(newSessionCommand2, newSessionCommand1);
    }

    @Test
    public void equals_differentSessionDateTime_returnsFalse() {
        NewSessionCommand newSessionCommand1 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand1.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        NewSessionCommand newSessionCommand2 =
                new NewSessionCommand(newSessionName,
                        new SessionDateTime(LocalDateTime.of(2019, 11, 1, 10, 30)));
        newSessionCommand2.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertNotEquals(newSessionCommand2, newSessionCommand1);
    }

    @Test
    public void equals_oneHasNullStudentRecordList_returnsFalse() {
        NewSessionCommand newSessionCommand1 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        NewSessionCommand newSessionCommand2 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        newSessionCommand2.setStudentRecords(StudentRecordListManager.of(getTypicalStudents()));
        assertNotEquals(newSessionCommand2, newSessionCommand1);
    }

    @Test
    public void equals_sameAttributesWithNullRecordLists_returnsTrue() {
        NewSessionCommand newSessionCommand1 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        NewSessionCommand newSessionCommand2 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        assertEquals(newSessionCommand2, newSessionCommand1);
    }

    @Test
    public void equals_differentAttributesWithNullRecordLists_returnsFalse() {
        NewSessionCommand newSessionCommand1 =
                new NewSessionCommand(newSessionName, newSessionDateTime);
        NewSessionCommand newSessionCommand2 =
                new NewSessionCommand(new SessionName("Different name"), newSessionDateTime);
        assertNotEquals(newSessionCommand2, newSessionCommand1);
    }
}
