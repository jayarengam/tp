package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_CLASS_PARTICIPATION;

import java.util.List;

import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.exceptions.SessionException;

public class ParticipationAllCommand extends ParticipationCommand {
    public static final String COMMAND_WORD = "score all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Scores the class participation of the student identified by the "
            + "index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CLASS_PARTICIPATION
            + "CLASS_PARTICIPATION (between 0 to 10 inclusive, allows for 2 decimal points) \n"
            + "You may substitute INDEX for the word 'all' to mark all students in the session. \n"
            + "Example 1: " + COMMAND_WORD + " 1 " + PREFIX_CLASS_PARTICIPATION + "7\n"
            + "Example 2: " + COMMAND_WORD + " all " + PREFIX_CLASS_PARTICIPATION + "6\n";

    public static final String MESSAGE_MARK_ALL_SUCCESS = "Scored %1$s for all students' participation mark.";

    public ParticipationAllCommand(double score) {
        super(null, score);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            List<StudentRecord> lastShownList = model.getFilteredStudentRecordList();
            model.scoreAllStudents(lastShownList, score);
        } catch (SessionException sessionException) {
            throw new CommandException(sessionException.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_MARK_ALL_SUCCESS, score));
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

        return (this.score == m.score);
    }
}
