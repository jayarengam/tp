package seedu.taskmaster.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.record.StudentRecordList;
import seedu.taskmaster.model.record.StudentRecordListManager;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.NusnetId;


/**
 * Jackson-friendly version of {@link Session}.
 */
@JsonRootName(value = "session")
class JsonSerializableSession {

    public static final String MESSAGE_DUPLICATE_STUDENT = "StudentRecord list contains duplicate NusnetId(s).";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    private final String sessionName;
    private final String sessionDateTime;
    private final List<JsonAdaptedStudentRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablesession} from the given parameters.
     */
    @JsonCreator
    public JsonSerializableSession(@JsonProperty("sessionName") String sessionName,
                                   @JsonProperty("sessionDateTime") String sessionDateTime,
                                   @JsonProperty("records") List<JsonAdaptedStudentRecord> records) {
        assert sessionName != null;
        assert sessionDateTime != null;
        assert records != null;

        this.sessionName = sessionName;
        this.sessionDateTime = sessionDateTime;
        this.records.addAll(records);
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonSerializableSession(Session source) {
        this.sessionName = source.getSessionName().name;
        this.sessionDateTime = source.getSessionDateTime().getDateTimeString();
        this.records.addAll(source.getStudentRecords().stream()
                    .map(JsonAdaptedStudentRecord::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this object into a Session for use by the model.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Session toModelType() throws IllegalValueException {
        SessionDateTime newDateTime;

        List<StudentRecord> studentRecords = new ArrayList<>();
        List<NusnetId> nusnetIds = new ArrayList<>();

        for (JsonAdaptedStudentRecord jsonAdaptedStudentRecord : records) {
            StudentRecord modelAttendance = jsonAdaptedStudentRecord.toModelType();

            // check for duplicates
            NusnetId nusnetId = modelAttendance.getNusnetId();
            if (nusnetIds.contains(nusnetId)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            nusnetIds.add(modelAttendance.getNusnetId());

            studentRecords.add(modelAttendance);
        }

        StudentRecordList newRecordList = new StudentRecordListManager();
        newRecordList.setStudentRecords(studentRecords);
        SessionName newSessionName = new SessionName(sessionName);

        try {
            newDateTime = new SessionDateTime(
                    LocalDateTime.parse(sessionDateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Error when parsing SessionDateTime.");
        }

        return new Session(newSessionName, newDateTime, newRecordList);
    }

}
