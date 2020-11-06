package seedu.taskmaster.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_DOUBLE;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_STRING;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalTaskmaster;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ModelManager;
import seedu.taskmaster.model.UserPrefs;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.SessionName;



class ParticipationAllCommandTest {
    private Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());

    @Test
    public void execute_participationAllStudents_success() {
        model.changeSession(new SessionName("Typical session"));
        List<StudentRecord> students = model.getFilteredStudentRecordList();
        ParticipationAllCommand participationAllCommand = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        expectedModel.changeSession(new SessionName("Typical session"));
        expectedModel.scoreAllStudents(students, VALID_SCORE_DOUBLE);
        String expectedMessage = String.format(
                ParticipationAllCommand.MESSAGE_MARK_ALL_SUCCESS, Double.parseDouble(VALID_SCORE_STRING));
        assertCommandSuccess(participationAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySessionList_exceptionThrown() {
        model.setSessions(new ArrayList<>());
        ParticipationAllCommand participationAllCommand = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        String expectedMessage = "There are no sessions yet!";
        assertCommandFailure(participationAllCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullCurrentSession_exceptionThrown() {
        ParticipationAllCommand participationAllCommand = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        String expectedMessage = "Please select a session first!";
        assertCommandFailure(participationAllCommand, model, expectedMessage);
    }

    @Test
    void testEquals() {
        ParticipationAllCommand partCommand1 = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        ParticipationAllCommand partCommand2 = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        ParticipationAllCommand partCommand3 = new ParticipationAllCommand(VALID_SCORE_DOUBLE + 1);
        assertTrue(partCommand1.equals(partCommand1));
        assertTrue(partCommand1.equals(partCommand2));
        assertFalse(partCommand1.equals(partCommand3));
    }
}
