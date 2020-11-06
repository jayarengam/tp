package seedu.taskmaster.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NUSNETID_BOB;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.taskmaster.testutil.Assert.assertThrows;
import static seedu.taskmaster.testutil.TypicalStudents.ALICE;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalSession;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalTaskmaster;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.session.exceptions.DuplicateSessionException;
import seedu.taskmaster.model.session.exceptions.SessionNotFoundException;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.exceptions.DuplicateStudentException;
import seedu.taskmaster.testutil.StudentBuilder;
import seedu.taskmaster.testutil.TaskmasterBuilder;
import seedu.taskmaster.testutil.TypicalStudents;

public class TaskmasterTest {

    private final Taskmaster taskmaster = new Taskmaster();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskmaster.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskmaster.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaskmaster_replacesData() {
        Taskmaster newData = getTypicalTaskmaster();
        taskmaster.resetData(newData);
        assertEquals(newData, taskmaster);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withNusnetId(VALID_NUSNETID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        TaskmasterStub newData = new TaskmasterStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> taskmaster.resetData(newData));
    }

    @Test
    public void setSessions_validSessionList_success() {
        Session newSession = TypicalStudents.getTypicalSession();
        List<Session> sessions = new ArrayList<>();
        sessions.add(newSession);
        taskmaster.setSessions(sessions);
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().withSession(newSession).build();
        assertEquals(taskmaster, expectedTaskmaster);
    }

    @Test
    public void setSessions_nullSessionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskmaster.setSessions(null));
    }

    @Test
    public void addSession_validSession_success() {
        Session newSession = TypicalStudents.getTypicalSession();
        taskmaster.addSession(newSession);
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().withSession(newSession).build();
        assertEquals(taskmaster, expectedTaskmaster);
    }

    @Test
    public void addSession_duplicateSession_success() {
        Session duplicateSession = TypicalStudents.getTypicalSession();
        Taskmaster taskmasterWithSession = new TaskmasterBuilder().withSession(duplicateSession).build();
        assertThrows(DuplicateSessionException.class, ()
            -> taskmasterWithSession.addSession(duplicateSession));
    }

    @Test
    public void deleteSession_sessionFound_success() {
        Taskmaster actualTaskmaster = new TaskmasterBuilder().withSession(getTypicalSession()).build();
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().build();
        actualTaskmaster.deleteSession(getTypicalSession().getSessionName());
        assertEquals(expectedTaskmaster, actualTaskmaster);
    }

    @Test
    public void deleteSession_sessionNotFound_sessionNotFoundExceptionThrown() {
        Taskmaster tmWithoutSession = new Taskmaster();
        assertThrows(SessionNotFoundException.class, ()
            -> tmWithoutSession.deleteSession(getTypicalSession().getSessionName()));
    }

    @Test
    public void hasSession_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskmaster.hasSession((Session) null));
    }

    @Test
    public void hasSession_sessionNotInSessionList_returnsFalse() {
        Session newSession = new Session(
                new SessionName("This session is not in the session list"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                TypicalStudents.getTypicalStudents());
        assertFalse(taskmaster.hasSession(newSession));
    }

    @Test
    public void hasSession_sessionInStudentList_returnsTrue() {
        taskmaster.addSession(TypicalStudents.getTypicalSession());
        assertTrue(taskmaster.hasSession(TypicalStudents.getTypicalSession()));
    }

    @Test
    public void hasSession_nullSessionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskmaster.hasSession((SessionName) null));
    }

    @Test
    public void hasSession_sessionNameNotInSessionList_returnsFalse() {
        assertFalse(taskmaster.hasSession(new SessionName("This session is not in the session list")));
    }

    @Test
    public void hasSession_sessionNameInStudentList_returnsTrue() {
        taskmaster.addSession(TypicalStudents.getTypicalSession());
        assertTrue(taskmaster.hasSession(TypicalStudents.getTypicalSession().getSessionName()));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskmaster.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInStudentList_returnsFalse() {
        assertFalse(taskmaster.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInStudentList_returnsTrue() {
        taskmaster.addStudent(ALICE);
        assertTrue(taskmaster.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInStudentList_returnsTrue() {
        taskmaster.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withNusnetId(VALID_NUSNETID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(taskmaster.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskmaster.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyTaskmaster whose students list can violate interface constraints.
     */
    private static class TaskmasterStub implements ReadOnlyTaskmaster {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<StudentRecord> studentRecords = FXCollections.observableArrayList();
        private final ObservableList<Session> sessions = FXCollections.observableArrayList();

        TaskmasterStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<StudentRecord> getStudentRecordList() {
            return studentRecords;
        }

        @Override
        public ObservableList<Session> getSessionList() {
            return sessions;
        }
    }

}
