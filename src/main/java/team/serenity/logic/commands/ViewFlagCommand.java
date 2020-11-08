package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import team.serenity.model.Model;

/**
 * Display the students with flagged attendance across all lessons in the group specified.
 */
public class ViewFlagCommand extends Command {

    public static final String COMMAND_WORD = "viewflag";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
        + ": Displays all students with flagged attendance as a list with index numbers.\n"
        + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "These are the flagged attendance records.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.UiAction.FLAG_ATT);
    }

}
