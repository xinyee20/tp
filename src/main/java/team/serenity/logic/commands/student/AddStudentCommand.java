package team.serenity.logic.commands.student;

import static team.serenity.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.student.Student;

public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a new Student to a specified tutorial group. \n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_NAME + "STUDENT_NAME "
        + PREFIX_MATRIC + "STUDENT_NUMBER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04 "
        + PREFIX_NAME + "Ryan "
        + PREFIX_MATRIC + "A0123456U\n";

    public static final String MESSAGE_SUCCESS = "You added %s (%s) to tutorial group %s.";

    private final String studentName;
    private final String studentId;
    private final Predicate<Group> predicate;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}.
     *
     * @param studentName
     * @param studentId
     * @param predicate
     */
    public AddStudentCommand(String studentName, String studentId, Predicate<Group> predicate) {
        requireAllNonNull(studentName, studentId, predicate);
        this.studentName = studentName;
        this.studentId = studentId;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Student student = new Student(studentName, studentId);
            model.updateFilteredGroupList(predicate);

            if (model.getFilteredGroupList().isEmpty()) {
                //no such group exists
                throw new CommandException(MESSAGE_GROUP_EMPTY);
            }

            Group targetGroup = model.getFilteredGroupList().get(0);
            if (targetGroup.getStudents().contains(student)) {
                throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
            }

            model.addStudentToGroup(student, predicate);
            return new CommandResult(
                String.format(MESSAGE_SUCCESS, studentName, studentId,
                    model.getFilteredGroupList().get(0).getGroupName()),
                CommandResult.UiAction.REFRESH_TABLE
            );
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand other = (AddStudentCommand) obj;
        return this.studentName.equals(other.studentName)
            && this.studentId.equals(other.studentId)
            && this.predicate.equals(other.predicate);
    }

}
