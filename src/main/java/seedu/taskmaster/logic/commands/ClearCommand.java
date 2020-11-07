package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.Taskmaster;

/**
 * Clears the student list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Student and session list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaskmaster(new Taskmaster());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
