package team.serenity.logic.commands.question;

import static team.serenity.logic.commands.CommandTestUtil.assertQuestionViewsQuestionTabCommandSuccess;
import static team.serenity.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewQnCommand.
 */
class ViewQnCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
        this.expectedModel = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertQuestionViewsQuestionTabCommandSuccess(new ViewQnCommand(), this.model,
                ViewQnCommand.MESSAGE_SUCCESS, this.expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionAtIndex(this.model, INDEX_FIRST);
        assertQuestionViewsQuestionTabCommandSuccess(new ViewQnCommand(), this.model,
                ViewQnCommand.MESSAGE_SUCCESS, this.expectedModel);
    }

}
