package team.serenity.logic.commands.question;

import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G01;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_1;
import static team.serenity.logic.commands.CommandTestUtil.VALID_QN_DESC_B;
import static team.serenity.logic.commands.CommandTestUtil.assertCommandFailure;
import static team.serenity.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.serenity.logic.commands.question.AddQnCommand.MESSAGE_DUPLICATE_QUESTION;
import static team.serenity.testutil.TypicalGroups.getTypicalSerenity;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.testutil.question.QuestionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddQnCommand}.
 */
public class AddQnCommandIntegrationTest {

    private Model model;
    private final GroupName groupName = new GroupName(VALID_GROUP_NAME_G01);
    private final LessonName lessonName = new LessonName(VALID_LESSON_NAME_1_1);
    private final GroupContainsKeywordPredicate grpPredicate =
        new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01);
    private final LessonContainsKeywordPredicate lsnPredicate =
        new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1);
    private final QuestionFromGroupLessonPredicate qnPredicate =
        new QuestionFromGroupLessonPredicate(groupName, lessonName);

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(getTypicalSerenity(), getTypicalQuestionManager(), new UserPrefs());
        this.model.updateFilteredGroupList(grpPredicate);
        this.model.updateFilteredLessonList(lsnPredicate);
    }

    @Test
    public void execute_newQuestion_success() {
        Question validQuestion = new QuestionBuilder().withDescription(VALID_QN_DESC_B).build();

        Model expectedModel = new ModelManager(this.model.getSerenity(),
            this.model.getQuestionManager(), new UserPrefs());
        expectedModel.addQuestion(validQuestion);
        expectedModel.updateFilteredGroupList(grpPredicate);
        expectedModel.updateFilteredLessonList(lsnPredicate);
        expectedModel.updateFilteredQuestionList(qnPredicate);

        assertCommandSuccess(new AddQnCommand(validQuestion.getDescription()), this.model,
                String.format(AddQnCommand.MESSAGE_SUCCESS, validQuestion), expectedModel);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question questionInList = this.model.getQuestionManager().getListOfQuestions().get(0);
        assertCommandFailure(new AddQnCommand(questionInList.getDescription()), this.model,
            MESSAGE_DUPLICATE_QUESTION);
    }

}
