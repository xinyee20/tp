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
import team.serenity.model.ReadOnlySerenity;

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
        return filePath;
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity() throws DataConversionException {
        return readSerenity(filePath);
    }

    /**
     * Similar to {@link #readSerenity()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySerenity> readSerenity(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSerenity> jsonSerenity = JsonUtil.readJsonFile(
            filePath, JsonSerializableSerenity.class);
        if (jsonSerenity.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerenity.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSerenity(ReadOnlySerenity serenity) throws IOException {
        saveSerenity(serenity, filePath);
    }

    /**
     * Similar to {@link #saveSerenity(ReadOnlySerenity)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException {
        requireNonNull(serenity);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSerenity(serenity), filePath);
    }
}
