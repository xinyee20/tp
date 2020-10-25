package team.serenity.storage.question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;

/**
 * Represents a storage for {@link QuestionManager}.
 */
public interface QuestionStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getQuestionManagerStorageFilePath();

    /**
     * Returns QuestionManager data as a {@link ReadOnlyQuestionManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyQuestionManager> readQuestionManager() throws DataConversionException, IOException;

    /**
     * @see #readQuestionManager()
     */
    Optional<ReadOnlyQuestionManager> readQuestionManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyQuestionManager} to the storage.
     *
     * @param questionManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuestionManager(ReadOnlyQuestionManager questionManager) throws IOException;

    /**
     * @see #saveQuestionManager(ReadOnlyQuestionManager)
     */
    void saveQuestionManager(ReadOnlyQuestionManager questionManager, Path filePath) throws IOException;

}
