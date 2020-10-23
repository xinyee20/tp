package team.serenity.model.userprefs;

import java.nio.file.Path;

import team.serenity.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getSerenityFilePath();

    Path getQuestionStorageFilePath();
}
