package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_NEGATIVEZERO;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_NONINTEGER;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_TOOBIG;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_TOOSMALL;
import static seedu.taskmaster.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.PREAMBLE_ALL;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_DOUBLE;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_STRING;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_CLASS_PARTICIPATION;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskmaster.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.logic.commands.ParticipationAllCommand;
import seedu.taskmaster.logic.commands.ParticipationCommand;

class ParticipationCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParticipationCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INPUT_TOO_SMALL =
            "Invalid input: Negative score. Score needs to be between 0 to 10 inclusive.";
    private static final String MESSAGE_INVALID_INPUT_TOO_BIG =
            "Invalid input: Score is greater than 10. Score needs to be between 0 to 10 inclusive.";
    private static final String MESSAGE_INVALID_NEGATIVE_ZERO =
            "Invalid input: Do not put - before zero.";

    private ParticipationCommandParser parser = new ParticipationCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no score specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no score specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAttendanceType_failure() {
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_NONINTEGER, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_TOOSMALL, MESSAGE_INVALID_INPUT_TOO_SMALL);
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_TOOBIG, MESSAGE_INVALID_INPUT_TOO_BIG);
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_NEGATIVEZERO, MESSAGE_INVALID_NEGATIVE_ZERO);
    }

    @Test
    public void parse_markOneStudent_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String inputScore = targetIndex.getOneBased() + " "
                + PREFIX_CLASS_PARTICIPATION + VALID_SCORE_STRING;
        ParticipationCommand expectedCommandPresent = new ParticipationCommand(targetIndex, VALID_SCORE_DOUBLE);
        assertParseSuccess(parser, inputScore, expectedCommandPresent);
    }

    @Test
    public void parse_markAllStudents_success() {
        String inputScore = PREAMBLE_ALL + " "
                + PREFIX_CLASS_PARTICIPATION + VALID_SCORE_STRING;
        ParticipationCommand expectedCommandPresent = new ParticipationAllCommand(VALID_SCORE_DOUBLE);
        assertParseSuccess(parser, inputScore, expectedCommandPresent);
    }
}
