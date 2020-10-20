package team.serenity.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.Serenity;

/**
 * Represents a storage for {@link Serenity}.
 */
public interface SerenityStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSerenityFilePath();

    /**
     * Returns Serenity data as a {@link ReadOnlySerenity}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySerenity> readSerenity() throws DataConversionException, IOException;

    /**
     * @see #getSerenityFilePath()
     */
    Optional<ReadOnlySerenity> readSerenity(Path filePath)
        throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySerenity} to the storage.
     *
     * @param serenity cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSerenity(ReadOnlySerenity serenity) throws IOException;

    /**
     * @see #saveSerenity(ReadOnlySerenity)
     */
    void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException;

}
