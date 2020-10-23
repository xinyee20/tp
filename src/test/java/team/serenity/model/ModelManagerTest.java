package team.serenity.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.GuiSettings;
import team.serenity.model.group.Question;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.userprefs.UserPrefs;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSerenityFilePath(Paths.get("data/serenity.json"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSerenityFilePath(Paths.get("new/serenity/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGroup(null));
    }

    @Test
    public void hasGroup_groupNotInSerenity_returnsFalse() {
        // TODO
    }

    @Test
    public void hasGroup_groupInSerenity_returnsTrue() {
        // TODO
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGroupList().remove(0));
    }

    // ========== QuestionManager ==========

    @Test
    public void hasQuestionManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasQuestion(null));
        assertFalse(modelManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void deleteQuestionManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteQuestion(null));
        Question newQuestion = QUESTION_A;
        assertThrows(QuestionNotFoundException.class, () -> modelManager.deleteQuestion(newQuestion));
        modelManager.addQuestion(newQuestion);
        modelManager.deleteQuestion(newQuestion);
        assertFalse(modelManager.getFilteredQuestionList().contains(newQuestion));
        assertFalse(modelManager.getFilteredQuestionList()
                .contains(newQuestion));
    }

    @Test
    public void addQuestionManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addQuestion(null));
        Question newQuestion = QUESTION_A;
        modelManager.addQuestion(newQuestion);
        assertTrue(modelManager.getFilteredQuestionList().contains(newQuestion));
        assertFalse(modelManager.getFilteredQuestionList().contains(QUESTION_B));
    }

    @Test
    public void setQuestionManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setQuestion(null, null));
        Question newQuestionA = QUESTION_A;
        Question newQuestionB = QUESTION_B;
        assertThrows(QuestionNotFoundException.class, () -> modelManager
                .setQuestion(newQuestionA, newQuestionB)); // Event where tries to set non-existent activity.

        modelManager.addQuestion(newQuestionA);
        modelManager.setQuestion(newQuestionA, newQuestionB);
        assertTrue(modelManager.getFilteredQuestionList().contains(newQuestionB));
        assertFalse(modelManager.getFilteredQuestionList().contains(newQuestionA));
    }

    @Test
    public void updateFilteredQuestionManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredQuestionList(null));
    }


    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(userPrefs);
        ModelManager modelManagerCopy = new ModelManager(userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // TODO: different serenity -> returns false

        // TODO: different filteredGroupList -> returns false
        /*
        FOR REFERENCE (AB3)
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(userPrefs)));
         */

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        // TODO: different userPrefs -> returns false
        /*
        FOR REFERENCE (AB3)
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressbookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(differentUserPrefs)));
         */
    }

}
