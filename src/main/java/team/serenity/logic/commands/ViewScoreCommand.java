package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_LISTED_OVERVIEW;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import team.serenity.model.Model;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Display the participation score data of all students across all lessons in the group specified.
 * Keyword matching is case insensitive.
 */
public class ViewScoreCommand extends Command {

    public static final String COMMAND_WORD = "viewscore";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
        + ": View participation score sheet of all students in the specified group (case-insensitive) "
        + "and displays them as a table.\n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + " G04\n";

    private final GroupContainsKeywordPredicate predicate;

    public ViewScoreCommand(GroupContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    private String getMessage(Model model) {
        return model.getFilteredGroupList().isEmpty()
            ? MESSAGE_GROUP_EMPTY
            : String.format(MESSAGE_SCORE_LISTED_OVERVIEW, model.getFilteredGroupList().get(0).getGroupName());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(this.predicate);
        model.updateFilteredLessonList(Model.PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(this.getMessage(model), CommandResult.UiAction.VIEW_SCORE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewScoreCommand // instanceof handles nulls
            && this.predicate.equals(((ViewScoreCommand) other).predicate)); // state check
    }

}
