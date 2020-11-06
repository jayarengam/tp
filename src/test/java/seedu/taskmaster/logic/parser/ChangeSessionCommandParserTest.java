package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.NEW_SESSION_NAME_DESC;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.logic.commands.ChangeSessionCommand;
import seedu.taskmaster.model.session.SessionName;

public class ChangeSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSessionCommand.MESSAGE_USAGE);

    private final ChangeSessionCommandParser parser = new ChangeSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no session name specified
        assertParseFailure(parser, PREFIX_SESSION_NAME.getPrefix(), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validInput_returnsChangeSessionCommand() {
        String input = NEW_SESSION_NAME_DESC;
        ChangeSessionCommand expectedCommand = new ChangeSessionCommand(new SessionName("Tutorial 1"));
        assertParseSuccess(parser, input, expectedCommand);
    }
}
