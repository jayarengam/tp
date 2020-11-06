package seedu.taskmaster.model.session;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.taskmaster.model.session.exceptions.SessionNotFoundException;

public interface SessionList extends Iterable<Session> {

    Session get(SessionName sessionName) throws SessionNotFoundException;

    int getNumberOfSessions();

    /**
     * Returns true if the session list contains {@code session}.
     */
    boolean contains(Session session);

    boolean contains(SessionName sessionName);

    void add(Session toAdd);

    void delete(SessionName toDelete);

    /**
     * Returns true if there are no sessions in the session list.
     */
    boolean isEmpty();

    void setSessions(SessionListManager replacement);

    /**
     * Replaces the contents of this list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    void setSessions(List<Session> sessions);

    /**
     * Returns the session list as an unmodifiable {@code ObservableList}
     */
    ObservableList<Session> asUnmodifiableObservableList();

}
