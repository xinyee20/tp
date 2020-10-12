package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Question;
import seedu.address.model.group.UniqueQuestionList;

/**
 * Deletes a question identified using it's displayed index from the specified group's lesson in Serenity.
 */
public class DelQnCommand extends Command {

    public static final String COMMAND_WORD = "delqn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the question identified by the index number used in the displayed question list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_QUESTION_SUCCESS = "[%1$s %2$s] Deleted Question: %3$s";

    private final Index targetIndex;

    public DelQnCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredGroupList().size() != 1) {
            throw new CommandException(Messages.MESSAGE_NOT_VIEWING_A_GROUP);
        }

        if (model.getFilteredLessonList().size() != 1) {
            throw new CommandException(Messages.MESSAGE_NOT_VIEWING_A_LESSON);
        }

        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueQuestionList uniqueQuestionList = uniqueLesson.getQuestionList();
        ObservableList<Question> lastViewedQuestionList = uniqueQuestionList.asUnmodifiableObservableList();

        if (targetIndex.getZeroBased() >= lastViewedQuestionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToDelete = lastViewedQuestionList.get(targetIndex.getZeroBased());
        uniqueQuestionList.remove(questionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_QUESTION_SUCCESS,
                model.getFilteredGroupList().get(0),
                uniqueLesson, questionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DelQnCommand // instanceof handles nulls
                && targetIndex.equals(((DelQnCommand) other).targetIndex)); // state check
    }

}
