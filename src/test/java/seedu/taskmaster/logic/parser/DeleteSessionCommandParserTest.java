package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.logic.commands.DeleteSessionCommand;
import seedu.taskmaster.model.session.SessionName;

public class DeleteSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE);

    private final DeleteSessionCommandParser parser = new DeleteSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no session name specified
        assertParseFailure(parser, "s/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid session name
        assertParseFailure(parser, "s/****", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validInput_returnsChangeSessionCommand() {
        String input = " s/To Delete";
        DeleteSessionCommand expectedCommand = new DeleteSessionCommand(new SessionName("To Delete"));
        assertParseSuccess(parser, input, expectedCommand);
    }
}
