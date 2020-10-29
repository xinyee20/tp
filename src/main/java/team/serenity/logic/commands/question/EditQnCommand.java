package team.serenity.logic.commands.question;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import team.serenity.commons.core.index.Index;
import team.serenity.commons.util.CollectionUtil;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;

/**
 * Edits the description of an existing question.
 */
public class EditQnCommand extends Command {

    public static final String COMMAND_WORD = "editqn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the question identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GRP + "GROUP_NAME] "
            + "[" + PREFIX_LSN + "LESSON_NAME] "
            + "[" + PREFIX_QN + "QUESTION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QN + "Can you repeat the deadlines for all submissions?";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Edited Question: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Question is not edited.\n"
        + "At least one field needs to be change from the original question.";
    public static final String MESSAGE_GROUP_NOT_FOUND_FORMAT = "This group \"%s\" does not exists.";
    public static final String MESSAGE_LESSON_NOT_FOUND_FORMAT = "This lesson \"%s\" does not exists.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists in the list.";


    private final Index index;
    private final EditQuestionDescriptor editQuestionDescriptor;

    /**
     * Creates an EditQnCommand to edit the specified question at {@code index} in the current question display list.
     *
     * @param index of the question in the filtered question list to edit
     * @param editQuestionDescriptor details to edit the question with
     */
    public EditQnCommand(Index index, EditQuestionDescriptor editQuestionDescriptor) {
        requireAllNonNull(index, editQuestionDescriptor);
        this.index = index;
        this.editQuestionDescriptor = new EditQuestionDescriptor(editQuestionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Question> lastShownList = model.getFilteredQuestionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToEdit = lastShownList.get(index.getZeroBased());
        Question editedQuestion = createEditedQuestion(questionToEdit, editQuestionDescriptor);

        // Checks if the edited question is the same as question to edit
        if (questionToEdit.equals(editedQuestion)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        // Checks if the edited question exists in the question manager
        if (model.hasQuestion(editedQuestion)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        // Checks if the edited question's group name exists in serenity
        if (!questionToEdit.getGroupName().equals(editedQuestion.getGroupName())
            && !model.hasGroupName(editedQuestion.getGroupName())) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND_FORMAT,
                editedQuestion.getGroupName().groupName));
        }

        // Checks if the edited question's lesson name exists in the group and serenity
        if (!questionToEdit.getLessonName().equals(editedQuestion.getLessonName())
            && !model.ifTargetGroupHasLessonName(editedQuestion.getGroupName(), editedQuestion.getLessonName())) {
            throw new CommandException(String.format(MESSAGE_LESSON_NOT_FOUND_FORMAT,
                    editedQuestion.getLessonName().lessonName));
        }

        model.setQuestion(questionToEdit, editedQuestion);
        model.updateFilteredQuestionList(Model.PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion),
                CommandResult.UiAction.VIEW_QN
        );
    }

    /**
     * Creates and returns a {@code Question} with the details of {@code questionToEdit}
     * edited with {@code editQuestionDescriptor}
     */
    private static Question createEditedQuestion(Question questionToEdit,
                                                 EditQuestionDescriptor editQuestionDescriptor) {
        assert questionToEdit != null; // the question to edit is taken from the filtered question list

        GroupName updatedGroupName = editQuestionDescriptor.getGroupName().orElse(questionToEdit.getGroupName());
        LessonName updatedLessonName = editQuestionDescriptor.getLessonName().orElse(questionToEdit.getLessonName());
        Description updatedDescription = editQuestionDescriptor.getDescription()
                .orElse(questionToEdit.getDescription());

        return new Question(updatedGroupName, updatedLessonName, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EditQnCommand // instanceof handles nulls
            && this.index.equals(((EditQnCommand) other).index)
            && this.editQuestionDescriptor.equals(((EditQnCommand) other).editQuestionDescriptor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.index.hashCode(), this.editQuestionDescriptor.hashCode());
    }

    /**
     * Stores the details to edit the question with. Each non-empty field value will replace the
     * corresponding field value of the question.
     */
    public static class EditQuestionDescriptor {
        private GroupName groupName;
        private LessonName lessonName;
        private Description description;

        public EditQuestionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditQuestionDescriptor(EditQuestionDescriptor toCopy) {
            setGroupName(toCopy.groupName);
            setLessonName(toCopy.lessonName);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.groupName, this.lessonName, this.description);
        }

        public void setGroupName(GroupName groupName) {
            this.groupName = groupName;
        }

        public Optional<GroupName> getGroupName() {
            return Optional.ofNullable(this.groupName);
        }

        public void setLessonName(LessonName lessonName) {
            this.lessonName = lessonName;
        }

        public Optional<LessonName> getLessonName() {
            return Optional.ofNullable(this.lessonName);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(this.description);
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof EditQuestionDescriptor // instanceof handles nulls
                && this.getGroupName().equals(((EditQuestionDescriptor) other).getGroupName())
                && this.getLessonName().equals(((EditQuestionDescriptor) other).getLessonName())
                && this.getDescription().equals(((EditQuestionDescriptor) other).getDescription()));
        }
    }

}
