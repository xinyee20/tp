package team.serenity.logic.commands.student;

import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;

public class DelStudentCommand extends Command {

    public static final String COMMAND_WORD = "delstudent";
    public static final String MESSAGE_SUCCESS = "You removed %s (%s) from tutorial group %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Removes a new Student from a specified tutorial group. \n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_NAME + "STUDENT_NAME "
        + PREFIX_MATRIC + "STUDENT_NUMBER " + "or INDEX(starting from 1)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04 "
        + PREFIX_NAME + "Ryan "
        + PREFIX_MATRIC + "A1234567U\n"
        + "or " + COMMAND_WORD + " 2 "
        + PREFIX_GRP + "G04";

    private Optional<String> studentName;
    private Optional<String> studentId;
    private Optional<Student> toDelete;
    private Optional<Index> index;
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
        this.studentName = Optional.ofNullable(studentName);
        this.studentId = Optional.ofNullable(studentId);
        this.predicate = predicate;
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student} by index.
     * @param index Index of student in the list
     * @param predicate Group predicate
     */
    public DelStudentCommand(Index index, Predicate<Group> predicate) {
        requireAllNonNull(index, predicate);
        this.index = Optional.ofNullable(index);
        this.predicate = predicate;
        this.isByIndex = true;
        this.studentId = Optional.empty();
        this.studentName = Optional.empty();
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
            toDelete = Optional.ofNullable(new Student(this.studentName.get(), this.studentId.get()));
            if (!groups.get(0).getStudents().contains(toDelete.get())) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        } else {
            if (index.get().getZeroBased() >= uniqueStudentList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, index.get().getOneBased()));
            }

            toDelete = Optional.ofNullable(uniqueStudentList.getList().get(index.get().getZeroBased()));
            if (!uniqueStudentList.contains(toDelete.get())) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        }

        model.deleteStudentFromGroup(toDelete.get(), this.predicate);
        model.updateFilteredGroupList(this.predicate);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, toDelete.get().getStudentName().fullName,
                toDelete.get().getStudentNo().studentNumber,
                model.getFilteredGroupList().get(0).getGroupName()),
            CommandResult.UiAction.REFRESH_TABLE
        );
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
                && this.index.equals(other.index)
                && this.predicate.equals(other.predicate);
    }

}
