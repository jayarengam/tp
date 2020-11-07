package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskmaster.logic.commands.AddCommand;
import seedu.taskmaster.logic.commands.ChangeSessionCommand;
import seedu.taskmaster.logic.commands.ClearCommand;
import seedu.taskmaster.logic.commands.Command;
import seedu.taskmaster.logic.commands.DeleteCommand;
import seedu.taskmaster.logic.commands.DeleteSessionCommand;
import seedu.taskmaster.logic.commands.EditCommand;
import seedu.taskmaster.logic.commands.ExitCommand;
import seedu.taskmaster.logic.commands.FindCommand;
import seedu.taskmaster.logic.commands.HelpCommand;
import seedu.taskmaster.logic.commands.ListRecordsCommand;
import seedu.taskmaster.logic.commands.ListStudentsCommand;
import seedu.taskmaster.logic.commands.LowestScoreCommand;
import seedu.taskmaster.logic.commands.MarkCommand;
import seedu.taskmaster.logic.commands.NewSessionCommand;
import seedu.taskmaster.logic.commands.ParticipationCommand;
import seedu.taskmaster.logic.commands.RandomStudentCommand;
import seedu.taskmaster.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TaskmasterParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListStudentsCommand.COMMAND_WORD:
            return new ListStudentsCommand();

        case ListRecordsCommand.COMMAND_WORD:
            return new ListRecordsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case ParticipationCommand.COMMAND_WORD:
            return new ParticipationCommandParser().parse(arguments);

        case LowestScoreCommand.COMMAND_WORD:
            return new LowestScoreCommand();

        case RandomStudentCommand.COMMAND_WORD:
            return new RandomStudentCommand();

        case NewSessionCommand.COMMAND_WORD:
            return new NewSessionCommandParser().parse(arguments);

        case ChangeSessionCommand.COMMAND_WORD:
            return new ChangeSessionCommandParser().parse(arguments);

        case DeleteSessionCommand.COMMAND_WORD:
            return new DeleteSessionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
