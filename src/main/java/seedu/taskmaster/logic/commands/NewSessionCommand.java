package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_DATE_TIME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.record.StudentRecordList;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;

/**
 * Adds a session to the session list.
 */
public class NewSessionCommand extends Command {

    public static final String COMMAND_WORD = "add-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a session to the session list. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "SESSION_NAME "
            + PREFIX_SESSION_DATE_TIME + "SESSION_DATE_TIME...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "CS2103 Tutorial 1 "
            + PREFIX_SESSION_DATE_TIME + "23-10-2020 0900";

    public static final String MESSAGE_SUCCESS = "New session added: %1$s";
    public static final String MESSAGE_DUPLICATE_SESSION = "Cannot add session:\n"
            + "A session with the same name already exists in the session list";

    private StudentRecordList studentRecordList = null;
    private final SessionName sessionName;
    private final SessionDateTime sessionDateTime;

    /**
     * Creates a NewSessionCommand to add the specified {@code Session}
     */
    public NewSessionCommand(SessionName sessionName, SessionDateTime sessionDateTime) {
        requireNonNull(sessionName);
        requireNonNull(sessionDateTime);
        this.sessionName = sessionName;
        this.sessionDateTime = sessionDateTime;
    }

    public void setStudentRecords(StudentRecordList studentRecords) {
        studentRecordList = studentRecords;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // At the point of execution, student records must be set.
        requireNonNull(studentRecordList);

        // If the Taskmaster already has a session with the same name, throw an error
        if (model.hasSession(sessionName)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        Session toAdd = new Session(sessionName, sessionDateTime, studentRecordList);
        model.addSession(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }
        if (studentRecordList == null) {
            return other instanceof NewSessionCommand
                    && sessionName.equals(((NewSessionCommand) other).sessionName)
                    && sessionDateTime.equals(((NewSessionCommand) other).sessionDateTime)
                    && ((NewSessionCommand) other).studentRecordList == null;
        } else {
            return other instanceof NewSessionCommand // instanceof handles nulls
                    && sessionName.equals(((NewSessionCommand) other).sessionName)
                    && sessionDateTime.equals(((NewSessionCommand) other).sessionDateTime)
                    && studentRecordList.equals(((NewSessionCommand) other).studentRecordList);
        }
    }
}
