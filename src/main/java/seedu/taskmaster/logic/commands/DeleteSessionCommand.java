package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.session.exceptions.SessionNotFoundException;

/**
 * Deletes a session from the session list.
 */
public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = "delete-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a session from the session list. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "SESSION_NAME...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "CS2103 Tutorial 1";

    public static final String MESSAGE_SUCCESS = "Deleted session: %1$s";
    public static final String MESSAGE_MISSING_SESSION = "There are no sessions in the session list with that name!";

    private final SessionName sessionName;

    /**
     * Creates a DeleteSessionCommand to delete the specified {@code Session}
     */
    public DeleteSessionCommand(SessionName sessionName) {
        requireNonNull(sessionName);
        this.sessionName = sessionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.deleteSession(sessionName);
        } catch (SessionNotFoundException sessionNotFoundException) {
            throw new CommandException(MESSAGE_MISSING_SESSION);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sessionName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof DeleteSessionCommand
                && sessionName.equals(((DeleteSessionCommand) other).sessionName);
    }
}
