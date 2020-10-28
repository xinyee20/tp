package team.serenity.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.commons.core.Messages.MESSAGE_QUESTIONS_LISTED_OVERVIEW;
import static team.serenity.logic.commands.CommandTestUtil.assertQuestionViewsQuestionTabCommandSuccess;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindQnCommand}.
 */
class FindQnCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
        this.expectedModel = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
    }

    @Test
    public void equals() {
        QuestionContainsKeywordPredicate firstPredicate =
                new QuestionContainsKeywordPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordPredicate secondPredicate =
                new QuestionContainsKeywordPredicate(Collections.singletonList("second"));

        FindQnCommand findFirstCommand = new FindQnCommand(firstPredicate);
        FindQnCommand findSecondCommand = new FindQnCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindQnCommand findFirstCommandCopy = new FindQnCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noQuestionFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 0, "questions")
                + String.format("\nUse the \"%s\" command to show all questions", ViewQnCommand.COMMAND_WORD);
        QuestionContainsKeywordPredicate predicate = preparePredicate(" ");
        FindQnCommand command = new FindQnCommand(predicate);
        this.expectedModel.updateFilteredQuestionList(predicate);
        assertQuestionViewsQuestionTabCommandSuccess(command, this.model, expectedMessage, this.expectedModel);
        assertEquals(Collections.emptyList(), this.model.getFilteredQuestionList());
    }

    @Test
    public void execute_multipleKeywords_multipleQuestionsFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 1, "questions")
                + String.format("\nUse the \"%s\" command to show all questions", ViewQnCommand.COMMAND_WORD);
        QuestionContainsKeywordPredicate predicate = preparePredicate("deadline");
        FindQnCommand command = new FindQnCommand(predicate);
        this.expectedModel.updateFilteredQuestionList(predicate);
        assertQuestionViewsQuestionTabCommandSuccess(command, this.model, expectedMessage, this.expectedModel);
        assertEquals(Collections.singletonList(QUESTION_A), this.model.getFilteredQuestionList());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}

