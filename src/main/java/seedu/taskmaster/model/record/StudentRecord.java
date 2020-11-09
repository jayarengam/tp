package seedu.taskmaster.model.record;

import seedu.taskmaster.model.student.Name;
import seedu.taskmaster.model.student.NusnetId;

/**
 * Represents a record of a student enrolled in a session.
 */
public class StudentRecord {
    private static final String STRING_FORMAT = "%s|%s|%s";

    /**
     * Each record has a
     * (1) name of student for display purposes.
     * (2) NUSNET ID of student for identification purposes.
     * (3) attendance type to record attendance status of the student in that session.
     * (4) class participation score to track class participation of the student in that session.
     */
    private final Name name;
    private final NusnetId nusnetId;
    private final AttendanceType attendanceType;
    private final ClassParticipation classParticipation;

    /**
     * The student is represented in the record by their {@code nusnetId} and initially has no record of attendance and
     * class participation score.
     */
    public StudentRecord(Name name, NusnetId nusnetId) {
        this.name = name;
        this.nusnetId = nusnetId;
        this.attendanceType = AttendanceType.NO_RECORD;
        this.classParticipation = new ClassParticipation();
    }

    /**
     * Creates an StudentRecord object with the AttendanceType already specified.
     */
    public StudentRecord(Name name, NusnetId nusnetId, AttendanceType attendanceType,
                         ClassParticipation classParticipation) {
        this.name = name;
        this.nusnetId = nusnetId;
        this.attendanceType = attendanceType;
        this.classParticipation = classParticipation;
    }

    /**
     * Returns the {@code name} of the student recorded.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the {@code nusnetId} of the student recorded.
     */
    public NusnetId getNusnetId() {
        return nusnetId;
    }

    /**
     * Returns the {@code attendanceType} of the student recorded.
     */
    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    /**
     * Returns the {@code classParticipation} of the student recorded.
     * @return
     */
    public ClassParticipation getClassParticipation() {
        return classParticipation;
    }

    /**
     * Returns true if the record is representing the same student as the {@code otherRecord}.
     */
    public boolean isSameStudentAs(StudentRecord otherRecord) {
        return this.getNusnetId().equals(otherRecord.getNusnetId());
    }

    /**
     * Returns a string representation of the record.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, nusnetId, attendanceType.name(), classParticipation);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentRecord)) {
            return false;
        }

        // state check
        StudentRecord studentRecord = (StudentRecord) other;
        return this.name.equals(studentRecord.name)
                && this.nusnetId.equals(studentRecord.nusnetId)
                && this.attendanceType.equals(studentRecord.attendanceType)
                && this.classParticipation.equals(studentRecord.classParticipation);
    }
}
