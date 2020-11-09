package seedu.taskmaster.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.taskmaster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskmaster.testutil.TypicalStudents.getScoredTaskmaster;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalPresentStudentRecords;
import static seedu.taskmaster.testutil.TypicalStudents.markAllAsPresentInTypicalSession;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ModelManager;
import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.UserPrefs;
import seedu.taskmaster.model.record.ScoreEqualsPredicate;
import seedu.taskmaster.model.session.SessionName;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class LowestScoreCommandTest {
    private Taskmaster scoredTaskmaster = getScoredTaskmaster();
    private Model model = new ModelManager(scoredTaskmaster, new UserPrefs());
    private Model expectedModel = new ModelManager(scoredTaskmaster, new UserPrefs());

    @Test
    public void equals() {

        LowestScoreCommand lowestScoreCommand = new LowestScoreCommand();

        // same object -> returns true
        assertTrue(lowestScoreCommand.equals(lowestScoreCommand));

        // different types -> returns false
        assertFalse(lowestScoreCommand.equals(0));

        // null -> returns false
        assertFalse(lowestScoreCommand.equals(null));

    }

    @Test
    public void execute_unscoredSession_allStudentsFound() {
        model.changeSession(new SessionName("Typical session"));
        expectedModel.changeSession(new SessionName("Typical session"));
        markAllAsPresentInTypicalSession(model.getCurrentSession().get());
        markAllAsPresentInTypicalSession(expectedModel.getCurrentSession().get());

        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 7);
        LowestScoreCommand command = new LowestScoreCommand();
        ScoreEqualsPredicate predicate = new ScoreEqualsPredicate(0);
        expectedModel.updateFilteredStudentRecordList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalPresentStudentRecords(), model.getFilteredStudentRecordList());
    }

    @Test
    public void execute_scoredStudentSession_multipleStudentsFound() {
        model.changeSession(new SessionName("Typical session 2"));
        expectedModel.changeSession(new SessionName("Typical session 2"));
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 2);

        LowestScoreCommand command = new LowestScoreCommand();
        ScoreEqualsPredicate predicate = new ScoreEqualsPredicate(4);

        expectedModel.updateFilteredStudentRecordList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredStudentRecordList(), expectedModel.getFilteredStudentRecordList());
    }
}
