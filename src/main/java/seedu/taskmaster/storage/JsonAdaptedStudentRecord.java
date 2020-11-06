package seedu.taskmaster.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.ClassParticipation;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.student.Name;
import seedu.taskmaster.model.student.NusnetId;

/**
 * Jackson-friendly version of {@link StudentRecord}.
 */
class JsonAdaptedStudentRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student record's %s field is missing!";

    private final String name;
    private final String nusnetId;
    private final String attendanceType;
    private final String classParticipation;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudentRecord(@JsonProperty("name") String name,
                                    @JsonProperty("nusnetId") String nusnetId,
                                    @JsonProperty("telegram") String attendanceType,
                                    @JsonProperty("classParticipation") String classParticipation) {
        this.name = name;
        this.nusnetId = nusnetId;
        this.attendanceType = attendanceType;
        this.classParticipation = classParticipation;
    }

    /**
     * Converts a given {@code StudentRecord} into this class for Jackson use.
     */
    public JsonAdaptedStudentRecord(StudentRecord source) {
        name = source.getName().fullName;
        nusnetId = source.getNusnetId().value;
        attendanceType = source.getAttendanceType().name();
        classParticipation = String.valueOf(source.getClassParticipation().getRawScore());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code StudentRecordListManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public StudentRecord toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }

        if (nusnetId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NusnetId.class.getSimpleName()));
        }

        if (!NusnetId.isValidNusnetId(nusnetId)) {
            throw new IllegalValueException(NusnetId.MESSAGE_CONSTRAINTS);
        }

        if (attendanceType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AttendanceType.class.getSimpleName()));
        }

        if (!AttendanceType.isValidAttendanceType(attendanceType)) {
            throw new IllegalValueException(AttendanceType.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        final NusnetId modelNusnetId = new NusnetId(nusnetId);

        final AttendanceType modelAttendanceType = AttendanceType.valueOf(attendanceType);


        final ClassParticipation classParticipation = new ClassParticipation(
                Double.parseDouble(this.classParticipation));

        return new StudentRecord(modelName, modelNusnetId, modelAttendanceType, classParticipation);
    }

}

