package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.session.exceptions.SessionException;

public class LowestScoreCommand extends Command {
    public static final String COMMAND_WORD = "lowest-score";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the student(s) with the lowest "
            + "class participation score.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed %1$d students";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.showLowestScoringStudents();
        } catch (SessionException se) {
            throw new CommandException(se.getMessage());
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, model.getFilteredStudentRecordList().size()));
    }
}
