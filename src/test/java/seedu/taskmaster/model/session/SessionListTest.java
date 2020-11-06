package seedu.taskmaster.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.testutil.Assert.assertThrows;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmaster.model.session.exceptions.DuplicateSessionException;
import seedu.taskmaster.model.session.exceptions.SessionNotFoundException;
import seedu.taskmaster.testutil.TypicalStudents;

public class SessionListTest {

    private final Session typicalSession1 = new Session(
            new SessionName("Typical session"),
            new SessionDateTime(LocalDateTime.of(2020, 1, 1, 12, 0)),
            TypicalStudents.getTypicalStudents());

    private final Session typicalSession2 = new Session(
            new SessionName("Typical session 2"),
            new SessionDateTime(LocalDateTime.of(2020, 1, 1, 13, 0)),
            TypicalStudents.getTypicalStudents());

    // The typicalSessionList contains session1 and session2 only
    private final SessionList typicalSessionList = TypicalStudents.getTypicalSessionList();

    private final SessionList emptySessionList = SessionListManager.of(new ArrayList<>());

    @Test
    public void of() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession1);
        sessions.add(typicalSession2);
        SessionList constructedSessionList = SessionListManager.of(sessions);
        assertEquals(typicalSessionList, constructedSessionList);
    }

    @Test
    public void get_sessionFound_success() {
        Session retrievedSession1 = typicalSessionList.get(typicalSession1.getSessionName());
        assertEquals(typicalSession1, retrievedSession1);
    }

    @Test
    public void get_sessionNotFound_exceptionThrown() {
        Session missingSession = new Session(
                new SessionName("Missing Session"),
                new SessionDateTime(LocalDateTime.of(2019, 1, 1, 13, 0)),
                TypicalStudents.getTypicalStudents());
        assertThrows(SessionNotFoundException.class, ()
            -> typicalSessionList.get(missingSession.getSessionName()));
    }

    @Test
    public void getNumberOfSessions_twoSessions_returnTwo() {
        List<Session> twoSessions = new ArrayList<>();
        twoSessions.add(typicalSession1);
        twoSessions.add(typicalSession2);
        SessionList listWithTwoSessions = SessionListManager.of(twoSessions);
        assertEquals(2, listWithTwoSessions.getNumberOfSessions());
    }

    @Test
    public void getNumberOfSessions_noSessions_returnZero() {
        assertEquals(0, emptySessionList.getNumberOfSessions());
    }

    @Test
    public void contains_sessionFound_returnTrue() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession1);
        SessionList listWithTypicalSession1 = SessionListManager.of(sessions);
        assertTrue(listWithTypicalSession1.contains(typicalSession1));
    }

    @Test
    public void contains_sessionNotFound_returnFalse() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession2);
        SessionList listWithTypicalSession2 = SessionListManager.of(sessions);
        assertFalse(listWithTypicalSession2.contains(typicalSession1));
    }

    @Test
    public void contains_sessionNameFound_returnTrue() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession1);
        SessionList listWithTypicalSession1 = SessionListManager.of(sessions);
        assertTrue(listWithTypicalSession1.contains(typicalSession1.getSessionName()));
    }

    @Test
    public void contains_sessionNameNotFound_returnFalse() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession2);
        SessionList listWithTypicalSession2 = SessionListManager.of(sessions);
        assertFalse(listWithTypicalSession2.contains(typicalSession1.getSessionName()));
    }

    @Test
    public void isEmpty_emptySessionList_returnTrue() {
        assertTrue(emptySessionList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptySessionList_returnFalse() {
        assertFalse(typicalSessionList.isEmpty());
    }

    @Test
    public void add_sessionNotInList_success() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession1);
        SessionList sessionList = SessionListManager.of(sessions);
        sessionList.add(typicalSession2);
        assertEquals(typicalSessionList, sessionList);
    }

    @Test
    public void add_sessionInList_exceptionThrown() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(typicalSession1);
        SessionList sessionList = SessionListManager.of(sessions);
        assertThrows(DuplicateSessionException.class, ()
            -> sessionList.add(typicalSession1));
    }

    @Test
    public void deleteSession_sessionInList_success() {
        List<Session> sessions = new ArrayList<>();
        SessionList listWithoutSession = SessionListManager.of(sessions);
        sessions.add(typicalSession1);
        SessionList listWithSession = SessionListManager.of(sessions);
        listWithSession.delete(typicalSession1.getSessionName());
        assertEquals(listWithSession, listWithoutSession);
    }

    @Test
    public void deleteSession_sessionNotInList_sessionNotFoundExceptionThrown() {
        List<Session> sessions = new ArrayList<>();
        SessionList listWithoutSession = SessionListManager.of(sessions);
        assertThrows(SessionNotFoundException.class, ()
            -> listWithoutSession.delete(getTypicalSession().getSessionName()));
    }

    @Test
    public void setSessions_replacementSessionListManager_success() {
        List<Session> sessions = new ArrayList<>();
        SessionList sessionList = SessionListManager.of(sessions);
        sessionList.setSessions((SessionListManager) typicalSessionList);
        assertEquals(typicalSessionList, sessionList);
    }

    @Test
    public void setSessions_replacementListOfUniqueSessions_success() {
        List<Session> listOfNoSessions = new ArrayList<>();
        SessionList sessionList = SessionListManager.of(listOfNoSessions);

        List<Session> listOfTwoSessions = new ArrayList<>();
        listOfTwoSessions.add(typicalSession1);
        listOfTwoSessions.add(typicalSession2);
        sessionList.setSessions(listOfTwoSessions);
        assertEquals(typicalSessionList, sessionList);
    }

    @Test
    public void setSessions_replacementListOfDuplicateSessions_success() {
        List<Session> listOfNoSessions = new ArrayList<>();
        SessionList sessionList = SessionListManager.of(listOfNoSessions);

        List<Session> listOfDuplicateSessions = new ArrayList<>();
        listOfDuplicateSessions.add(typicalSession1);
        listOfDuplicateSessions.add(typicalSession1);
        assertThrows(DuplicateSessionException.class, ()
            -> sessionList.setSessions(listOfDuplicateSessions));
    }

    @Test
    public void asUnmodifiableObservableList() {
        ObservableList<Session> unmodifiableObservableList =
                FXCollections.unmodifiableObservableList(
                        FXCollections.observableArrayList(typicalSession1, typicalSession2));
        assertEquals(typicalSessionList.asUnmodifiableObservableList(), unmodifiableObservableList);
    }

    @Test
    public void equals_sameSessionList_returnTrue() {
        assertTrue(typicalSessionList.equals(typicalSessionList));
    }

    @Test
    public void equals_sameInternalList_returnTrue() {
        List<Session> listOfTwoSessions = new ArrayList<>();
        listOfTwoSessions.add(typicalSession1);
        listOfTwoSessions.add(typicalSession2);
        SessionList sessionList = SessionListManager.of(listOfTwoSessions);
        assertTrue(sessionList.equals(typicalSessionList));
    }

    @Test
    public void equals_differentInternalList_returnFalse() {
        assertFalse(typicalSessionList.equals(emptySessionList));
    }
}
