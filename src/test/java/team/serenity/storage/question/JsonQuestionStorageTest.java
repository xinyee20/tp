package team.serenity.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_C;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.testutil.question.QuestionBuilder;

class JsonQuestionStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonQuestionStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void getQuestionManagerStorageFilePath() {
        JsonQuestionStorage questionStorage = new JsonQuestionStorage(TEST_DATA_FOLDER);
        assertEquals(TEST_DATA_FOLDER, questionStorage.getQuestionManagerStorageFilePath());
    }

    @Test
    public void readQuestionManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readQuestionManager(null));
    }

    private Optional<ReadOnlyQuestionManager> readQuestionManager(String filePath) throws Exception {
        return new JsonQuestionStorage(Paths.get(filePath)).readQuestionManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readQuestionManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
            readQuestionManager("notJsonFormatQuestionManager.json"));
    }

    @Test
    public void readQuestionManager_invalidQuestionQuestionManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readQuestionManager("invalidQuestionManager.json"));
    }

    @Test
    public void readQuestionManager_invalidAndValidQuestionQuestionManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readQuestionManager("invalidAndValidQuestionManager.json"));
    }

    @Test
    public void readAndSaveQuestionManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempQuestionManager.json");
        QuestionManager original = getTypicalQuestionManager();
        JsonQuestionStorage jsonQuestionStorage = new JsonQuestionStorage(filePath);

        //Save in new file and read back
        jsonQuestionStorage.saveQuestionManager(original, filePath);
        ReadOnlyQuestionManager readBack = jsonQuestionStorage.readQuestionManager(filePath).get();
        assertEquals(original, new QuestionManager(readBack));

        //Modify data, overwrite exiting file and read back
        Question newQuestion = new QuestionBuilder(QUESTION_B).withGroupName("G10").withLessonName("1-1").build();
        original.addQuestion(newQuestion);
        original.deleteQuestion(QUESTION_A);
        jsonQuestionStorage.saveQuestionManager(original, filePath);
        readBack = jsonQuestionStorage.readQuestionManager(filePath).get();
        assertEquals(original, new QuestionManager(readBack));

        // Save and read without specifying file path
        Question newQuestion1 = new QuestionBuilder(QUESTION_C).withGroupName("G10").withLessonName("1-1").build();
        original.addQuestion(newQuestion1);
        jsonQuestionStorage.saveQuestionManager(original);
        readBack = jsonQuestionStorage.readQuestionManager().get();
        assertEquals(original, new QuestionManager(readBack));
    }

    @Test
    public void saveQuestionManager_nullQuestionManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestionManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code questionManager} at specified {@code filePath}.
     */
    private void saveQuestionManager(ReadOnlyQuestionManager questionManager, String filePath) {
        try {
            new JsonQuestionStorage(Paths.get(filePath))
                    .saveQuestionManager(questionManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file ", ioe);
        }
    }

    @Test
    public void saveQuestionManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestionManager(new QuestionManager(), null));
    }

}
