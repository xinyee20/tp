package team.serenity.logic.commands;

import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_GROUP_NAME_FORMAT;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_STUDENT_FORMAT;

import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.student.Student;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Checks if group name already exists in the group manager.
     * @param model
     * @throws CommandException
     */
    protected void checkIfGroupNameExists(Model model, Group toAdd) throws CommandException {
        if (model.hasGroupName(toAdd.getGroupName())) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_GROUP_NAME_FORMAT,
                toAdd.getGroupName().groupName));
        }
    }

    /**
     * Checks if students in the new group already exist in the student manager.
     *
     * @param model
     * @param toAdd
     * @throws CommandException
     */
    protected void checkIfStudentsExist(Model model, Group toAdd) throws CommandException {
        for (Student student : toAdd.getStudents()) {
            if (model.hasStudent(student)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT_FORMAT,
                    student.getStudentName(), student.getStudentNo()));
            }
        }
    }

    /**
     * Checks if tutorial group is empty.
     *
     * @param model
     * @throws CommandException
     */
    protected void checkIfGroupIsEmpty (Model model) throws CommandException {
        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_EMPTY);
        }
    }
}
