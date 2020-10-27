package team.serenity.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class AddLsnCommand extends Command {

    public static final String COMMAND_WORD = "addlsn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a new lesson to a specified tutorial group. "
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_PATH + "PATH ";

    public static final String MESSAGE_SUCCESS = "New lesson for tutorial group %2$s added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson for tutorial group %1$s already exists.";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";

    private final GroupContainsKeywordPredicate targetGrpPredicate;
    private final String toAdd;

    /**
     * Creates an AddLsnCommand to add the specified lesson.
     */
    public AddLsnCommand(String lesson, GroupContainsKeywordPredicate target) {
        requireNonNull(lesson);
        this.targetGrpPredicate = target;
        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(this.targetGrpPredicate);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_DOES_NOT_EXIST);
        }

        Group targetGrp = model.getFilteredGroupList().get(0);
        UniqueList<Student> students = targetGrp.getStudents();
        UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        Lesson toAdd = new Lesson(this.toAdd, studentsInfo);

        if (targetGrp.getLessons().contains(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_LESSON, toAdd, targetGrp));
        }

        targetGrp.getLessons().add(toAdd);

        model.updateLessonList();
        model.updateFilteredLessonList(new LessonContainsKeywordPredicate(this.toAdd));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, targetGrp),
            false, false, true, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLsnCommand // instanceof handles nulls
                && this.toAdd.equals(((AddLsnCommand) other).toAdd));
    }

}
