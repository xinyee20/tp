package team.serenity.logic.commands.question;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.question.Question;

/**
 * Deletes a question identified using it's displayed index from viewing the question list.
 */
public class DelQnCommand extends Command {

    public static final String COMMAND_WORD = "delqn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the question identified by the index number used in the displayed question list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1\n";

    public static final String MESSAGE_DELETE_QUESTION_SUCCESS = "Deleted Question: %s";

    private final Index targetIndex;

    /**
     * Creates a DelQnCommand to delete the specified question.
     */
    public DelQnCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Question> lastViewedQuestionList = model.getFilteredQuestionList();

        if (this.targetIndex.getZeroBased() >= lastViewedQuestionList.size()) {
            throw new CommandException(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToDelete = lastViewedQuestionList.get(this.targetIndex.getZeroBased());
        model.deleteQuestion(questionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_QUESTION_SUCCESS, questionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DelQnCommand // instanceof handles nulls
                && this.targetIndex.equals(((DelQnCommand) other).targetIndex)); // state check
    }

}
