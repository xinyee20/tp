package team.serenity.logic.commands.question;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_QUESTIONS_LISTED_OVERVIEW;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;

/**
 * Finds and lists all questions that contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindQnCommand extends Command {

    public static final String COMMAND_WORD = "findqn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " deadline criteria";

    private final QuestionContainsKeywordPredicate predicate;

    public FindQnCommand(QuestionContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredQuestionList(this.predicate);
        return new CommandResult(String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW,
            model.getFilteredQuestionList().size(), "questions")
            + String.format("\nUse the \"%s\" command to show all questions", ViewQnCommand.COMMAND_WORD),
            CommandResult.UiAction.VIEW_QN
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindQnCommand // instanceof handles nulls
            && this.predicate.equals(((FindQnCommand) other).predicate)); // state check
    }

}

