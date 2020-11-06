package seedu.taskmaster.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.commons.exceptions.DataConversionException;
import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.commons.util.FileUtil;
import seedu.taskmaster.commons.util.JsonUtil;
import seedu.taskmaster.model.ReadOnlyTaskmaster;
import seedu.taskmaster.model.session.SessionList;

/**
 * A class to access Taskmaster data stored as a json file on the hard disk.
 */
public class JsonTaskmasterStorage implements TaskmasterStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskmasterStorage.class);

    private Path taskmasterFilePath;
    private Path sessionListFilePath;

    /**
     * Initialises the Storage object which will use the given file paths.
     */
    public JsonTaskmasterStorage(Path taskmasterFilePath, Path sessionListFilePath) {
        this.taskmasterFilePath = taskmasterFilePath;
        this.sessionListFilePath = sessionListFilePath;
    }

    @Override
    public Path getTaskmasterFilePath() {
        return taskmasterFilePath;
    }

    @Override
    public Path getSessionListFilePath() {
        return sessionListFilePath;
    }

    @Override
    public Optional<ReadOnlyTaskmaster> readTaskmaster() throws DataConversionException {
        return readTaskmaster(taskmasterFilePath);
    }

    /**
     * Similar to {@link #readTaskmaster()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskmaster> readTaskmaster(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskmaster> jsonTaskmaster = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskmaster.class);

        if (!jsonTaskmaster.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaskmaster.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskmaster(ReadOnlyTaskmaster taskmaster) throws IOException {
        saveTaskmaster(taskmaster, taskmasterFilePath);
    }

    /**
     * Similar to {@link #saveTaskmaster(ReadOnlyTaskmaster)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskmaster(ReadOnlyTaskmaster taskmaster, Path filePath) throws IOException {
        requireNonNull(taskmaster);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskmaster(taskmaster), filePath);
    }

    @Override
    public void saveSessionList(ReadOnlyTaskmaster taskmaster) throws IOException {
        saveSessionList(taskmaster, sessionListFilePath);
    }

    /**
     * Saves the SessionList in a Taskmaster to the filepath specified.
     */
    @Override
    public void saveSessionList(ReadOnlyTaskmaster taskmaster, Path filePath) throws IOException {
        requireNonNull(taskmaster);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSessionList(taskmaster), filePath);
    }

    /**
     * See {@link #readSessionList(Path)}
     */
    public Optional<SessionList> readSessionList() throws DataConversionException {
        return readSessionList(sessionListFilePath);
    }

    /**
     * Gets SessionList from the filePath specified.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<SessionList> readSessionList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSessionList> jsonSerializableSessionList = JsonUtil.readJsonFile(
                filePath, JsonSerializableSessionList.class);

        if (!jsonSerializableSessionList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerializableSessionList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
