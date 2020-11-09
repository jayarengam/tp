package seedu.taskmaster.model.record;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.exceptions.StudentNotFoundException;

/**
 * Represents a list of student records.
 */
public interface StudentRecordList extends Iterable<StudentRecord> {

    /**
     * Marks the attendance of a student identified by their {@code nusnetId} with given {@code attendanceType}.
     */
    void markStudentAttendance(NusnetId nusnetId, AttendanceType attendanceType);

    /**
     * Marks the attendances of all {@code StudentRecords} with given {@code attendanceType}
     */
    void markAllStudentAttendances(AttendanceType attendanceType);

    /**
     * Updates participation score of a student identified by their {@code nusnetId} to {@code score}.
     */
    void scoreStudentParticipation(NusnetId nusnetId, double score);

    /**
     * Updates the {@code ClassParticipation} of all {@code StudentRecords} which are {@code PRESENT} with the
     * given {@code score}.
     */
    void scoreAllParticipation(double score);

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    void setStudentRecords(StudentRecordListManager replacement);

    /**
     * Replaces the contents of this list with {@code studentRecords}.
     * {@code studentRecords} must not contain records representing the same student.
     */
    void setStudentRecords(List<StudentRecord> studentRecords);

    /**
     * Returns the lowest score amongst all present students in the student list.
     * @throws StudentNotFoundException if there are no present students in the student list.
     */
    double getLowestScore() throws StudentNotFoundException;

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    ObservableList<StudentRecord> asUnmodifiableObservableList();

    @Override
    Iterator<StudentRecord> iterator();
}
