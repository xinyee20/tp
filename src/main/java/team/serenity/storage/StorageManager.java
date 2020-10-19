package team.serenity.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.serenity.commons.core.LogsCenter;
import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.ReadOnlyUserPrefs;
import team.serenity.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SerenityStorage serenityStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code SerenityStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(SerenityStorage serenityStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.serenityStorage = serenityStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return this.userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return this.userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        this.userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Serenity methods ==============================

    @Override
    public Path getSerenityFilePath() {
        return this.serenityStorage.getSerenityFilePath();
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity() throws DataConversionException, IOException {
        return readSerenity(this.serenityStorage.getSerenityFilePath());
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity(Path filePath)
        throws DataConversionException, IOException {
        this.logger.fine("Attempting to read data from file: " + filePath);
        return this.serenityStorage.readSerenity(filePath);
    }

    @Override
    public void saveSerenity(ReadOnlySerenity serenity) throws IOException {
        saveSerenity(serenity, this.serenityStorage.getSerenityFilePath());
    }

    @Override
    public void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException {
        this.logger.fine("Attempting to write to data file: " + filePath);
        this.serenityStorage.saveSerenity(serenity, filePath);
    }
}
