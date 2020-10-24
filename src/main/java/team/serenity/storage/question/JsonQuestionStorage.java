package team.serenity.storage.question;

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
import team.serenity.model.managers.ReadOnlyQuestionManager;

/**
 * A class to access QuestionManager data stored as a json file on the hard disk.
 */
public class JsonQuestionStorage implements QuestionStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonQuestionStorage.class);

    private Path filePath;

    public JsonQuestionStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getQuestionManagerStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyQuestionManager> readQuestionManager() throws DataConversionException {
        return readQuestionManager(filePath);
    }

    /**
     * Similar to {@link #readQuestionManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyQuestionManager> readQuestionManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuestionManager> jsonQuestionManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuestionManager.class);
        if (jsonQuestionManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuestionManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveQuestionManager(ReadOnlyQuestionManager questionManager) throws IOException {
        saveQuestionManager(questionManager, filePath);
    }

    /**
     * Similar to {@link #saveQuestionManager(ReadOnlyQuestionManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveQuestionManager(ReadOnlyQuestionManager questionManager, Path filePath) throws IOException {
        requireNonNull(questionManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuestionManager(questionManager), filePath);
    }

}
