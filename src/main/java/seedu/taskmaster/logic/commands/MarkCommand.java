package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.logic.parser.CliSyntax.PREFIX_ATTENDANCE_TYPE;

import java.util.List;

import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.exceptions.SessionException;

/**
 * Marks the attendance of a student record identified using its displayed index from the student record list.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks attendance of the student record identified by the "
            + "index number used in the displayed student record list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTENDANCE_TYPE + "ATTENDANCE_TYPE (must be 'present', 'absent' or 'no_record') \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ATTENDANCE_TYPE + "present";
    public static final String MESSAGE_MARK_STUDENT_SUCCESS = "Marked %1$s as %2$s";

    protected final AttendanceType attendanceType;
    private final Index targetIndex;

    /**
     * Instantiates a new {@code MarkCommand}.
     * Each mark command contains a {@code targetIndex} identifying the record in the filtered student record list to
     * mark and an {@code attendanceType} to mark the record with.
     */
    public MarkCommand(Index targetIndex, AttendanceType attendanceType) {
        this.targetIndex = targetIndex;
        this.attendanceType = attendanceType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        StudentRecord studentRecordToMark = null;

        try {
            int index = targetIndex.getZeroBased();
            List<StudentRecord> lastShownList = model.getFilteredStudentRecordList();

            if (index >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }

            studentRecordToMark = lastShownList.get(index);
            model.markStudentRecord(studentRecordToMark, attendanceType);
        } catch (SessionException sessionException) {
            throw new CommandException(sessionException.getMessage());
        }
        return new CommandResult(String.format(
                MESSAGE_MARK_STUDENT_SUCCESS, studentRecordToMark.getNusnetId(), attendanceType));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        // state check
        MarkCommand m = (MarkCommand) other;

        return targetIndex.equals(m.targetIndex)
                && attendanceType.equals(m.attendanceType);
    }
}
