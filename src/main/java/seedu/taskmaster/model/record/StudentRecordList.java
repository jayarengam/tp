package seedu.taskmaster.model.record;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.taskmaster.model.student.NusnetId;

public interface StudentRecordList extends Iterable<StudentRecord> {

    /**
     * Marks the attendance of a student represented by their {@code nusnetId} with {@code attendanceType}.
     */
    void markStudentAttendance(NusnetId nusnetId, AttendanceType attendanceType);

    /**
     * Marks the attendance of students represented by the list of {@code nusnetIds} with {@code attendanceType}.
     */
    void markAllStudentAttendances(List<NusnetId> nusnetIds, AttendanceType attendanceType);

    /**
     * Updates participation score of a student represented by their {@code nusnetId} to {@code score}.
     */
    void scoreStudentParticipation(NusnetId nusnetId, double score);

    /**
     * Updates participation score of all students in the list of {@code nusnetIds} with {@code attendanceType}.
     */
    void scoreAllParticipation(List<NusnetId> nusnetIds, double score);

    /**
     * Returns the lowest score amongst all students in the student list.
     */
    double getLowestScore();

    void setStudentRecords(StudentRecordListManager replacement);

    /**
     * Replaces the contents of this list with {@code studentRecords}.
     * {@code studentRecords} must not contain duplicate students.
     */
    void setStudentRecords(List<StudentRecord> studentRecords);

    /**
     * Returns a random Student Record from the Student Record List
     * @return A random Student Record
     */
    StudentRecord getRandomStudentRecord();

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    ObservableList<StudentRecord> asUnmodifiableObservableList();

    @Override
    Iterator<StudentRecord> iterator();
}
