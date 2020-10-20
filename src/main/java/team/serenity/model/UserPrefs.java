package team.serenity.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import team.serenity.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path serenityFilePath = Paths.get("data", "serenity.json");

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
            && this.serenityFilePath.equals(o.serenityFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guiSettings, this.serenityFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + this.guiSettings);
        sb.append("\nLocal data file location : " + this.serenityFilePath);
        return sb.toString();
    }

}
