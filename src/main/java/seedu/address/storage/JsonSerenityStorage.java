package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlySerenity;

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

    public void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException {
        requireNonNull(serenity);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSerenity(serenity), filePath);
    }

}
