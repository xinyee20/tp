package team.serenity.logic.commands;

import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.util.UniqueList;

public class DelStudentCommand extends Command {

    public static final String COMMAND_WORD = "delstudent";
    public static final String MESSAGE_SUCCESS = "You removed %s (%s) from tutorial group %s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "Index %d is not found, please ensure that it exists";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Removes a new Student from a specified tutorial group. \n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_NAME + "STUDENT_NAME "
        + PREFIX_ID + "STUDENT_NUMBER " + "or INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04 "
        + PREFIX_NAME + "Ryan "
        + PREFIX_ID + "e1234567\n"
        + "or " + COMMAND_WORD + " 2 "
            + PREFIX_GRP + "G04";

    private String studentName;
    private String studentId;
    private Student toDelete;
    private Index index;
    private boolean isByIndex;
    private final Predicate<Group> predicate;

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student}.
     * @param studentName Name of Student
     * @param studentId Id of Student
     * @param predicate Group predicate
     */
    public DelStudentCommand(String studentName, String studentId, Predicate<Group> predicate) {
        requireAllNonNull(studentName, studentId, predicate);
        this.studentName = studentName;
        this.studentId = studentId;
        this.predicate = predicate;
        this.isByIndex = false;
    }

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student} by index.
     * @param index Index of student in the list
     * @param predicate Group predicate
     */
    public DelStudentCommand(Index index, Predicate<Group> predicate) {
        requireAllNonNull(index, predicate);
        this.index = index;
        this.predicate = predicate;
        this.isByIndex = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredGroupList(this.predicate);
        ObservableList<Group> groups = model.getFilteredGroupList();

        if (groups.isEmpty()) {
            //no such group
            throw new CommandException(MESSAGE_GROUP_EMPTY);
        }

        UniqueList<Student> uniqueStudentList = groups.get(0).getStudents();

        if (!isByIndex) {
            toDelete = new Student(this.studentName, this.studentId);
            if (!groups.get(0).getStudents().contains(toDelete)) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        } else {
            if (index.getZeroBased() > uniqueStudentList.size()) {
                throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, index.getOneBased()));
            }

            toDelete = uniqueStudentList.getList().get(index.getZeroBased());
            if (!uniqueStudentList.contains(toDelete)) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        }

        model.deleteStudentFromGroup(toDelete, this.predicate);
        model.updateFilteredGroupList(this.predicate);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, toDelete.getName(), toDelete.getStudentId(),
            model.getFilteredGroupList().get(0).getName()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DelStudentCommand)) {
            return false;
        }

        DelStudentCommand other = (DelStudentCommand) obj;
        return this.studentName.equals(other.studentName)
                && this.studentId.equals(other.studentId)
                && this.predicate.equals(other.predicate);
    }

}
