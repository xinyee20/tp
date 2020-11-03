package team.serenity.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.userprefs.ReadOnlyUserPrefs;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.storage.question.QuestionStorage;
import team.serenity.storage.userprefs.UserPrefsStorage;

/**
 * API of the Storage component.
 */
public interface Storage extends UserPrefsStorage, SerenityStorage, QuestionStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ---- Serenity ----//

    @Override
    Path getSerenityFilePath();

    @Override
    Optional<ReadOnlySerenity> readSerenity() throws IllegalValueException, DataConversionException;

}
