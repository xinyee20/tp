package team.serenity.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.EDITED_QN_A;
import static team.serenity.logic.commands.CommandTestUtil.EDITED_QN_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GRP_GROUP_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LSN_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_QN_DESC_B;
import static team.serenity.logic.commands.CommandTestUtil.assertQuestionCommandFailure;
import static team.serenity.logic.commands.CommandTestUtil.assertQuestionCommandSuccess;
import static team.serenity.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_DUPLICATE_QUESTION;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_EDIT_QUESTION_SUCCESS;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.question.EditQnCommand.EditQuestionDescriptor;
import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.testutil.question.EditQuestionDescriptorBuilder;
import team.serenity.testutil.question.QuestionBuilder;

class EditQnCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        /*
        Question editedQuestion = new QuestionBuilder().build();
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(editedQuestion).build();
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST, descriptor);

        String expectedMessage =
                String.format(EditQnCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new Serenity(), this.model.getQuestionManager(), new UserPrefs());

        expectedModel.addQuestion(editedQuestion);

        assertQuestionCommandSuccess(editCommand, this.model, expectedMessage, expectedModel);
         */
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastQuestion = Index.fromOneBased(this.model.getFilteredQuestionList().size());
        Question lastQuestion = this.model.getFilteredQuestionList().get(indexLastQuestion.getZeroBased());

        QuestionBuilder questionInList = new QuestionBuilder(lastQuestion);
        Question editedQuestion = questionInList.withGroupName(VALID_GRP_GROUP_B)
                .withLessonName(VALID_LSN_B)
                .withDescription(VALID_QN_DESC_B)
                .build();

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GRP_GROUP_B)
                .withLessonName(VALID_LSN_B)
                .withDescription(VALID_QN_DESC_B)
                .build();

        EditQnCommand editCommand = new EditQnCommand(indexLastQuestion, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new Serenity(), this.model.getQuestionManager(), new UserPrefs());

        expectedModel.setQuestion(lastQuestion, editedQuestion);

        assertQuestionCommandSuccess(editCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // TODO: WENJIN
    }

    @Test
    public void execute_filteredList_success() {
        // TODO: WENJIN
    }

    @Test
    public void execute_duplicateQuestionUnfilteredList_failure() {
        Question firstQuestion = this.model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(firstQuestion).build();
        EditQnCommand editCommand = new EditQnCommand(INDEX_SECOND, descriptor);

        assertQuestionCommandFailure(editCommand, this.model, MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_duplicateQuestionFilteredList_failure() {
        showQuestionAtIndex(this.model, INDEX_FIRST);

        // edit question in filtered list into a duplicate in question manager
        Question questionInList = this.model.getQuestionManager().getListOfQuestions().get(INDEX_SECOND.getZeroBased());
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST,
                new EditQuestionDescriptorBuilder(questionInList).build());

        assertQuestionCommandFailure(editCommand, this.model, MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_invalidQuestionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(this.model.getFilteredQuestionList().size() + 1);
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GRP_GROUP_B)
                .build();
        EditQnCommand editCommand = new EditQnCommand(outOfBoundIndex, descriptor);
        String expectedMessage = String.format(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        assertQuestionCommandFailure(editCommand, this.model, expectedMessage);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Question Manager
     */
    @Test
    public void execute_invalidQuestionIndexFilteredList_failure() {
        // TODO: WENJIN
    }

    @Test
    public void equals() {
        final EditQnCommand standardCommand = new EditQnCommand(INDEX_FIRST, EDITED_QN_A);

        // same values -> returns true
        EditQuestionDescriptor copyDescriptor = new EditQuestionDescriptor(EDITED_QN_A);
        EditQnCommand commandWithSameValues = new EditQnCommand(INDEX_FIRST, copyDescriptor);

        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ViewQnCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditQnCommand(INDEX_SECOND, EDITED_QN_A));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditQnCommand(INDEX_FIRST, EDITED_QN_B));
    }

}
