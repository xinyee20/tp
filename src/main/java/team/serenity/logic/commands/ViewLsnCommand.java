package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;

import team.serenity.commons.core.Messages;
import team.serenity.model.Model;
import team.serenity.model.group.GrpContainsKeywordPredicate;
import team.serenity.model.group.LsnContainsKeywordPredicate;

/**
 * Finds and lists the attendance and class participation of all the students from
 * a specified group and lesson. Keyword matching is case insensitive.
 */
public class ViewLsnCommand extends Command {

    public static final String COMMAND_WORD = "viewlsn";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the attendance and class participation of all students "
            + "from the specified lesson of a specific group (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: GROUP LESSON\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GRP + " G04 " + PREFIX_LSN + " 2-2";

    private final GrpContainsKeywordPredicate grpPredicate;
    private final LsnContainsKeywordPredicate lsnPredicate;

    /**
     * Creates a ViewLsnCommand to view the specified {@code Lesson}
     */
    public ViewLsnCommand(GrpContainsKeywordPredicate grpPredicate,
                          LsnContainsKeywordPredicate lsnPredicate) {
        this.grpPredicate = grpPredicate;
        this.lsnPredicate = lsnPredicate;
    }

    private String getMessage(Model model) {
        return model.getFilteredGroupList().isEmpty()
                ? Messages.MESSAGE_LESSON_EMPTY
                : String.format(Messages.MESSAGE_LESSON_LISTED_OVERVIEW,
                model.getFilteredGroupList().get(0).getName(),
                model.getFilteredLessonList().get(0).getName());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(grpPredicate);
        model.updateFilteredLessonList(lsnPredicate);
        return new CommandResult(this.getMessage(model), false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewLsnCommand // instanceof handles nulls
                && grpPredicate.equals(((ViewLsnCommand) other).grpPredicate)) // state check
                && lsnPredicate.equals(((ViewLsnCommand) other).lsnPredicate);
    }

}
