package team.serenity.logic.commands.question;

import static team.serenity.testutil.TypicalGroups.getTypicalSerenity;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.question.Question;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.testutil.question.QuestionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddQnCommand}.
 */
public class AddQnCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(getTypicalSerenity(), getTypicalQuestionManager(), new UserPrefs());
        this.model.updateFilteredGroupList(new GroupContainsKeywordPredicate("G04"));
        this.model.updateFilteredLessonList(new LessonContainsKeywordPredicate("1-1"));
    }

    @Test
    public void execute_newQuestion_success() {
        Question validQuestion = new QuestionBuilder().build();

        Model expectedModel = new ModelManager(this.model.getSerenity(),
            this.model.getQuestionManager(), new UserPrefs());
        expectedModel.addQuestion(validQuestion);

        // TODO: Addqn Integration Testing successful adding of question
        /*
        assertAddQnCommandSuccess(new AddQnCommand(validQuestion.getDescription()), this.model,
                String.format(AddQnCommand.MESSAGE_SUCCESS, validQuestion), expectedModel);
         */
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        // TODO: Addqn Integration Testing adding duplicate question
        /*
        Question questionInList = this.model.getQuestionManager().getListOfQuestions().get(0);
        assertAddQnCommandFailure(new AddQnCommand(questionInList.getDescription()), this.model,
            MESSAGE_DUPLICATE_QUESTION);
         */
    }

}
