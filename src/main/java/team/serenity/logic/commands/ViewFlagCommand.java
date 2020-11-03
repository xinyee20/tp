package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;

import team.serenity.model.Model;

/**
 * Display the students with flagged attendance across all lessons in the group specified.
 * Keyword matching is case insensitive.
 */
public class ViewFlagCommand extends Command {

    public static final String COMMAND_WORD = "viewflag";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
        + ": View all students with flagged attendance "
        + "and display them as a list.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SUCCESS_MESSAGE = "These are the flagged attendance records.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(SUCCESS_MESSAGE, CommandResult.UiAction.FLAG_ATT);
    }

}
