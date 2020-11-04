package team.serenity.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;
import static team.serenity.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonName;

public class DelLsnCommand extends Command {
    public static final String COMMAND_WORD = "dellsn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes a specified lesson from a specified tutorial group. "
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_LSN + "LESSON\n"
        + "Example: "
        + PREFIX_GRP + "G04 "
        + PREFIX_LSN + "1-1";

    public static final String MESSAGE_SUCCESS = "Lesson %1$s for tutorial group %2$s is deleted.";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Group %1$s does not exist.";
    public static final String MESSAGE_LESSON_DOES_NOT_EXIST = "Lesson %1$s does not exist.";

    private final GroupName targetGroupName;
    private final LessonName toDel;

    /**
     * Creates a DelLsnCommand to delete the specified lesson.
     */
    public DelLsnCommand(GroupName groupName, LessonName lessonName) {
        requireNonNull(lessonName);
        this.targetGroupName = groupName;
        this.toDel = lessonName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Checks if group exists in serenity
        if (!model.hasGroupName(this.targetGroupName)) {
            throw new CommandException(String.format(MESSAGE_GROUP_DOES_NOT_EXIST, this.targetGroupName));
        }

        // Checks if lesson exists in target group
        if (!model.ifTargetGroupHasLessonName(this.targetGroupName, this.toDel)) {
            throw new CommandException(String.format(MESSAGE_LESSON_DOES_NOT_EXIST, this.toDel));
        }

        // Update filtered group list and filtered lesson list to get Group and Lesson
        model.updateFilteredGroupList(new GroupContainsKeywordPredicate(this.targetGroupName.groupName));
        model.updateFilteredLessonList(new LessonContainsKeywordPredicate(this.toDel.lessonName));
        Group trgtGrp = model.getFilteredGroupList().get(0);
        Lesson lessonToDel = model.getFilteredLessonList().get(0);

        model.deleteLesson(trgtGrp, lessonToDel);

        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toDel, this.targetGroupName),
                CommandResult.UiAction.VIEW_GRP
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DelLsnCommand // instanceof handles nulls
                && this.targetGroupName.equals(((DelLsnCommand) other).targetGroupName)
                && this.toDel.equals(((DelLsnCommand) other).toDel));
    }
}
