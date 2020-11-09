package seedu.taskmaster.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_SCORE_DOUBLE;
import static seedu.taskmaster.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.taskmaster.testutil.Assert.assertThrows;
import static seedu.taskmaster.testutil.TypicalStudentRecords.ALICE_STUDENT_RECORD;
import static seedu.taskmaster.testutil.TypicalStudentRecords.BENSON_STUDENT_RECORD;
import static seedu.taskmaster.testutil.TypicalStudents.ALICE;
import static seedu.taskmaster.testutil.TypicalStudents.BENSON;
import static seedu.taskmaster.testutil.TypicalStudents.BOB;
import static seedu.taskmaster.testutil.TypicalStudents.getTypicalSession;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskmaster.commons.core.GuiSettings;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.session.exceptions.NoSessionException;
import seedu.taskmaster.model.session.exceptions.NoSessionSelectedException;
import seedu.taskmaster.model.student.NameContainsKeywordsPredicate;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.testutil.TaskmasterBuilder;
import seedu.taskmaster.testutil.TypicalStudents;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();
    private ModelManager modelManager2 = new ModelManager(new Taskmaster(), new UserPrefs());

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Taskmaster(), new Taskmaster(modelManager.getTaskmaster()));
        assertEquals(new UserPrefs(), modelManager2.getUserPrefs());
        assertEquals(new Taskmaster(), new Taskmaster(modelManager2.getTaskmaster()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTaskmasterFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTaskmasterFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTaskmasterFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTaskmasterFilePath(null));
    }

    @Test
    public void setTaskmasterFilePath_validPath_setsTaskmasterFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTaskmasterFilePath(path);
        assertEquals(path, modelManager.getTaskmasterFilePath());
    }

    @Test
    public void setSessions_validSessionList_success() {
        Session newSession = getTypicalSession();
        List<Session> sessions = new ArrayList<>();
        sessions.add(newSession);
        modelManager.setSessions(sessions);
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().withSession(newSession).build();
        Model expectedModel = new ModelManager(expectedTaskmaster, new UserPrefs());
        assertEquals(modelManager, expectedModel);
    }

    @Test
    public void setSessions_nullSessionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSessions(null));
    }

    @Test
    public void addSession_validSession_success() {
        Session newSession = getTypicalSession();
        modelManager.addSession(newSession);
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().withSession(newSession).build();
        Model expectedModel = new ModelManager(expectedTaskmaster, new UserPrefs());
        assertEquals(modelManager, expectedModel);
    }

    @Test
    public void deleteSession_sessionFound_success() {
        Taskmaster actualTaskmaster = new TaskmasterBuilder().withSession(getTypicalSession()).build();
        Model actualModel = new ModelManager(actualTaskmaster, new UserPrefs());
        Taskmaster expectedTaskmaster = new TaskmasterBuilder().build();
        Model expectedModel = new ModelManager(expectedTaskmaster, new UserPrefs());
        actualModel.deleteSession(getTypicalSession().getSessionName());
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void hasSession_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSession((Session) null));
    }

    @Test
    public void hasSession_sessionNotInSessionList_returnsFalse() {
        Session newSession = new Session(
                new SessionName("This session is not in the session list"),
                new SessionDateTime(LocalDateTime.of(2020, 11, 1, 10, 30)),
                TypicalStudents.getTypicalStudents());
        assertFalse(modelManager.hasSession(newSession));
    }

    @Test
    public void hasSession_sessionInStudentList_returnsTrue() {
        modelManager.addSession(getTypicalSession());
        assertTrue(modelManager.hasSession(getTypicalSession()));
    }

    @Test
    public void hasSession_nullSessionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSession((SessionName) null));
    }

    @Test
    public void hasSession_sessionNameNotInSessionList_returnsFalse() {
        assertFalse(modelManager.hasSession(new SessionName("This session is not in the session list")));
    }

    @Test
    public void hasSession_sessionNameInStudentList_returnsTrue() {
        modelManager.addSession(getTypicalSession());
        assertTrue(modelManager.hasSession(getTypicalSession().getSessionName()));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInStudentList_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInStudentList_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void equals() {
        Taskmaster taskmaster = new TaskmasterBuilder().withStudent(ALICE).withStudent(BENSON).build();
        Taskmaster differentTaskmaster = new Taskmaster();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(taskmaster, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(taskmaster, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different taskmaster -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTaskmaster, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(taskmaster, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTaskmasterFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(taskmaster, differentUserPrefs)));
    }

    Session initSessionWithAliceAndBenson() {
        SessionName sName = new SessionName("Test Session");
        ArrayList<Student> stds = new ArrayList<Student>();
        stds.add(ALICE);
        stds.add(BOB);
        return new Session(sName,
                new SessionDateTime(LocalDateTime.now()),
                stds);
    }

    @Test
    void markStudentRecord_success() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        modelManager.markStudentRecord(ALICE_STUDENT_RECORD, AttendanceType.PRESENT);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|PRESENT|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");

        modelManager.markStudentRecord(ALICE_STUDENT_RECORD, AttendanceType.ABSENT);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|ABSENT|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
    }

    @Test
    void markStudent_noSession_failure() {
        assertThrows(NoSessionException.class, () ->
                modelManager.markStudentRecord(ALICE_STUDENT_RECORD, AttendanceType.PRESENT));
    }

    @Test
    void markStudent_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        modelManager.addSession(s);
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () ->
                modelManager.markStudentRecord(ALICE_STUDENT_RECORD, AttendanceType.PRESENT));
    }

    @Test
    void markStudentWithNusnetId() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        modelManager.markStudentWithNusnetId(ALICE.getNusnetId(), AttendanceType.PRESENT);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|PRESENT|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
        modelManager.markStudentWithNusnetId(ALICE.getNusnetId(), AttendanceType.ABSENT);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|ABSENT|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
    }

    @Test
    void markStudentWithNusnetId_noSession_failure() {
        assertThrows(NoSessionException.class, () -> modelManager
                .markStudentWithNusnetId(ALICE.getNusnetId(), AttendanceType.PRESENT));
    }

    @Test
    void markStudentWithNusnetId_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        modelManager.addSession(s);
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () -> modelManager
                .markStudentWithNusnetId(ALICE.getNusnetId(), AttendanceType.PRESENT));
    }

    @Test
    void scoreStudent() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        modelManager.scoreStudent(ALICE_STUDENT_RECORD, VALID_SCORE_DOUBLE);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|NO_RECORD|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
        modelManager.scoreStudent(ALICE_STUDENT_RECORD, (VALID_SCORE_DOUBLE + 2));
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|NO_RECORD|Class Participation Score: 2.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
    }

    @Test
    void scoreStudent_noSession_failure() {
        assertThrows(NoSessionException.class, () -> modelManager
                .scoreStudent(ALICE_STUDENT_RECORD, VALID_SCORE_DOUBLE));
    }

    @Test
    void scoreStudent_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        modelManager.addSession(s);
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () -> modelManager
                .scoreStudent(ALICE_STUDENT_RECORD, VALID_SCORE_DOUBLE));
    }

    @Test
    void scoreStudentWithNusnetId() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        modelManager.scoreStudentWithNusnetId(ALICE.getNusnetId(), VALID_SCORE_DOUBLE);
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|NO_RECORD|Class Participation Score: 0.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
        modelManager.scoreStudentWithNusnetId(ALICE.getNusnetId(), (VALID_SCORE_DOUBLE + 1));
        assertEquals(s.getStudentRecords().toString(),
                "[e0123456|NO_RECORD|Class Participation Score: 1.00, "
                        + "e0456789|NO_RECORD|Class Participation Score: 0.00]");
    }

    @Test
    void scoreStudentWithNusnetId_noSession_failure() {
        assertThrows(NoSessionException.class, () -> modelManager
                .scoreStudentWithNusnetId(ALICE.getNusnetId(), VALID_SCORE_DOUBLE));
    }

    @Test
    void scoreStudentWithNusnetId_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        modelManager.addSession(s);
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () -> modelManager
                .scoreStudentWithNusnetId(ALICE.getNusnetId(), VALID_SCORE_DOUBLE));
    }

    @Test
    void scoreAllStudents() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        List<StudentRecord> studentRecords = modelManager.getFilteredStudentRecordList();
        modelManager.markAllStudents(AttendanceType.PRESENT);
        assertEquals("[e0123456|PRESENT|Class Participation Score: 0.00,"
                + " e0456789|PRESENT|Class Participation Score: 0.00]", s.getStudentRecords().toString());
        modelManager.scoreAllStudents(2);
        assertEquals("[e0123456|PRESENT|Class Participation Score: 2.00,"
                + " e0456789|PRESENT|Class Participation Score: 2.00]", s.getStudentRecords().toString());
    }

    @Test
    void scoreAllStudents_noSession_failure() {
        ArrayList<StudentRecord> studentRecords = new ArrayList<>();
        studentRecords.add(ALICE_STUDENT_RECORD);
        studentRecords.add(BENSON_STUDENT_RECORD);
        assertThrows(NoSessionException.class, () -> modelManager.scoreAllStudents(VALID_SCORE_DOUBLE));
    }

    @Test
    void scoreAllStudents_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        modelManager.addSession(s);
        List<StudentRecord> studentRecords = modelManager.getFilteredStudentRecordList();
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () -> modelManager
                .scoreAllStudents(VALID_SCORE_DOUBLE));
    }

    @Test
    void markAllStudents() {
        Session s = initSessionWithAliceAndBenson();
        SessionName sName = new SessionName("Test Session");
        modelManager.addSession(s);
        modelManager.changeSession(sName);
        List<StudentRecord> studentRecords = modelManager.getFilteredStudentRecordList();
        modelManager.markAllStudents(AttendanceType.PRESENT);

        assertEquals("[e0123456|PRESENT|Class Participation Score: 0.00,"
                + " e0456789|PRESENT|Class Participation Score: 0.00]", s.getStudentRecords().toString());
        modelManager.markAllStudents(AttendanceType.ABSENT);
        assertEquals("[e0123456|ABSENT|Class Participation Score: 0.00,"
                + " e0456789|ABSENT|Class Participation Score: 0.00]", s.getStudentRecords().toString());
    }

    @Test
    void markAllStudents_noSession_failure() {
        ArrayList<StudentRecord> studentRecords = new ArrayList<>();
        studentRecords.add(ALICE_STUDENT_RECORD);
        studentRecords.add(BENSON_STUDENT_RECORD);
        assertThrows(NoSessionException.class, () ->
                modelManager.markAllStudents(AttendanceType.PRESENT));
    }

    @Test
    void markAllStudents_sessionNotSelected_failure() {
        Session s = initSessionWithAliceAndBenson();
        ArrayList<StudentRecord> studentRecords = new ArrayList<>();
        studentRecords.add(ALICE_STUDENT_RECORD);
        studentRecords.add(BENSON_STUDENT_RECORD);
        modelManager.addSession(s);
        modelManager.showStudentList();
        assertThrows(NoSessionSelectedException.class, () -> modelManager
                .markAllStudents(AttendanceType.PRESENT));
    }
}
