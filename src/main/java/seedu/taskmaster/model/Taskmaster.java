package seedu.taskmaster.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionList;
import seedu.taskmaster.model.session.SessionListManager;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.session.exceptions.NoSessionException;
import seedu.taskmaster.model.session.exceptions.NoSessionSelectedException;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.UniqueStudentList;

/**
 * Wraps all data at the Taskmaster level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Taskmaster implements ReadOnlyTaskmaster {

    protected SimpleObjectProperty<Session> currentSession;
    private final UniqueStudentList students;
    private final SessionList sessions;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication between constructors.
     * See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are
     * other ways to avoid duplication among constructors.
     */
    {
        students = new UniqueStudentList();
        currentSession = new SimpleObjectProperty<Session>() {
        };
        sessions = SessionListManager.of(new ArrayList<>());
    }

    public Taskmaster() {}

    /**
     * Creates an Taskmaster using the Students in the {@code toBeCopied}
     */
    public Taskmaster(ReadOnlyTaskmaster toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /* List Overwrite Operations */

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        currentSession.setValue(null);
        this.students.setStudents(students);
    }


    /**
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setSessions(sessions);
    }

    /**
     * Resets the existing data of this {@code Taskmaster} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskmaster newData) {
        requireNonNull(newData);
        setStudents(newData.getStudentList());
        setSessions(newData.getSessionList());
        currentSession.setValue(null);
    }

    /* Session-Level Operations */

    /**
     * Deletes the session with {@code sessionName}.
     * {@code sessionName} must already exist in the session list.
     */
    public void deleteSession(SessionName sessionName) {
        currentSession.setValue(null);
        sessions.delete(sessionName);
    }

    /**
     * Adds a session to the session list.
     * The session must not already exist in the session list.
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /**
     * Changes the current session of this {@code Taskmaster} to a previously
     * created session with name {@code sessionName}.
     */
    public void changeSession(SessionName sessionName) {
        assert sessions.contains(sessionName);
        currentSession.setValue(sessions.get(sessionName));
    }

    /**
     * Switches {@code Taskmaster} to the student list view.
     */
    public void showStudentList() {
        currentSession.setValue(null);
    }

    /**
     * Returns true if {@code session} exists in the session list.
     */
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return sessions.contains(session);
    }

    /**
     * Returns true if a session with {@code sessionName} exists in the session list.
     */
    public boolean hasSession(SessionName sessionName) {
        requireNonNull(sessionName);
        return sessions.contains(sessionName);
    }

    /**
     * Returns the Session with the sessionName
     */
    Session getSession(SessionName sessionName) {
        return sessions.get(sessionName);
    }

    /* Student-Level Operations */

    /**
     * Returns true if a student with the same identity as {@code student} exists in the student list.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the student list.
     * The student must not already exist in the student list.
     */
    public void addStudent(Student student) {
        currentSession.setValue(null);
        students.add(student);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the student list.
     * The student identity of {@code editedStudent} must not be the same as another existing student in
     * the student list.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);
        currentSession.setValue(null);
        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code Taskmaster}.
     * {@code key} must exist in the student list.
     */
    public void removeStudent(Student key) {
        currentSession.setValue(null);
        students.remove(key);
    }

    /**
     * Marks the attendance of a {@code target} student record with {@code attendanceType} in the current session.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void markStudentRecord(StudentRecord target, AttendanceType attendanceType)
            throws NoSessionException, NoSessionSelectedException {
        assert target != null;
        assert attendanceType != null;

        if (sessions.isEmpty()) {
            throw new NoSessionException();
        }

        if (!sessions.isEmpty() && currentSession.get() == null) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().markStudentAttendance(target.getNusnetId(), attendanceType);
    }

    /**
     * Marks the attendance of a student record identified by its {@code nusnetId} with {@code attendanceType} in the
     * current session.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void markStudentWithNusnetId(NusnetId nusnetId, AttendanceType attendanceType)
            throws NoSessionException, NoSessionSelectedException {
        assert nusnetId != null;
        assert attendanceType != null;

        if (sessions.isEmpty()) {
            throw new NoSessionException();
        }

        if (!sessions.isEmpty() && currentSession.get() == null) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().markStudentAttendance(nusnetId, attendanceType);
    }

    /**
     * Marks the attendance of all student records in the {@code studentRecordList} of the current session with the
     * given {@code attendanceType}.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void markAllStudentRecords(AttendanceType attendanceType)
            throws NoSessionException, NoSessionSelectedException {
        assert attendanceType != null;

        if (sessions.isEmpty()) {
            throw new NoSessionException();
        }
        if (!sessions.isEmpty() && currentSession.get() == null) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().markAllStudentAttendances(attendanceType);
    }

    /**
     * Updates participation marks of a {@code target} student with score {@code score} in the current session.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void scoreStudent(StudentRecord target, double score)
            throws NoSessionException, NoSessionSelectedException {
        assert target != null;

        if (sessions.isEmpty()) {
            throw new NoSessionException();
        } else if (currentSession.isNull().get()) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().scoreStudentParticipation(target.getNusnetId(), score);
    }

    /**
     * Updates participation marks of a student given the {@code nusnetId}
     * with score {@code score} in the current session.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void scoreStudentWithNusnetId(NusnetId nusnetId, double score)
            throws NoSessionException, NoSessionSelectedException {
        assert nusnetId != null;

        if (sessions.isEmpty()) {
            throw new NoSessionException();
        } else if (currentSession.isNull().get()) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().scoreStudentParticipation(nusnetId, score);
    }

    /**
     * Updates participation marks of all students represented by {@code nusnetIds} in the {@code studentRecordList}
     * of the {@code currentSession}, with the given {@code attendanceType}.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public void scoreAllStudents(double score)
            throws NoSessionException, NoSessionSelectedException {
        if (sessions.isEmpty()) {
            throw new NoSessionException();
        } else if (currentSession.isNull().get()) {
            throw new NoSessionSelectedException();
        }

        currentSession.get().scoreAllParticipation(score);
    }

    /**
     * Returns the lowest score amongst all students in the student list.
     */
    public double getLowestScore() throws NoSessionException, NoSessionSelectedException {
        if (sessions.isEmpty()) {
            throw new NoSessionException();
        } else if (currentSession.isNull().get()) {
            throw new NoSessionSelectedException();
        }

        return currentSession.get().getLowestScore();
    }

    /* Util Methods */

    @Override
    public String toString() {
        // TODO: refine later
        return students.asUnmodifiableObservableList().size() + " students";
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the {@code StudentRecordList} of the current session.
     *
     * @throws NoSessionException If the session list is empty.
     * @throws NoSessionSelectedException If no session has been selected.
     */
    public ObservableList<StudentRecord> getStudentRecordList()
            throws NoSessionException, NoSessionSelectedException {
        if (sessions.isEmpty()) {
            throw new NoSessionException();
        }

        if (!sessions.isEmpty() && currentSession.get() == null) {
            throw new NoSessionSelectedException();
        }

        return currentSession.get().getStudentRecords();
    }

    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    public boolean inSession() {
        return !this.currentSession.isNull().get();
    }

    public SessionName currentSessionName() {
        return this.currentSession.get().getSessionName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Taskmaster // instanceof handles nulls
                && students.equals(((Taskmaster) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }

    public SimpleObjectProperty<Session> getCurrentSession() {
        return this.currentSession;
    }
}
