package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_CLASS_PARTICIPATION;

import java.util.List;

import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.exceptions.SessionException;

/**
 * Marks the participation of a student identified using its displayed index from the student list.
 */
public class ParticipationCommand extends Command {
    public static final String COMMAND_WORD = "score";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Scores the class participation of the student identified by the "
            + "index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CLASS_PARTICIPATION
            + "CLASS_PARTICIPATION (between 0 to 10 inclusive, allows for 2 decimal points) \n"
            + "You may substitute INDEX for the word 'all' to mark all students in the session. \n"
            + "Example 1: " + COMMAND_WORD + " 1 " + PREFIX_CLASS_PARTICIPATION + "7\n"
            + "Example 2: " + COMMAND_WORD + " all " + PREFIX_CLASS_PARTICIPATION + "6\n";

    public static final String MESSAGE_SCORE_STUDENT_SUCCESS = "%1$s scored %2$s for class participation";
    protected final double score;
    private final Index targetIndex;

    /**
     * @param targetIndex of the student in the filtered student list to mark
     * @param newScore as the new score of the student
     */
    public ParticipationCommand(Index targetIndex, double newScore) {
        this.targetIndex = targetIndex;
        this.score = newScore;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        StudentRecord studentToScore;
        try {
            int index = targetIndex.getZeroBased();
            List<StudentRecord> lastShownList = model.getFilteredStudentRecordList();

            if (index >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }

            studentToScore = lastShownList.get(index);
            model.scoreStudent(studentToScore, score);
        } catch (SessionException sessionException) {
            throw new CommandException(sessionException.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SCORE_STUDENT_SUCCESS, studentToScore.getName(), score));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ParticipationCommand)) {
            return false;
        }

        // state check
        ParticipationCommand m = (ParticipationCommand) other;

        return (targetIndex.equals(m.targetIndex))
                && (this.score == m.score);
    }
}

