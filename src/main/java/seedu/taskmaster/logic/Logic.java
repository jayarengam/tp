package seedu.taskmaster.logic;

import java.nio.file.Path;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.core.GuiSettings;
import seedu.taskmaster.logic.commands.CommandResult;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.logic.parser.exceptions.ParseException;
import seedu.taskmaster.model.ReadOnlyTaskmaster;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.Student;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Taskmaster.
     *
     * @see seedu.taskmaster.model.Model#getTaskmaster()
     */
    ReadOnlyTaskmaster getTaskmaster();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered list of student records */
    ObservableList<StudentRecord> getFilteredStudentRecordList();

    /** Returns an unmodifiable view of the filtered session list */
    ObservableList<Session> getFilteredSessionList();

    /**
     * Returns the user prefs' student list file path.
     */
    Path getTaskmasterFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void changeSession(SessionName sessionName);

    void showStudentList();

    SimpleObjectProperty<Session> getCurrentSession();

}
