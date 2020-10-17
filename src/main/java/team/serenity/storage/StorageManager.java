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
    private UserPrefsStorage userPrefsStorage;
    private SerenityStorage serenityStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage}, {@code UserPrefStorage} and {@code
     * SerenityStorage}.
     */
    public StorageManager(SerenityStorage serenityStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.serenityStorage = serenityStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Serenity methods ==============================

    @Override
    public Path getSerenityFilePath() {
        return serenityStorage.getSerenityFilePath();
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity() throws DataConversionException, IOException {
        return readSerenity(serenityStorage.getSerenityFilePath());
    }

    @Override
    public Optional<ReadOnlySerenity> readSerenity(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return serenityStorage.readSerenity(filePath);
    }

    @Override
    public void saveSerenity(ReadOnlySerenity serenity) throws IOException {
        saveSerenity(serenity, serenityStorage.getSerenityFilePath());
    }

    @Override
    public void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        serenityStorage.saveSerenity(serenity, filePath);
    }
}
