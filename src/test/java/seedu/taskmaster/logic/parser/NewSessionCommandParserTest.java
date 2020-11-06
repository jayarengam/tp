package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_SESSION_NAME_DESC;
import static seedu.taskmaster.logic.commands.CommandTestUtil.NEW_SESSION_DATETIME_DESC;
import static seedu.taskmaster.logic.commands.CommandTestUtil.NEW_SESSION_NAME_DESC;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SESSION_DATETIME;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SESSION_NAME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_DATE_TIME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.logic.commands.NewSessionCommand;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;

public class NewSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewSessionCommand.MESSAGE_USAGE);

    private NewSessionCommandParser parser = new NewSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no session date time specified
        assertParseFailure(parser, PREFIX_SESSION_NAME + VALID_SESSION_NAME, MESSAGE_INVALID_FORMAT);

        // no session name specified
        assertParseFailure(parser, PREFIX_SESSION_DATE_TIME + VALID_SESSION_DATETIME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid session name
        assertParseFailure(parser, INVALID_SESSION_NAME_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validInput_returnsNewSessionCommand() {
        String input = NEW_SESSION_NAME_DESC + NEW_SESSION_DATETIME_DESC;
        NewSessionCommand expectedCommand = new NewSessionCommand(
                new SessionName(VALID_SESSION_NAME),
                new SessionDateTime(LocalDateTime.of(2020, 10, 23, 9, 0))
        );
        assertParseSuccess(parser, input, expectedCommand);
    }
}
