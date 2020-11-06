package seedu.taskmaster.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionName;

/**
 * Panel containing the list of sessions.
 */
public class SessionListPanel extends UiPart<Region> {
    private static final String FXML = "SessionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SessionListPanel.class);
    private Runnable studentDisplay;

    @FXML
    private ListView<Session> sessionListView;

    /**
     * Creates a {@code SessionListPanel} with the given {@code ObservableList}.
     */
    public SessionListPanel(ObservableList<Session> sessionList, Consumer<SessionName> changeSessionAndFill,
                            Runnable studentDisplay) {
        super(FXML);
        sessionListView.setItems(sessionList);
        sessionListView.setCellFactory(listView -> new SessionListViewCell(changeSessionAndFill));
        this.studentDisplay = studentDisplay;
    }

    @FXML
    private void handleStudent() {
        studentDisplay.run();
    }

    /**
     *
     */
    class SessionListViewCell extends ListCell<Session> { //change the stub to session
        private final Consumer<SessionName> changeSession;
        private final Button button = new Button();
        SessionListViewCell(Consumer<SessionName> changeSession) {
            this.changeSession = changeSession;
        }
        @Override
        protected void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);
            if (empty || session == null) {
                setGraphic(null);
                setText(null);
            } else {
                SessionName sessionName = session.getSessionName();
                button.setText(sessionName.name + "\n" + session.getSessionDateTime().getDisplayDateTimeString());
                button.setMinSize(100, 50);
                button.setMaxWidth(140);
                button.setStyle("-fx-font-size:13; -fx-alignment: center-left;");
                AnchorPane.setBottomAnchor(button, 0.0);
                AnchorPane.setLeftAnchor(button, 0.0);
                AnchorPane.setRightAnchor(button, 0.0);
                AnchorPane.setTopAnchor(button, 0.0);
                button.wrapTextProperty().setValue(true);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        changeSession.accept(sessionName);
                    }
                });
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().add(button);
                anchorPane.setStyle("-fx-padding: 3 0 3 0; -fx-background-color: #383838; -fx-border-color: #383838");
                setGraphic(anchorPane);
            }
        }
    }
}
