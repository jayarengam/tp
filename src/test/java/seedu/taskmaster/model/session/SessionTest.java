package seedu.taskmaster.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.testutil.Assert.assertThrows;
import static seedu.taskmaster.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecordList;
import seedu.taskmaster.model.record.StudentRecordListManager;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.exceptions.StudentNotFoundException;
import seedu.taskmaster.testutil.StudentBuilder;
import seedu.taskmaster.testutil.TypicalStudents;

public class SessionTest {

    private final List<Student> typicalStudents = TypicalStudents.getTypicalStudents();
    private final Student firstStudent = typicalStudents.get(INDEX_FIRST_STUDENT.getZeroBased());
    private final Student lastStudent = typicalStudents.get(typicalStudents.size() - 1);

    private final StudentRecordList typicalClearStudentRecordList = StudentRecordListManager.of(typicalStudents);

    private final Session typicalSessionByStudentList = new Session(
            new SessionName("Typical Session"),
            new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
            typicalStudents);

    private final Session typicalSessionByStudentRecordList = new Session(
            new SessionName("Typical Session"),
            new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
            typicalClearStudentRecordList);

    @Test
    public void constructor() {
        assertEquals(typicalSessionByStudentList, typicalSessionByStudentRecordList);
    }

    @Test
    public void getSessionName() {
        assertEquals(new SessionName("Typical Session"), typicalSessionByStudentList.getSessionName());
        assertEquals(new SessionName("Typical Session"), typicalSessionByStudentRecordList.getSessionName());
    }

    @Test
    public void getSessionDateTime() {
        assertEquals(new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalSessionByStudentList.getSessionDateTime());
        assertEquals(new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalSessionByStudentRecordList.getSessionDateTime());
    }

    @Test
    public void getStudentRecords() {
        assertEquals(typicalClearStudentRecordList.asUnmodifiableObservableList(),
                typicalSessionByStudentList.getStudentRecords());
        assertEquals(typicalClearStudentRecordList.asUnmodifiableObservableList(),
                typicalSessionByStudentRecordList.getStudentRecords());
    }

    @Test
    public void clearAttendance() {
        StudentRecordList markedStudentRecordList = StudentRecordListManager.of(typicalStudents);
        markedStudentRecordList.markStudentAttendance(firstStudent.getNusnetId(), AttendanceType.PRESENT);
        markedStudentRecordList.markStudentAttendance(lastStudent.getNusnetId(), AttendanceType.ABSENT);

        Session sessionWithMarkedRecords = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                markedStudentRecordList);

        sessionWithMarkedRecords.clearAttendance();
        assertEquals(sessionWithMarkedRecords, typicalSessionByStudentRecordList);
    }

    @Test
    public void updateStudentRecords_validRecords_success() {
        StudentRecordList markedStudentRecordList = StudentRecordListManager.of(typicalStudents);
        markedStudentRecordList.markStudentAttendance(firstStudent.getNusnetId(), AttendanceType.PRESENT);
        markedStudentRecordList.markStudentAttendance(lastStudent.getNusnetId(), AttendanceType.ABSENT);

        Session sessionWithMarkedRecords = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                markedStudentRecordList);

        Session sessionToUpdate = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalClearStudentRecordList);

        sessionToUpdate.updateStudentRecords(markedStudentRecordList.asUnmodifiableObservableList());
        assertEquals(sessionWithMarkedRecords, sessionToUpdate);
    }

    @Test
    public void updateStudentRecords_extraStudent_exceptionThrown() {
        List<Student> biggerStudentList = new ArrayList<>(typicalStudents);
        biggerStudentList.add(new StudentBuilder().withName("Extra student").withNusnetId("e0999999").build());
        StudentRecordList biggerStudentRecordList = StudentRecordListManager.of(biggerStudentList);

        Session sessionToUpdate = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalClearStudentRecordList);

        assertThrows(StudentNotFoundException.class, ()
            -> sessionToUpdate.updateStudentRecords(biggerStudentRecordList.asUnmodifiableObservableList()));
    }

    @Test
    public void equals_sameSession_returnTrue() {
        assertTrue(typicalSessionByStudentList.equals(typicalSessionByStudentList));
        assertTrue(typicalSessionByStudentRecordList.equals(typicalSessionByStudentRecordList));
    }

    @Test
    public void equals_differentSessionName_returnFalse() {
        Session differentSessionByStudentList = new Session(
                new SessionName("Different Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalStudents);
        Session differentSessionByStudentRecordList = new Session(
                new SessionName("Different Session"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                typicalClearStudentRecordList);
        assertFalse(typicalSessionByStudentList.equals(differentSessionByStudentList));
        assertFalse(typicalSessionByStudentRecordList.equals(differentSessionByStudentRecordList));
    }

    @Test
    public void equals_differentSessionDateTime_returnFalse() {
        Session differentSessionByStudentList = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2019, 11, 1, 10, 30)),
                typicalStudents);
        Session differentSessionByStudentRecordList = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2019, 11, 1, 10, 30)),
                typicalClearStudentRecordList);
        assertFalse(typicalSessionByStudentList.equals(differentSessionByStudentList));
        assertFalse(typicalSessionByStudentRecordList.equals(differentSessionByStudentRecordList));
    }

    @Test
    public void equals_differentStudentRecords_returnFalse() {
        List<Student> differentStudents = new ArrayList<>();
        differentStudents.add(new StudentBuilder().build());
        StudentRecordList differentStudentRecordList = StudentRecordListManager.of(differentStudents);
        Session differentSessionByStudentList = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2019, 11, 1, 10, 30)),
                differentStudents);
        Session differentSessionByStudentRecordList = new Session(
                new SessionName("Typical Session"),
                new SessionDateTime(LocalDateTime.of(2019, 11, 1, 10, 30)),
                differentStudentRecordList);
        assertFalse(typicalSessionByStudentList.equals(differentSessionByStudentList));
        assertFalse(typicalSessionByStudentRecordList.equals(differentSessionByStudentRecordList));
    }

}
