package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_LISTED_OVERVIEW;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import team.serenity.model.Model;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Finds and lists all students and lessons in the group specified. Keyword matching is case insensitive.
 */
public class ViewGrpCommand extends Command {

    public static final String COMMAND_WORD = "viewgrp";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
        + ": Displays the details of the specified tutorial group (case-insensitive) which includes "
        + "the lists of lessons and students, and the sheets of attendance and participation scores.\n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G01\n";

    private final GroupContainsKeywordPredicate predicate;

    public ViewGrpCommand(GroupContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    private String getMessage(Model model) {
        return model.getFilteredGroupList().isEmpty()
                ? MESSAGE_GROUP_EMPTY
                : String.format(MESSAGE_GROUP_LISTED_OVERVIEW, model.getFilteredGroupList().get(0).getGroupName());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(this.predicate);
        model.updateFilteredLessonList(Model.PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(this.getMessage(model), CommandResult.UiAction.VIEW_GRP);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewGrpCommand // instanceof handles nulls
                && this.predicate.equals(((ViewGrpCommand) other).predicate)); // state check
    }

}
