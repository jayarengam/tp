package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.model.Model.PREDICATE_SHOW_ALL_STUDENT_RECORDS;

import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.session.exceptions.SessionException;

/**
 * Lists all student records in the student record list to the user.
 */
public class ListRecordsCommand extends Command {
    public static final String COMMAND_WORD = "list-records";

    public static final String MESSAGE_SUCCESS = "Listed all student records";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.updateFilteredStudentRecordList(PREDICATE_SHOW_ALL_STUDENT_RECORDS);
        } catch (SessionException se) {
            throw new CommandException(se.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
