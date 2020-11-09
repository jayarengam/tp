package seedu.taskmaster.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.core.GuiSettings;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ReadOnlyTaskmaster;
import seedu.taskmaster.model.ReadOnlyUserPrefs;
import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.record.AttendanceType;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.testutil.StudentBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStudent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STUDENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTaskmasterFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskmasterFilePath(Path taskmasterFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskmaster(ReadOnlyTaskmaster taskmaster) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskmaster getTaskmaster() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSessions(List<Session> sessions) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSession(SessionName sessionName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeSession(SessionName sessionName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(SessionName sessionName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<StudentRecord> getFilteredStudentRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Session> getFilteredSessionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSessionList(Predicate<Session> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentRecordList(Predicate<StudentRecord> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showLowestScoringStudents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markStudentRecord(StudentRecord studentRecord, AttendanceType attendanceType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markStudentWithNusnetId(NusnetId nusnetId, AttendanceType attendanceType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markAllStudents(AttendanceType attendanceType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void scoreStudent(StudentRecord target, double score) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void scoreStudentWithNusnetId(NusnetId nusnetId, double score) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void scoreAllStudents(double score) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showRandomStudent(Random random) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleObjectProperty<Session> getCurrentSession() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyTaskmaster getTaskmaster() {
            return new Taskmaster();
        }
    }
}
