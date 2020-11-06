package seedu.taskmaster.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.model.record.StudentRecord;

/**
 * Panel containing the list of student records.
 */
public class StudentRecordListPanel extends UiPart<Region> {
    private static final String FXML = "ListDisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentRecordListPanel.class);

    @FXML
    private Label viewName;

    @FXML
    private Label viewDateTime;

    @FXML
    private ListView<StudentRecord> mainListView;

    /**
     * Creates a {@code StudentRecordListPanel} with the given {@code ObservableList}.
     * @param studentRecordList
     */
    public StudentRecordListPanel(ObservableList<StudentRecord> studentRecordList,
                                  String sessionName, String sessionDateTime) {
        super(FXML);
        viewName.setText(sessionName);
        viewDateTime.setText(sessionDateTime);
        mainListView.setItems(studentRecordList);
        mainListView.setCellFactory(listView -> new StudentRecordListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudentRecord} using a
     * {@code StudentRecordListCard}.
     */
    class StudentRecordListViewCell extends ListCell<StudentRecord> {
        @Override
        protected void updateItem(StudentRecord studentRecord, boolean empty) {
            super.updateItem(studentRecord, empty);

            if (empty || studentRecord == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentRecordCard(studentRecord, getIndex() + 1).getRoot());
            }
        }
    }

}
