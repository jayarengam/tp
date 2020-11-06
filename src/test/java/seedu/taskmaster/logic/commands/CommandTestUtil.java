package seedu.taskmaster.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_ATTENDANCE_TYPE;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_CLASS_PARTICIPATION;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_NUSNETID;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_DATE_TIME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.taskmaster.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.student.NameContainsKeywordsPredicate;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TELEGRAM_AMY = "11111111";
    public static final String VALID_TELEGRAM_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_NUSNETID_AMY = "e0123456";
    public static final String VALID_NUSNETID_BOB = "e0456789";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_SESSION_NAME = "Tutorial 1";
    public static final String VALID_SESSION_DATETIME = "23-10-2020 0900";
    public static final String PRESENT_ATTENDANCE = "present";
    public static final String ABSENT_ATTENDANCE = "absent";
    public static final String VALID_SCORE_STRING = "0.00";
    public static final double VALID_SCORE_DOUBLE = 0;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TELEGRAM_DESC_AMY = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_AMY;
    public static final String TELEGRAM_DESC_BOB = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String NUSNETID_DESC_AMY = " " + PREFIX_NUSNETID + VALID_NUSNETID_AMY;
    public static final String NUSNETID_DESC_BOB = " " + PREFIX_NUSNETID + VALID_NUSNETID_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ATTENDANCE_DESC_PRESENT = " " + PREFIX_ATTENDANCE_TYPE + PRESENT_ATTENDANCE;
    public static final String ATTENDANCE_DESC_ABSENT = " " + PREFIX_ATTENDANCE_TYPE + ABSENT_ATTENDANCE;
    public static final String NEW_SESSION_NAME_DESC = " " + PREFIX_SESSION_NAME + VALID_SESSION_NAME;
    public static final String NEW_SESSION_DATETIME_DESC = " " + PREFIX_SESSION_DATE_TIME + VALID_SESSION_DATETIME;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TELEGRAM_DESC = " " + PREFIX_TELEGRAM + "911@"; // '@' not allowed in telegram
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_NUSNETID; // empty string not allowed for nusnetid
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ATTENDANCE_DESC = " " + PREFIX_ATTENDANCE_TYPE + "notAttendanceType";
    public static final String INVALID_PARTICIPATION_SCORE = " " + PREFIX_CLASS_PARTICIPATION + "-1";
    public static final String INVALID_PARTICIPATION_TOOSMALL = " " + PREFIX_CLASS_PARTICIPATION + "-1";
    public static final String INVALID_PARTICIPATION_NEGATIVEZERO = " " + PREFIX_CLASS_PARTICIPATION + "-0";
    public static final String INVALID_PARTICIPATION_TOOBIG = " " + PREFIX_CLASS_PARTICIPATION + "10.1";
    public static final String INVALID_PARTICIPATION_NONINTEGER = " " + PREFIX_CLASS_PARTICIPATION + "NOTASCORE";
    public static final String INVALID_SESSION_NAME_DESC = " " + PREFIX_SESSION_NAME + "****"; // '*' not allowed

    // refer to {@code AttendanceType} for valid attendance types

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String PREAMBLE_ALL = "all";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegram(VALID_TELEGRAM_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_NUSNETID_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_NUSNETID_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the student list, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Taskmaster expectedTaskmaster = new Taskmaster(actualModel.getTaskmaster());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTaskmaster, actualModel.getTaskmaster());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s student list.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
