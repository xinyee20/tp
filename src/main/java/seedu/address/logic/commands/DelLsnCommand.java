package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LSN;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordPredicate;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueStudentInfoList;
import seedu.address.model.util.UniqueList;

public class DelLsnCommand extends Command {
    public static final String COMMAND_WORD = "dellsn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": deletes a specified lesson from a specified tutorial group. "
            + "Parameters: "
            + PREFIX_GRP + "GRP "
            + PREFIX_LSN + "PATH ";

    public static final String MESSAGE_SUCCESS = "lesson for tutorial group %2$s deleted: %1$s";
    public static final String MESSAGE_LESSON_NOT_FOUND = "This lesson for tutorial group %1$s does not exists.";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";
    private final GroupContainsKeywordPredicate trgtGrp;
    private final String toDel;

    /**
     * Creates an AddGrpCommand to add the specified {@code Group}
     */
    public DelLsnCommand(String lesson, GroupContainsKeywordPredicate target) {
        requireNonNull(lesson);
        trgtGrp = target;
        toDel = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(trgtGrp);

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
            throw new CommandException(String.format(MESSAGE_LESSON_NOT_FOUND, toDel, trgtGrp));
        }

        trgtGrp.getLessons().remove(toDel);

        model.updateLessonList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDel, trgtGrp), false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLsnCommand // instanceof handles nulls
                && toDel.equals(((DelLsnCommand) other).toDel));
    }
}
