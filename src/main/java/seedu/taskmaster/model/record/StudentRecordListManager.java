package seedu.taskmaster.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.util.CollectionUtil;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.exceptions.DuplicateStudentException;
import seedu.taskmaster.model.student.exceptions.StudentNotFoundException;

/**
 * Represents a list of student records.
 * A student is identified by their NUSNET ID.
 */
public class StudentRecordListManager implements StudentRecordList {
    private final ObservableList<StudentRecord> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudentRecord> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Initialises a {@code StudentRecordListManager} with the given {@code students}.
     * The attendance of each student is initialised as {@code NO_RECORD}.
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
     * Marks the attendance of a student identified by their {@code nusnetId} with given {@code attendanceType}.
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
     * Marks the attendances of all {@code StudentRecords} with given {@code attendanceType}.
     */
    @Override
    public void markAllStudentAttendances(AttendanceType attendanceType) {
        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);
            EditStudentRecordDescriptor descriptor = new EditStudentRecordDescriptor();
            descriptor.setAttendanceType(attendanceType);

            StudentRecord markedStudentRecord = createEditedStudentRecord(studentRecord, descriptor);
            internalList.set(i, markedStudentRecord);
        }
    }


    /**
     * Updates participation score of a student identified by their {@code nusnetId} to {@code score}.
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
     * Updates the {@code ClassParticipation} of all {@code StudentRecords} which are {@code PRESENT} with the
     * given {@code score}.
     */
    @Override
    public void scoreAllParticipation(double score) {
        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);

            if (studentRecord.getAttendanceType() == AttendanceType.PRESENT) {
                EditStudentRecordDescriptor descriptor = new EditStudentRecordDescriptor();
                descriptor.setClassParticipation(new ClassParticipation(score));

                StudentRecord scoredStudentRecord = createEditedStudentRecord(studentRecord, descriptor);
                internalList.set(i, scoredStudentRecord);
            }
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    @Override
    public void setStudentRecords(StudentRecordListManager replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code studentRecords}.
     * {@code studentRecords} must not contain records representing the same student.
     */
    @Override
    public void setStudentRecords(List<StudentRecord> studentRecords) {
        requireAllNonNull(studentRecords);
        if (!studentsAreUnique(studentRecords)) {
            throw new DuplicateStudentException();
        }

        internalList.setAll(studentRecords);
    }

    @Override
    public double getLowestScore() throws StudentNotFoundException {
        double lowestScore = Double.MAX_VALUE;
        for (int i = 0; i < internalList.size(); i++) {
            StudentRecord studentRecord = internalList.get(i);
            if (studentRecord.getAttendanceType().equals(AttendanceType.PRESENT)) {
                double score = studentRecord.getClassParticipation().getRawScore();

                if (score < lowestScore) {
                    lowestScore = score;
                }
            }
        }

        if (lowestScore == Double.MAX_VALUE) {
            throw new StudentNotFoundException();
        }

        return lowestScore;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
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
     * Creates and returns a {@code StudentRecord} with the details of {@code studentRecordToEdit}
     * edited with {@code editStudentRecordDescriptor}.
     * Note that name and NUSNET ID should not be edited.
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
     * Returns true if {@code studentRecords} contains only records of unique students and no duplicate students.
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
     * Stores the details to edit the student record with. Each non-empty field value will replace the
     * corresponding field value of the student record. Note that name and NUSNET ID cannot be changed.
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
