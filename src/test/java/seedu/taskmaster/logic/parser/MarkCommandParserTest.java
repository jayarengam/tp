package seedu.taskmaster.logic.parser;

import static seedu.taskmaster.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.ATTENDANCE_DESC_ABSENT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.ATTENDANCE_DESC_PRESENT;
import static seedu.taskmaster.logic.commands.CommandTestUtil.INVALID_ATTENDANCE_DESC;
import static seedu.taskmaster.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.PREAMBLE_ALL;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskmaster.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskmaster.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.logic.commands.MarkAllCommand;
import seedu.taskmaster.logic.commands.MarkCommand;
import seedu.taskmaster.model.record.AttendanceType;

public class MarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no attendance type specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no attendance type specified
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
        assertParseFailure(parser, "1 a/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAttendanceType_failure() {
        assertParseFailure(parser, "1" + INVALID_ATTENDANCE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_markOneStudent_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String inputMarkPresent = targetIndex.getOneBased() + ATTENDANCE_DESC_PRESENT;
        MarkCommand expectedCommandPresent = new MarkCommand(targetIndex, AttendanceType.PRESENT);
        assertParseSuccess(parser, inputMarkPresent, expectedCommandPresent);

        String inputMarkAbsent = targetIndex.getOneBased() + ATTENDANCE_DESC_ABSENT;
        MarkCommand expectedCommandAbsent = new MarkCommand(targetIndex, AttendanceType.ABSENT);
        assertParseSuccess(parser, inputMarkAbsent, expectedCommandAbsent);
    }

    @Test
    public void parse_markAllStudents_success() {
        String inputMarkPresent = PREAMBLE_ALL + ATTENDANCE_DESC_PRESENT;
        MarkCommand expectedCommandPresent = new MarkAllCommand(AttendanceType.PRESENT);
        assertParseSuccess(parser, inputMarkPresent, expectedCommandPresent);

        String inputMarkAbsent = PREAMBLE_ALL + ATTENDANCE_DESC_ABSENT;
        MarkCommand expectedCommandAbsent = new MarkAllCommand(AttendanceType.ABSENT);
        assertParseSuccess(parser, inputMarkAbsent, expectedCommandAbsent);
    }
}
