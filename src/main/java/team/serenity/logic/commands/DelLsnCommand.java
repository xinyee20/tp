package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;

import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class DelLsnCommand extends Command {
    public static final String COMMAND_WORD = "dellsn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes a specified lesson from a specified tutorial group. "
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_LSN + "PATH\n";

    public static final String MESSAGE_SUCCESS = "lesson for tutorial group %2$s deleted: %1$s";
    public static final String MESSAGE_LESSON_NOT_FOUND = "This lesson for tutorial group %1$s does not exists.";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";

    private final GroupContainsKeywordPredicate targetGrpPredicate;
    private final String toDel;

    /**
     * Creates a DelLsnCommand to delete the specified lesson.
     */
    public DelLsnCommand(String lesson, GroupContainsKeywordPredicate target) {
        requireNonNull(lesson);
        this.targetGrpPredicate = target;
        this.toDel = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(this.targetGrpPredicate);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_DOES_NOT_EXIST);
        }

        Group trgtGrp = model.getFilteredGroupList().get(0);
        UniqueList<Student> students = trgtGrp.getStudents();
        UniqueList<StudentInfo> studentInfos = new UniqueStudentInfoList();
        for (Student student : students) {
            studentInfos.add(new StudentInfo(student));
        }
        Lesson toDel = new Lesson(this.toDel, studentInfos);

        if (!trgtGrp.getLessons().contains(toDel)) {
            throw new CommandException(String.format(MESSAGE_LESSON_NOT_FOUND, this.toDel, this.targetGrpPredicate));
        }

        trgtGrp.getLessons().remove(toDel);

        model.updateLessonList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toDel, this.targetGrpPredicate),
            false, false, false, true, false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DelLsnCommand // instanceof handles nulls
                && this.toDel.equals(((DelLsnCommand) other).toDel));
    }
}
