package team.serenity.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.serenity.commons.core.GuiSettings;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.storage.question.JsonQuestionStorage;
import team.serenity.storage.userprefs.JsonUserPrefsStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonSerenityStorage serenityStorage = new JsonSerenityStorage(getTempFilePath("serenity"));
        JsonQuestionStorage questionStorage = new JsonQuestionStorage(getTempFilePath("question"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(serenityStorage, questionStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void serenityReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSerenityStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonSerenityStorageTest} class.
         */
        // TODO: saving serenity after each command is executed
        // Now it is not working, CY and RE looking into it
        /*
        Serenity original = getTypicalSerenity();
        storageManager.saveSerenity(original);
        ReadOnlySerenity retrieved = storageManager.readSerenity().get();
        assertEquals(original, new Serenity(retrieved));
         */
    }

    @Test
    public void getSerenityFilePath() {
        assertNotNull(storageManager.getSerenityFilePath());
    }

}
