package seedu.taskmaster.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.core.GuiSettings;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.logic.commands.Command;
import seedu.taskmaster.logic.commands.CommandResult;
import seedu.taskmaster.logic.commands.NewSessionCommand;
import seedu.taskmaster.logic.commands.StorageCommand;
import seedu.taskmaster.logic.commands.exceptions.CommandException;
import seedu.taskmaster.logic.parser.TaskmasterParser;
import seedu.taskmaster.logic.parser.exceptions.ParseException;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.ReadOnlyTaskmaster;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.record.StudentRecordList;
import seedu.taskmaster.model.record.StudentRecordListManager;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TaskmasterParser taskmasterParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        taskmasterParser = new TaskmasterParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = taskmasterParser.parseCommand(commandText);

        if (command instanceof StorageCommand) {
            StorageCommand storageCommand = (StorageCommand) command;
            storageCommand.initialiseStorage(storage);
            commandResult = storageCommand.execute(model);
        } else if (command instanceof NewSessionCommand) {
            NewSessionCommand newSessionCommand = (NewSessionCommand) command;
            StudentRecordList studentRecords = StudentRecordListManager.of(model.getFilteredStudentList());
            newSessionCommand.setStudentRecords(studentRecords);
            commandResult = newSessionCommand.execute(model);
        } else {
            commandResult = command.execute(model);
        }

        try {
            storage.saveTaskmaster(model.getTaskmaster());
            storage.saveSessionList(model.getTaskmaster());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTaskmaster getTaskmaster() {
        return model.getTaskmaster();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<StudentRecord> getFilteredStudentRecordList() {
        return model.getFilteredStudentRecordList();
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return model.getFilteredSessionList();
    }

    @Override
    public Path getTaskmasterFilePath() {
        return model.getTaskmasterFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void changeSession(SessionName sessionName) {
        model.changeSession(sessionName);
    }

    @Override
    public SimpleObjectProperty<Session> getCurrentSession() {
        return this.model.getCurrentSession();
    }
}
