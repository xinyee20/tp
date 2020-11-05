package team.serenity.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.managers.ReadOnlyGroupManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.managers.Serenity;


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
    Optional<ReadOnlySerenity> readSerenity() throws IllegalValueException, DataConversionException;

    /**
     * @see #getSerenityFilePath()
     */
    Optional<ReadOnlySerenity> readSerenity(Path filePath)
        throws IllegalValueException, DataConversionException;

    /**
     * Saves Serenity with the given {@code ReadOnlyGroupManager}
     */
    void saveSerenity(ReadOnlyGroupManager manager) throws IOException;


}
