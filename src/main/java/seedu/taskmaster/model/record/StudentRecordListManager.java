package seedu.taskmaster.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.util.CollectionUtil;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.exceptions.DuplicateStudentException;
import seedu.taskmaster.model.student.exceptions.StudentNotFoundException;

/**
 * Represents a list of students' attendance.
 */
public class StudentRecordListManager implements StudentRecordList {
    private final ObservableList<StudentRecord> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudentRecord> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Initialises an {@code StudentRecordListManager} with the given {@code students}.
     * The attendance of each student is marked as {@code NO_RECORD}.
     */
    public static StudentRecordList of(List<Student> students) {
        List<StudentRecord> studentRecords = students
                .stream()
                .map(student -> new StudentRecord(student.getName(), student.getNusnetId()))
                .collect(Collectors.toList());

        StudentRecordList studentRecordList = new StudentRecordListManager();
        studentRecordList.setStudentRecords(studentRecords);
        return studentRecordList;
    }

    /**
     * Marks the attendance of a student represented by their {@code nusnetId} with {@code attendanceType}.
     */
    @Override
    public void markStudentAttendance(NusnetId nusnetId, AttendanceType attendanceType) {
        requireAllNonNull(nusnetId, attendanceType);

        boolean isFound = false;

        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);
            if (studentRecord.getNusnetId().equals(nusnetId)) {
                EditStudentRecordDescriptor descriptor = new EditStudentRecordDescriptor();
                descriptor.setAttendanceType(attendanceType);

                StudentRecord markedStudentRecord = createEditedStudentRecord(studentRecord, descriptor);
                internalList.set(i, markedStudentRecord);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Marks the attendance of students represented by the list of {@code nusnetIds} with {@code attendanceType}.
     */
    @Override
    public void markAllStudentAttendances(List<NusnetId> nusnetIds, AttendanceType attendanceType) {
        for (NusnetId nusnetId : nusnetIds) {
            markStudentAttendance(nusnetId, attendanceType);
        }
    }


    /**
     * Updates participation score of a student represented by their {@code nusnetId} to {@code score}.
     */
    @Override
    public void scoreStudentParticipation(NusnetId nusnetId, double score) {
        requireAllNonNull(nusnetId);

        boolean isFound = false;

        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);
            if (studentRecord.getNusnetId().equals(nusnetId)) {
                EditStudentRecordDescriptor descriptor = new EditStudentRecordDescriptor();
                descriptor.setClassParticipation(new ClassParticipation(score));

                StudentRecord markedStudentRecord = createEditedStudentRecord(studentRecord, descriptor);
                internalList.set(i, markedStudentRecord);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Updates participation score of all students in the list of {@code nusnetIds} with {@code attendanceType}.
     */
    @Override
    public void scoreAllParticipation(List<NusnetId> nusnetIds, double score) {
        for (NusnetId nusnetId : nusnetIds) {
            scoreStudentParticipation(nusnetId, score);
        }
    }

    @Override
    public void setStudentRecords(StudentRecordListManager replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code attendances}.
     * {@code attendances} must not contain duplicate students.
     */
    @Override
    public void setStudentRecords(List<StudentRecord> studentRecords) {
        requireAllNonNull(studentRecords);
        if (!studentsAreUnique(studentRecords)) {
            throw new DuplicateStudentException();
        }

        internalList.setAll(studentRecords);
    }

    /**
     * Returns the lowest score amongst all students in the student list.
     */
    @Override
    public double getLowestScore() {
        double lowestScore = Integer.MAX_VALUE;
        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);
            double score = studentRecord.getClassParticipation().getRawScore();

            if (score < lowestScore) {
                lowestScore = score;
            }
        }

        return lowestScore;
    }

    /**
     * Creates and returns a {@code StudentRecord} with the details of {@code studentRecordToEdit}
     * edited with {@code editStudentRecordDescriptor}.
     * Note that the {@code name} and {@code nusnetId} should not be edited.
     */
    private static StudentRecord createEditedStudentRecord(
            StudentRecord studentRecordToEdit,
            StudentRecordListManager.EditStudentRecordDescriptor editStudentRecordDescriptor) {
        assert studentRecordToEdit != null;
        assert editStudentRecordDescriptor.isAnyFieldEdited();

        AttendanceType updatedAttendanceType = editStudentRecordDescriptor.getAttendanceType()
                .orElse(studentRecordToEdit.getAttendanceType());
        ClassParticipation updatedClassParticipation = editStudentRecordDescriptor.getClassParticipation()
                .orElse(studentRecordToEdit.getClassParticipation());

        return new StudentRecord(studentRecordToEdit.getName(), studentRecordToEdit.getNusnetId(),
                updatedAttendanceType, updatedClassParticipation);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    @Override
    public ObservableList<StudentRecord> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<StudentRecord> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        String result = "";

        for (StudentRecord studentRecord : internalList) {
            result += studentRecord.toString() + "\n";
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentRecordListManager // instanceof handles nulls
                && internalList.equals(((StudentRecordListManager) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code studentRecords} contains only unique students.
     */
    private boolean studentsAreUnique(List<StudentRecord> studentRecords) {
        for (int i = 0; i < studentRecords.size() - 1; i++) {
            for (int j = i + 1; j < studentRecords.size(); j++) {
                if (studentRecords.get(i).getNusnetId().equals(studentRecords.get(j).getNusnetId())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a random Student Record from the Student Record List
     * @return A random Student Record
     */
    @Override
    public StudentRecord getRandomStudentRecord() {
        Random random = new Random();
        int index = random.nextInt(internalList.size());
        return internalList.get(index);
    }

    /**
     * Stores the details to edit the student record with. Each non-empty field value will replace the
     * corresponding field value of the student record. Note that the name and nusnetId cannot be changed.
     */
    public static class EditStudentRecordDescriptor {
        private AttendanceType attendanceType;
        private ClassParticipation classParticipation;

        public EditStudentRecordDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditStudentRecordDescriptor(EditStudentRecordDescriptor toCopy) {
            setAttendanceType(toCopy.attendanceType);
            setClassParticipation((toCopy.classParticipation));
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(attendanceType, classParticipation);
        }

        public void setAttendanceType(AttendanceType attendanceType) {
            this.attendanceType = attendanceType;
        }

        public Optional<AttendanceType> getAttendanceType() {
            return Optional.ofNullable(attendanceType);
        }

        public void setClassParticipation(ClassParticipation classParticipation) {
            this.classParticipation = classParticipation;
        }

        public Optional<ClassParticipation> getClassParticipation() {
            return Optional.ofNullable(classParticipation);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof StudentRecordListManager.EditStudentRecordDescriptor)) {
                return false;
            }

            // state check
            StudentRecordListManager.EditStudentRecordDescriptor e =
                    (StudentRecordListManager.EditStudentRecordDescriptor) other;
            return getAttendanceType().equals(e.getAttendanceType())
                    && getClassParticipation().equals(e.getClassParticipation());
        }
    }
}
