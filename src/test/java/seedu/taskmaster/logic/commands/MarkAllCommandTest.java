package seedu.taskmaster.logic.commands;

import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalTaskmaster;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ModelManager;
import seedu.taskmaster.model.UserPrefs;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.SessionName;

public class MarkAllCommandTest {
    private Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());

    @Test
    public void execute_success() {
        model.changeSession(new SessionName("Typical session"));
        List<StudentRecord> studentRecords = model.getFilteredStudentRecordList();
        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        expectedModel.changeSession(new SessionName("Typical session"));

        MarkAllCommand markAllCommand = new MarkAllCommand(AttendanceType.PRESENT);
        expectedModel.markAllStudents(AttendanceType.PRESENT);
        String expectedMessage = String.format(MarkAllCommand.MESSAGE_MARK_ALL_SUCCESS, AttendanceType.PRESENT);
        assertCommandSuccess(markAllCommand, model, expectedMessage, expectedModel);

        markAllCommand = new MarkAllCommand(AttendanceType.ABSENT);
        expectedModel.markAllStudents(AttendanceType.ABSENT);
        expectedMessage = String.format(MarkAllCommand.MESSAGE_MARK_ALL_SUCCESS, AttendanceType.ABSENT);
        assertCommandSuccess(markAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySessionList_exceptionThrown() {
        model.setSessions(new ArrayList<>());
        MarkAllCommand markAllCommand = new MarkAllCommand(AttendanceType.PRESENT);
        String expectedMessage = "There are no sessions yet!";
        assertCommandFailure(markAllCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullCurrentSession_exceptionThrown() {
        MarkAllCommand markAllCommand = new MarkAllCommand(AttendanceType.PRESENT);
        String expectedMessage = "Please select a session first!";
        assertCommandFailure(markAllCommand, model, expectedMessage);
    }
}
