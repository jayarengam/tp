package seedu.taskmaster.model;

import static java.util.Objects.requireNonNull;
import static seedu.taskmaster.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.taskmaster.commons.core.GuiSettings;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.ScoreEqualsPredicate;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.record.StudentRecordEqualsPredicate;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.session.exceptions.NoSessionException;
import seedu.taskmaster.model.session.exceptions.NoSessionSelectedException;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;

/**
 * Represents the in-memory model of the student list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Taskmaster taskmaster;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Session> filteredSessions;
    private FilteredList<StudentRecord> filteredStudentRecords;
    private Predicate<StudentRecord> studentRecordPredicate;

    /**
     * Initializes a ModelManager with the given Taskmaster, SessionList, and userPrefs.
     */
    public ModelManager(ReadOnlyTaskmaster taskmaster, List<Session> sessionList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(taskmaster, userPrefs);

        logger.fine("Initializing with student list: " + taskmaster + " and user prefs " + userPrefs);

        this.taskmaster = new Taskmaster(taskmaster);
        this.taskmaster.setSessions(sessionList);

        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.taskmaster.getStudentList());
        filteredSessions = new FilteredList<>(this.taskmaster.getSessionList());
        filteredStudentRecords = null;
        studentRecordPredicate = PREDICATE_SHOW_ALL_STUDENT_RECORDS;
    }

    public ModelManager(ReadOnlyTaskmaster taskmaster, ReadOnlyUserPrefs userPrefs) {
        this(taskmaster, taskmaster.getSessionList(), userPrefs);
    }

    public ModelManager() {
        this(new Taskmaster(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTaskmasterFilePath() {
        return userPrefs.getTaskmasterFilePath();
    }

    @Override
    public void setTaskmasterFilePath(Path taskmasterFilePath) {
        requireNonNull(taskmasterFilePath);
        userPrefs.setTaskmasterFilePath(taskmasterFilePath);
    }

    //=========== Taskmaster ================================================================================

    @Override
    public void setTaskmaster(ReadOnlyTaskmaster taskmaster) {
        this.taskmaster.resetData(taskmaster);
    }

    @Override
    public ReadOnlyTaskmaster getTaskmaster() {
        return taskmaster;
    }

    @Override
    public void setSessions(List<Session> sessions) {
        taskmaster.setSessions(sessions);
    }

    @Override
    public void deleteSession(SessionName sessionName) {
        taskmaster.deleteSession(sessionName);
        updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
    }

    @Override
    public void addSession(Session session) {
        updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        filteredStudentRecords = null;
        taskmaster.addSession(session);
        taskmaster.changeSession(session.getSessionName());
    }

    /**
     * Changes the Session to the Session with that name.
     */
    @Override
    public void changeSession(SessionName sessionName) {
        if (sessionName == null) {
            filteredStudentRecords = null;
            studentRecordPredicate = PREDICATE_SHOW_ALL_STUDENT_RECORDS;
            taskmaster.changeSession(null);
            return;
        } else {
            /*
             * Note that the implementation of this method requires that the filteredStudentRecords field is updated
             * first, as changing the Session triggers the UI listener to call the getFilteredStudentRecordList, hence
             * it must be loaded first.
             */
            assert taskmaster.hasSession(sessionName);

            studentRecordPredicate = PREDICATE_SHOW_ALL_STUDENT_RECORDS;
            // Update filteredStudentRecords before Session is changed.
            filteredStudentRecords = new FilteredList<>(taskmaster.getSession(sessionName).getStudentRecords());

            taskmaster.changeSession(sessionName);
        }



    }

    @Override
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return taskmaster.hasSession(session);
    }

    @Override
    public boolean hasSession(SessionName sessionName) {
        requireNonNull(sessionName);
        return taskmaster.hasSession(sessionName);
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return taskmaster.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        taskmaster.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        taskmaster.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        taskmaster.setStudent(target, editedStudent);
    }

    @Override
    public void markStudentRecord(StudentRecord target, AttendanceType attendanceType) {
        requireAllNonNull(target, attendanceType);
        taskmaster.markStudentRecord(target, attendanceType);
    }

    @Override
    public void markStudentWithNusnetId(NusnetId nusnetId, AttendanceType attendanceType) {
        requireAllNonNull(nusnetId, attendanceType);
        taskmaster.markStudentWithNusnetId(nusnetId, attendanceType);
    }

    @Override
    public void scoreStudent(StudentRecord target, double score) {
        requireAllNonNull(target, score);
        taskmaster.scoreStudent(target, score);
    }

    @Override
    public void scoreStudentWithNusnetId(NusnetId nusnetId, double score) {
        requireAllNonNull(nusnetId, score);
        taskmaster.scoreStudentWithNusnetId(nusnetId, score);
    }

    @Override
    public void scoreAllStudents(List<StudentRecord> students, double score) {
        List<NusnetId> nusnetIds = students
                .stream()
                .map(StudentRecord::getNusnetId)
                .collect(Collectors.toList());

        taskmaster.scoreAllStudents(nusnetIds, score);
    }

    @Override
    public void markAllStudentRecords(List<StudentRecord> studentRecords, AttendanceType attendanceType) {
        List<NusnetId> nusnetIds = studentRecords
                .stream()
                .map(StudentRecord::getNusnetId)
                .collect(Collectors.toList());

        taskmaster.markAllStudentRecords(nusnetIds, attendanceType);
    }

    @Override
    public void clearAttendance() {
        taskmaster.clearAttendance();
    }

    @Override
    public void updateStudentRecords(List<StudentRecord> studentRecords) {
        taskmaster.updateStudentRecords(studentRecords);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedTaskmaster}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code StudentRecord} backed by the internal list of
     * {@code versionedTaskmaster}
     */
    @Override
    public ObservableList<StudentRecord> getFilteredStudentRecordList() {
        if (taskmaster.getSessionList().size() == 0) {
            throw new NoSessionException();
        }
        if (getCurrentSession().isNull().get()) {
            throw new NoSessionSelectedException();
        }

        if (filteredStudentRecords == null) {
            filteredStudentRecords = new FilteredList<>(taskmaster.getCurrentSession().get().getStudentRecords());
            studentRecordPredicate = PREDICATE_SHOW_ALL_STUDENT_RECORDS;
        }

        filteredStudentRecords.setPredicate(studentRecordPredicate);
        return filteredStudentRecords;
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentRecordList(Predicate<StudentRecord> predicate) {
        requireNonNull(predicate);
        studentRecordPredicate = predicate;
        filteredStudentRecords.setPredicate(predicate);
    }

    @Override
    public void showRandomStudent() {
        StudentRecord randomRecord = taskmaster.getRandomStudentRecord();
        filteredStudentRecords.setPredicate(new StudentRecordEqualsPredicate(randomRecord));
    }

    @Override
    public void showLowestScoringStudents() {
        double lowestScore = taskmaster.getLowestScore();
        studentRecordPredicate = new ScoreEqualsPredicate(lowestScore);

        updateFilteredStudentRecordList(studentRecordPredicate);
    }

    //=========== Current Session Accessor =================================================================
    @Override
    public SimpleObjectProperty<Session> getCurrentSession() {
        return this.taskmaster.getCurrentSession();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return taskmaster.equals(other.taskmaster)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

}
