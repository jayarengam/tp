package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Random;

import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.session.exceptions.SessionException;

public class RandomStudentCommand extends Command {
    public static final String COMMAND_WORD = "random-student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets a random student to call.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed random student";

    private Random random;

    public RandomStudentCommand() {
        this.random = new Random();
    }

    public RandomStudentCommand(Random random) {
        this.random = random;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.showRandomStudent(random);
        } catch (SessionException se) {
            throw new CommandException(se.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
