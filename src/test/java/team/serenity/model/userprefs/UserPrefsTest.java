package team.serenity.model.userprefs;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    private static final Path DEFAULT_FOLDER_PATH = Path.of("data");
    private static final Path QUESTION_STORAGE_FILE_PATH = DEFAULT_FOLDER_PATH.resolve("question.json");

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setSerenityFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setSerenityFilePath(null));
    }

    @Test
    public void setQuestionStorageFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setQuestionStorageFilePath(null));
    }

    @Test
    public void getQuestionStorageFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertTrue(userPrefs.getQuestionStorageFilePath().equals(QUESTION_STORAGE_FILE_PATH));
    }

}
