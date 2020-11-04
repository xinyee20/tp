package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;

import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.student.Student;

public class AddGrpCommand extends Command {

    public static final String COMMAND_WORD = "addgrp";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a new tutorial group. "
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + PREFIX_PATH + "PATH\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04 "
        + PREFIX_PATH + "CS2101_G04.xlsx\n";

    public static final String MESSAGE_SUCCESS = "New tutorial group added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT_FORMAT =
            "This student %s [%s] already exists in another group.\n"
            + "Please try again after removing him/her from the .xlsx file.";
    public static final String MESSAGE_DUPLICATE_GROUP_NAME_FORMAT = "This tutorial group name \"%s\" already exists.\n"
            + "Please try again with another group name.";

    private final Group toAdd;

    /**
     * Creates an AddGrpCommand to add the specified {@code Group}.
     */
    public AddGrpCommand(Group group) {
        requireNonNull(group);
        this.toAdd = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if group name already exists in the group manager
        if (model.hasGroupName(this.toAdd.getGroupName())) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_GROUP_NAME_FORMAT,
                    this.toAdd.getGroupName().groupName));
        }

        // Check if students in the new group already exist in the student manager
        for (Student student : this.toAdd.getStudents()) {
            if (model.hasStudent(student)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT_FORMAT,
                        student.getStudentName(), student.getStudentNo()));
            }
        }

        model.addGroup(this.toAdd);
        model.updateFilteredGroupList(new GroupContainsKeywordPredicate(this.toAdd.getGroupName().toString()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toAdd), CommandResult.UiAction.ADD_GRP);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddGrpCommand // instanceof handles nulls
            && this.toAdd.equals(((AddGrpCommand) other).toAdd));
    }

}
