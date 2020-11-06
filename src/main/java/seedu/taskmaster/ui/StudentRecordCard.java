package seedu.taskmaster.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentRecordCard extends UiPart<Region> {

    private static final String FXML = "StudentRecordListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final StudentRecord studentRecord;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label nusnetId;
    @FXML
    private Label attendanceStatus;
    @FXML
    private Label name;
    @FXML
    private Label classParticipation;

    /**
     * Creates a {@code StudentRecordListCard} with the given {@code AttendanceType} and index to display.
     */
    public StudentRecordCard(StudentRecord studentRecord, int displayedIndex) {
        super(FXML);
        this.studentRecord = studentRecord;
        id.setText(displayedIndex + ". ");

        String studentName = studentRecord.getName().fullName;
        String studentNusnetId = studentRecord.getNusnetId().value;
        String studentClassParticipation = String.valueOf(studentRecord.getClassParticipation().getRawScore());

        assert studentName.length() > 0;
        assert studentNusnetId.length() > 0;
        assert studentClassParticipation.length() > 0;

        name.setText(studentName);
        nusnetId.setText(studentNusnetId);
        setAttendanceStatus(studentRecord.getAttendanceType());
        classParticipation.setText(studentClassParticipation);
    }

    private void setAttendanceStatus(AttendanceType attendanceType) {
        attendanceStatus.setText(attendanceType.getDescription());
        attendanceStatus.setStyle(String.format("-fx-background-color: %s; "
                + "-fx-background-radius: 3; -fx-padding: 1 3 1 3",
                attendanceType.getColor()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentRecordCard)) {
            return false;
        }

        // state check
        StudentRecordCard card = (StudentRecordCard) other;
        return id.getText().equals(card.id.getText())
                && this.studentRecord.equals(card.studentRecord);
    }
}
