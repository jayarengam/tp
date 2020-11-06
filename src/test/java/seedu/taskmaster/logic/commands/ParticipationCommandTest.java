package seedu.taskmaster.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_DOUBLE;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_STRING;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskmaster.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.taskmaster.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalTaskmaster;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ModelManager;
import seedu.taskmaster.model.UserPrefs;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.SessionName;

class ParticipationCommandTest {

    private Model model = new ModelManager(getTypicalTaskmaster(), new UserPrefs());

    @Test
    public void execute_participationWithValidIndex_success() {
        model.changeSession(new SessionName("Typical session"));
        StudentRecord firstStudentRecord = model
                .getFilteredStudentRecordList()
                .get(INDEX_FIRST_STUDENT.getZeroBased());

        Model expectedModel = new ModelManager(getTypicalTaskmaster(), new UserPrefs());
        expectedModel.changeSession(new SessionName("Typical session"));
        ParticipationCommand participationCommand = new ParticipationCommand(INDEX_FIRST_STUDENT, VALID_SCORE_DOUBLE);
        expectedModel.scoreStudent(firstStudentRecord, VALID_SCORE_DOUBLE);

        String expectedMessage = String.format(ParticipationCommand.MESSAGE_SCORE_STUDENT_SUCCESS,
                firstStudentRecord.getName(), Double.parseDouble(VALID_SCORE_STRING));
        assertCommandSuccess(participationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexTooLarge_failure() {
        model.changeSession(new SessionName("Typical session"));
        int numOfStudents = model.getFilteredStudentList().size();
        Index indexTooBig = Index.fromZeroBased(numOfStudents);
        ParticipationCommand participationCommand = new ParticipationCommand(indexTooBig, VALID_SCORE_DOUBLE);
        assertCommandFailure(participationCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptySessionList_exceptionThrown() {
        model.setSessions(new ArrayList<>());
        ParticipationCommand participationCommand = new ParticipationCommand(INDEX_FIRST_STUDENT, VALID_SCORE_DOUBLE);
        String expectedMessage = "There are no sessions yet!";
        assertCommandFailure(participationCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullCurrentSession_exceptionThrown() {
        ParticipationCommand participationCommand = new ParticipationCommand(INDEX_FIRST_STUDENT, VALID_SCORE_DOUBLE);
        String expectedMessage = "Please select a session first!";
        assertCommandFailure(participationCommand, model, expectedMessage);
    }

    @Test
    void testEquals() {
        ParticipationCommand partCommand1 = new ParticipationCommand(INDEX_FIRST_STUDENT, VALID_SCORE_DOUBLE);
        ParticipationCommand partCommand2 = new ParticipationCommand(INDEX_FIRST_STUDENT, VALID_SCORE_DOUBLE);
        ParticipationCommand partCommand3 = new ParticipationCommand(INDEX_SECOND_STUDENT, VALID_SCORE_DOUBLE);
        ParticipationCommand partCommand4 = new ParticipationCommand(INDEX_SECOND_STUDENT, VALID_SCORE_DOUBLE + 1);
        assertTrue(partCommand1.equals(partCommand1));
        assertTrue(partCommand1.equals(partCommand2));
        assertFalse(partCommand1.equals(partCommand3));
        assertFalse(partCommand3.equals(partCommand4));
        assertFalse(partCommand1.equals(partCommand4));
    }
}
