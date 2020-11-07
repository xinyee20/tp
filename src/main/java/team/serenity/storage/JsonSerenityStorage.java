package team.serenity.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.serenity.commons.core.LogsCenter;
import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.commons.util.FileUtil;
import team.serenity.commons.util.JsonUtil;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.managers.ReadOnlyGroupManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.managers.Serenity;


/**
 * A class to access Serenity data stored as a json file on the hard disk.
 */
public class JsonSerenityStorage implements SerenityStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSerenityStorage.class);

    private Path filePath;

    public JsonSerenityStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSerenityFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity() throws IllegalValueException, DataConversionException {
        return readSerenity(this.filePath);
    }

    /**
     * Similar to {@link #readSerenity()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySerenity> readSerenity(Path filePath)
        throws IllegalValueException, DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSerenity> jsonSerenity = JsonUtil.readJsonFile(
            filePath, JsonSerializableSerenity.class);
        if (jsonSerenity.isEmpty()) {
            return Optional.empty();
        }

        try {
            Serenity fromData = jsonSerenity.get().toModelType();
            return Optional.of(fromData);
        } catch (DuplicateException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (IllegalArgumentException ive) {
            throw new DataConversionException(ive);
        }
    }



    @Override
    public void saveSerenity(ReadOnlyGroupManager groupManager) throws IOException {
        this.saveSerenity(groupManager, this.filePath);
    }



    /**
     * Saves group to storage
     * @throws IOException
     */
    public void saveSerenity(ReadOnlyGroupManager groupManager, Path filePath) throws IOException {
        requireNonNull(groupManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSerenity(groupManager), filePath);
    }
}
