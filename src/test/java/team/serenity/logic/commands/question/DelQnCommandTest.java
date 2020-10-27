package team.serenity.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.serenity.logic.commands.CommandTestUtil.assertDelQnCommandFailure;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.testutil.question.QuestionBuilder;

class DelQnCommandTest {

    private Model model;
    private QuestionManager questionManager;

    @BeforeEach
    public void setUp() {
        Question newQuestion = new QuestionBuilder().build();
        this.questionManager = new QuestionManager();
        this.questionManager.addQuestion(newQuestion);
        this.model = new ModelManager(new Serenity(), this.questionManager, new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {

        Question questionToDelete = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        DelQnCommand delQnCommand = new DelQnCommand(INDEX_FIRST);

        String expectedMessage = String.format(DelQnCommand.MESSAGE_DELETE_QUESTION_SUCCESS, questionToDelete);

        ModelManager expectedModel = new ModelManager(new Serenity(), this.questionManager, new UserPrefs());
        expectedModel.deleteQuestion(questionToDelete);

        assertCommandSuccess(delQnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        DelQnCommand delQnCommand = new DelQnCommand(outOfBoundIndex);
        String expectedMessage = MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;

        assertDelQnCommandFailure(delQnCommand, model, expectedMessage);
    }

    @Test
    public void test_equals() {
        DelQnCommand delQnFirstCommand = new DelQnCommand(INDEX_FIRST);
        DelQnCommand delQnSecondCommand = new DelQnCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(delQnFirstCommand, delQnFirstCommand);

        // same values -> returns true
        DelQnCommand delQnFirstCommandCopy = new DelQnCommand(INDEX_FIRST);
        assertEquals(delQnFirstCommand, delQnFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, delQnFirstCommand);

        // null -> returns false
        assertNotEquals(null, delQnFirstCommand);

        // different person -> returns false
        assertNotEquals(delQnFirstCommand, delQnSecondCommand);
    }
}
