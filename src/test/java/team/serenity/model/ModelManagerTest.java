package team.serenity.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.GuiSettings;
import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.testutil.question.QuestionManagerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), this.modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), this.modelManager.getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSerenityFilePath(Paths.get("data/serenity.json"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        this.modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, this.modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSerenityFilePath(Paths.get("new/serenity/file/path"));
        assertEquals(oldUserPrefs, this.modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        this.modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, this.modelManager.getGuiSettings());
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> this.modelManager.getFilteredGroupList().remove(0));
    }

    // ========== QuestionManager ==========

    @Test
    public void getQuestionManager() {
        assertEquals(this.modelManager.getQuestionManager(), new QuestionManager());
    }

    @Test
    public void setQuestionManager_nullQuestionManager_throwsNulPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.setQuestionManager(null));
    }

    @Test
    public void setQuestionManager_validQuestionManager_setsQuestionManager() {
        QuestionManager newQuestionManager = new QuestionManagerBuilder()
                .withQuestion(QUESTION_A).withQuestion(QUESTION_B).build();
        this.modelManager.setQuestionManager(newQuestionManager);
        assertEquals(newQuestionManager, this.modelManager.getQuestionManager());
    }

    @Test
    public void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.hasQuestion(null));
    }

    @Test
    public void hasQuestion_questionNotInModelManager_returnsFalse() {
        assertFalse(this.modelManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void deleteQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.deleteQuestion(null));
    }

    @Test
    public void deleteQuestion_questionNotInModelManager_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> this.modelManager.deleteQuestion(QUESTION_A));
    }

    @Test
    public void deleteQuestion_questionInModelManager_returnsTrue() {
        this.modelManager.addQuestion(QUESTION_A);
        this.modelManager.deleteQuestion(QUESTION_A);
        assertFalse(this.modelManager.getFilteredQuestionList().contains(QUESTION_A));
    }

    @Test
    public void addQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addQuestion(null));
    }

    @Test
    public void addQuestion_questionInModelManager_throwsDuplicateQuestionException() {
        this.modelManager.addQuestion(QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () -> this.modelManager.addQuestion(QUESTION_A));
    }

    @Test
    public void addQuestion_validQuestionToAdd_addsQuestion() {
        QuestionManager expectedQuestionManager = new QuestionManagerBuilder().withQuestion(QUESTION_A).build();
        ModelManager expectedModelManager = new ModelManager(new Serenity(), expectedQuestionManager, new UserPrefs());
        this.modelManager.addQuestion(QUESTION_A);
        assertEquals(expectedModelManager, this.modelManager);
    }

    @Test
    public void setQuestion_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.setQuestion(null, QUESTION_A));
        assertThrows(NullPointerException.class, () -> this.modelManager.setQuestion(QUESTION_A, null));
        assertThrows(NullPointerException.class, () -> this.modelManager.setQuestion(null, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInModelManager_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> this.modelManager.setQuestion(QUESTION_A, QUESTION_B));
    }

    @Test
    public void setQuestion_editedQuestionInModelManager_throwsDuplicateQuestionException() {
        this.modelManager.addQuestion(QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () -> this.modelManager.setQuestion(QUESTION_A, QUESTION_A));
    }

    @Test
    public void setQuestion_validQuestionInputs_setsQuestion() {
        QuestionManager expectedQuestionManager = new QuestionManager();
        expectedQuestionManager.addQuestion(QUESTION_B);
        ModelManager expectedModelManager = new ModelManager(new Serenity(), expectedQuestionManager, new UserPrefs());
        this.modelManager.addQuestion(QUESTION_A);
        this.modelManager.setQuestion(QUESTION_A, QUESTION_B);
        assertEquals(expectedModelManager, this.modelManager);
    }

    @Test
    public void getFilteredQuestionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> this.modelManager.getFilteredQuestionList().remove(0));
    }

    @Test
    public void updateFilteredQuestionList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.modelManager.updateFilteredQuestionList(null));
    }


    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        Serenity serenity = new Serenity();
        QuestionManager questionManager = new QuestionManagerBuilder()
                .withQuestion(QUESTION_A).withQuestion(QUESTION_B).build();
        QuestionManager differentQuestionManager = new QuestionManager();

        // same values -> returns true
        this.modelManager = new ModelManager(serenity, questionManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(serenity, questionManager, userPrefs);
        assertTrue(this.modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(this.modelManager.equals(this.modelManager));

        // null -> returns false
        assertFalse(this.modelManager.equals(null));

        // different types -> returns false
        assertFalse(this.modelManager.equals(5));

        // ====================== State equal checks ==============================

        // ====================== Question Manager Attributes ==============================

        // different questionManager -> returns false
        assertFalse(this.modelManager.equals(new ModelManager(serenity, differentQuestionManager, userPrefs)));

        // different filteredQuestionList -> return false
        this.modelManager.updateFilteredQuestionList(new QuestionContainsKeywordPredicate(Arrays.asList("criteria")));
        assertFalse(this.modelManager.equals(new ModelManager(serenity, questionManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        this.modelManager.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);

    }

}
