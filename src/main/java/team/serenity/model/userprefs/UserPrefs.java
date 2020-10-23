package team.serenity.model.userprefs;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Objects;

import team.serenity.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private static final Path DEFAULT_FOLDER_PATH = Path.of("data");

    private GuiSettings guiSettings = new GuiSettings();
    private Path serenityFilePath = DEFAULT_FOLDER_PATH.resolve("serenity.json");
    private Path questionStorageFilePath = DEFAULT_FOLDER_PATH.resolve("question.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
    }

    public GuiSettings getGuiSettings() {
        return this.guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    // Serenity
    public Path getSerenityFilePath() {
        return this.serenityFilePath;
    }

    public void setSerenityFilePath(Path serenityFilePath) {
        requireNonNull(serenityFilePath);
        this.serenityFilePath = serenityFilePath;
    }

    // Question Manager

    @Override
    public Path getQuestionStorageFilePath() {
        return this.questionStorageFilePath;
    }

    public void setQuestionStorageFilePath(Path questionStorageFilePath) {
        requireNonNull(questionStorageFilePath);
        this.questionStorageFilePath = questionStorageFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;
        return this.guiSettings.equals(o.guiSettings)
            && this.serenityFilePath.equals(o.serenityFilePath)
            && this.questionStorageFilePath.equals(o.questionStorageFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guiSettings, this.serenityFilePath, this.questionStorageFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + this.guiSettings);
        sb.append("\nLocal data file location : " + this.serenityFilePath);
        sb.append("\nLocal question data file location : " + this.questionStorageFilePath);
        return sb.toString();
    }

}
