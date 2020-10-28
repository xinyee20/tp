package team.serenity.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.EDITED_QN_A;
import static team.serenity.logic.commands.CommandTestUtil.EDITED_QN_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LSN_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_QN_DESC_B;
import static team.serenity.logic.commands.CommandTestUtil.assertQuestionCommandFailure;
import static team.serenity.logic.commands.CommandTestUtil.assertQuestionViewsQuestionTabCommandSuccess;
import static team.serenity.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_DUPLICATE_QUESTION;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_EDIT_QUESTION_SUCCESS;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_GROUP_NOT_FOUND_FORMAT;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_LESSON_NOT_FOUND_FORMAT;
import static team.serenity.logic.commands.question.EditQnCommand.MESSAGE_NOT_EDITED;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.question.EditQnCommand.EditQuestionDescriptor;
import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
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
        Question editedQuestion = questionInList.withGroupName(VALID_GROUP_NAME_B)
                .withLessonName(VALID_LSN_B)
                .withDescription(VALID_QN_DESC_B)
                .build();

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_B)
                .withLessonName(VALID_LSN_B)
                .withDescription(VALID_QN_DESC_B)
                .build();

        EditQnCommand editCommand = new EditQnCommand(indexLastQuestion, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new Serenity(), this.model.getQuestionManager(), new UserPrefs());

        expectedModel.setQuestion(lastQuestion, editedQuestion);

        assertQuestionViewsQuestionTabCommandSuccess(editCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // TODO: WJ execute_noFieldSpecifiedUnfilteredList_success
    }

    @Test
    public void execute_noFieldSpecifiedFilteredList_success() {
        // TODO: WJ execute_noFieldSpecifiedFilteredList_success
    }

    @Test
    public void execute_questionNotEditedUnfilteredList_failure() {
        Question questionToEdit = this.model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST,
                new EditQuestionDescriptorBuilder(questionToEdit).build());

        assertQuestionCommandFailure(editCommand, this.model, MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_questionNotEditedQuestionFilteredList_failure() {
        showQuestionAtIndex(this.model, INDEX_FIRST);

        // edit question in filtered list in question manager with a same fields
        Question questionToEdit = this.model.getQuestionManager().getListOfQuestions().get(INDEX_FIRST.getZeroBased());
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST,
                new EditQuestionDescriptorBuilder(questionToEdit).build());

        assertQuestionCommandFailure(editCommand, this.model, MESSAGE_NOT_EDITED);
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
    public void execute_groupNameNotFoundUnfilteredList_failure() {
        Question questionToEdit = this.model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        assertFalse(this.model.hasGroupName(new GroupName("G10")));
        EditQuestionDescriptor editedQuestionWithGroupNameNotFound =
            new EditQuestionDescriptorBuilder(questionToEdit).withGroupName("G10").build();

        String expectedMessage = String.format(MESSAGE_GROUP_NOT_FOUND_FORMAT,
                editedQuestionWithGroupNameNotFound.getGroupName().get().groupName);
        EditQnCommand editCommand = new EditQnCommand(INDEX_SECOND, editedQuestionWithGroupNameNotFound);

        assertQuestionCommandFailure(editCommand, this.model, expectedMessage);
    }

    @Test
    public void execute_groupNameNotFoundFilteredList_failure() {
        showQuestionAtIndex(this.model, INDEX_FIRST);

        // edit question in filtered list in question manager with a group name that don't exists in group manager
        Question questionToEdit = this.model.getQuestionManager().getListOfQuestions().get(INDEX_FIRST.getZeroBased());
        assertFalse(this.model.hasGroupName(new GroupName("G10")));
        EditQuestionDescriptor editedQuestionWithGroupNameNotFound =
                new EditQuestionDescriptorBuilder(questionToEdit).withGroupName("G10").build();

        String expectedMessage = String.format(MESSAGE_GROUP_NOT_FOUND_FORMAT,
                editedQuestionWithGroupNameNotFound.getGroupName().get().groupName);
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST, editedQuestionWithGroupNameNotFound);

        assertQuestionCommandFailure(editCommand, this.model, expectedMessage);
    }

    @Test
    public void execute_lessonNameNotFoundUnfilteredList_failure() {
        Question questionToEdit = this.model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        assertFalse(this.model.ifTargetGroupHasLessonName(questionToEdit.getGroupName(), new LessonName("10-1")));

        EditQuestionDescriptor editedQuestionWithLessonNameNotFound =
                new EditQuestionDescriptorBuilder(questionToEdit).withLessonName("10-1").build();
        String expectedMessage = String.format(MESSAGE_LESSON_NOT_FOUND_FORMAT,
                editedQuestionWithLessonNameNotFound.getLessonName().get().lessonName);
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST, editedQuestionWithLessonNameNotFound);

        assertQuestionCommandFailure(editCommand, this.model, expectedMessage);
    }

    @Test
    public void execute_lessonNameNotFoundFilteredList_failure() {
        showQuestionAtIndex(this.model, INDEX_FIRST);

        // edit question in filtered list in question manager with a group name that don't exists in group manager
        Question questionToEdit = this.model.getQuestionManager().getListOfQuestions().get(INDEX_FIRST.getZeroBased());
        assertFalse(this.model.ifTargetGroupHasLessonName(questionToEdit.getGroupName(), new LessonName("10-1")));

        EditQuestionDescriptor editedQuestionWithLessonNameNotFound =
                new EditQuestionDescriptorBuilder(questionToEdit).withLessonName("10-1").build();

        String expectedMessage = String.format(MESSAGE_LESSON_NOT_FOUND_FORMAT,
                editedQuestionWithLessonNameNotFound.getLessonName().get().lessonName);
        EditQnCommand editCommand = new EditQnCommand(INDEX_FIRST, editedQuestionWithLessonNameNotFound);

        assertQuestionCommandFailure(editCommand, this.model, expectedMessage);
    }

    @Test
    public void execute_invalidQuestionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(this.model.getFilteredQuestionList().size() + 1);
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_B)
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
        // TODO: WJ execute_invalidQuestionIndexFilteredList_failure
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

    @Test
    public void test_hashCode() {
        EditQuestionDescriptor editedQuestionA = new EditQuestionDescriptorBuilder().build();
        EditQuestionDescriptor editedQuestionB = new EditQuestionDescriptorBuilder().withGroupName("G10").build();
        EditQnCommand editQnACommand = new EditQnCommand(INDEX_FIRST, editedQuestionA);
        EditQnCommand editQnBCommand = new EditQnCommand(INDEX_SECOND, editedQuestionB);

        // Same case
        assertEquals(editQnACommand.hashCode(), editQnACommand.hashCode());

        // Different case
        assertNotEquals(editQnACommand.hashCode(), editQnBCommand.hashCode());
    }

}
