package team.serenity.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.commons.util.JsonUtil;
import team.serenity.model.managers.QuestionManager;

class JsonSerializableQuestionManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableQuestionManagerTest");
    private static final Path TYPICAL_QUESTION_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionManager.json");
    private static final Path INVALID_QUESTION_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionManager.json");
    private static final Path DUPLICATE_QUESTION_FILE = TEST_DATA_FOLDER.resolve("duplicateQuestionManager.json");

    @Test
    public void toModelType_typicalQuestionFile_success() throws Exception {
        JsonSerializableQuestionManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTION_FILE,
            JsonSerializableQuestionManager.class).get();
        QuestionManager questionManagerFromFile = dataFromFile.toModelType();
        QuestionManager typicalQuestionManager = getTypicalQuestionManager();
        assertEquals(questionManagerFromFile, typicalQuestionManager);
    }

    @Test
    public void toModelType_invalidQuestionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestionManager dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTION_FILE,
            JsonSerializableQuestionManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQuestions_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestionManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTION_FILE,
            JsonSerializableQuestionManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableQuestionManager.MESSAGE_DUPLICATE_QUESTION,
            dataFromFile::toModelType);
    }

}
