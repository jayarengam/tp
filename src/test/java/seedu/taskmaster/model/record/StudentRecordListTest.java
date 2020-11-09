package seedu.taskmaster.model.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.taskmaster.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.exceptions.DuplicateStudentException;
import seedu.taskmaster.model.student.exceptions.StudentNotFoundException;
import seedu.taskmaster.testutil.TypicalStudents;

public class StudentRecordListTest {
    private final Student studentInList = TypicalStudents.ALICE;
    private final StudentRecord markedStudentRecordOfStudentInList = new StudentRecord(
            studentInList.getName(),
            studentInList.getNusnetId(),
            AttendanceType.PRESENT,
            new ClassParticipation());
    private final StudentRecord scoredStudentRecordOfStudentInList = new StudentRecord(
            studentInList.getName(),
            studentInList.getNusnetId(),
            AttendanceType.NO_RECORD,
            new ClassParticipation(2.2));
    private final StudentRecord scoredAndMarkedStudentRecordOfStudentInList = new StudentRecord(
            studentInList.getName(),
            studentInList.getNusnetId(),
            AttendanceType.PRESENT,
            new ClassParticipation(2.2));

    private final Student studentNotInList = TypicalStudents.BENSON;
    private final StudentRecord studentRecordOfStudentNotInList = new StudentRecord(
            studentNotInList.getName(),
            studentNotInList.getNusnetId(),
            AttendanceType.PRESENT,
            new ClassParticipation(4.3));

    private final StudentRecord studentRecordOfAbsentStudentInList = new StudentRecord(
            studentInList.getName(),
            studentInList.getNusnetId(),
            AttendanceType.ABSENT,
            new ClassParticipation());

    private final StudentRecordList studentRecordList =
            StudentRecordListManager.of(Collections.singletonList(studentInList));

    @Test
    public void markStudentAttendance_idInList_success() {
        studentRecordList.markStudentAttendance(studentInList.getNusnetId(), AttendanceType.PRESENT);

        List<StudentRecord> expectedStudentRecords = Collections.singletonList(markedStudentRecordOfStudentInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void markStudentAttendance_idNotInList_failure() {
        assertThrows(StudentNotFoundException.class, ()
            -> studentRecordList.markStudentAttendance(
                studentNotInList.getNusnetId(), AttendanceType.PRESENT));
    }

    @Test
    public void markAllStudents_idsInList_success() {
        studentRecordList.markAllStudentAttendances(AttendanceType.PRESENT);

        List<StudentRecord> expectedStudentRecords = Collections.singletonList(markedStudentRecordOfStudentInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void scoreStudentAttendance_idInList_success() {
        studentRecordList.scoreStudentParticipation(studentInList.getNusnetId(), 2.2);

        List<StudentRecord> expectedStudentRecords = Collections.singletonList(scoredStudentRecordOfStudentInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void scoreStudentAttendance_idNotInList_failure() {
        assertThrows(StudentNotFoundException.class, ()
            -> studentRecordList.scoreStudentParticipation(
                studentNotInList.getNusnetId(), 4.3));
    }

    @Test
    public void scoreAllStudents_idsInList_success() {
        studentRecordList.markStudentAttendance(studentInList.getNusnetId(), AttendanceType.PRESENT);
        studentRecordList.scoreAllParticipation(2.2);

        List<StudentRecord> expectedStudentRecords = Collections.singletonList(
                scoredAndMarkedStudentRecordOfStudentInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void scoreAllStudents_absentStudentNotScored_success() {
        StudentRecordList listWithAbsentStudent = StudentRecordListManager.of(
                Collections.singletonList(studentInList));
        listWithAbsentStudent.markStudentAttendance(studentInList.getNusnetId(), AttendanceType.ABSENT);
        listWithAbsentStudent.scoreAllParticipation(5);

        List<StudentRecord> expectedStudentRecords = Collections.singletonList(studentRecordOfAbsentStudentInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        assertEquals(listWithAbsentStudent, expectedList);
    }

    @Test
    public void setStudentRecords_nullStudentRecordListManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> studentRecordList.setStudentRecords((StudentRecordListManager) null));
    }

    @Test
    public void setStudentRecords_studentRecordList_replacesOwnListWithProvidedStudentRecordList() {
        List<StudentRecord> expectedStudentRecords = Collections.singletonList(studentRecordOfStudentNotInList);
        StudentRecordListManager expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        studentRecordList.setStudentRecords(expectedList);
        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void setStudentRecords_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentRecordList.setStudentRecords((List<StudentRecord>) null));
    }

    @Test
    public void setStudentRecords_list_replacesOwnListWithProvidedList() {
        List<StudentRecord> expectedStudentRecords = Collections.singletonList(studentRecordOfStudentNotInList);
        StudentRecordList expectedList = new StudentRecordListManager();
        expectedList.setStudentRecords(expectedStudentRecords);

        studentRecordList.setStudentRecords(expectedStudentRecords);
        assertEquals(studentRecordList, expectedList);
    }

    @Test
    public void setStudentRecords_listWithDuplicateStudentRecords_throwsDuplicateStudentException() {
        List<StudentRecord> listWithDuplicateStudentRecords =
                Arrays.asList(markedStudentRecordOfStudentInList, markedStudentRecordOfStudentInList);

        assertThrows(DuplicateStudentException.class, ()
            -> studentRecordList.setStudentRecords(listWithDuplicateStudentRecords));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> studentRecordList.asUnmodifiableObservableList().remove(0));
    }
}
