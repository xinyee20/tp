package team.serenity.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_LESSON_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_LESSON_LISTED_OVERVIEW;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;

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
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_LSN + "LESSON\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04 "
        + PREFIX_LSN + "2-2\n";

    public static final String GROUP_DOES_NOT_EXIST_MESSAGE = "The specified group does not exist!";
    public static final String LESSON_DOES_NOT_EXIST_MESSAGE = "The specified lesson does not exist!";

    private final GroupContainsKeywordPredicate grpPredicate;
    private final LessonContainsKeywordPredicate lsnPredicate;

    /**
     * Creates a ViewLsnCommand to view the specified {@code Lesson}
     */
    public ViewLsnCommand(GroupContainsKeywordPredicate grpPredicate,
                          LessonContainsKeywordPredicate lsnPredicate) {
        this.grpPredicate = grpPredicate;
        this.lsnPredicate = lsnPredicate;
    }

    private String getMessage(Model model) {
        return model.getFilteredGroupList().isEmpty()
                ? MESSAGE_LESSON_EMPTY
                : String.format(MESSAGE_LESSON_LISTED_OVERVIEW,
                model.getFilteredGroupList().get(0).getGroupName(),
                model.getFilteredLessonList().get(0).getLessonName());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(this.grpPredicate);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(GROUP_DOES_NOT_EXIST_MESSAGE);
        }

        model.updateFilteredLessonList(this.lsnPredicate);

        if (model.getFilteredLessonList().isEmpty()) {
            throw new CommandException(LESSON_DOES_NOT_EXIST_MESSAGE);
        }

        return new CommandResult(this.getMessage(model), CommandResult.UiAction.VIEW_LSN
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewLsnCommand // instanceof handles nulls
                && this.grpPredicate.equals(((ViewLsnCommand) other).grpPredicate)) // state check
                && this.lsnPredicate.equals(((ViewLsnCommand) other).lsnPredicate);
    }

}
