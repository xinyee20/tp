package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GrpContainsKeywordPredicate;

public class DelGrpCommand extends Command {

    public static final String COMMAND_WORD = "delgrp";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes an existing tutorial group. "
        + "Parameter: "
        + PREFIX_GRP + "GRP \n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_GRP + "G04";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Tutorial group deleted: %1$s";

    private final GrpContainsKeywordPredicate grpPredicate;
    
    /**
     * Creates a DelGrpCommand to add the specified {@code Group}
     */
    public DelGrpCommand(GrpContainsKeywordPredicate grpPredicate) {
        this.grpPredicate = grpPredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        
        Group toDel = new Group();
        
        boolean hasGroup = false;
        
        if (!model.getSerenity().getGroupList().isEmpty()) {
            for (Group group : model.getSerenity().getGroupList()) {
                if (group.getName().equals(grpPredicate.getKeyword())) {
                    toDel = group;
                    hasGroup = true;
                    break;
                }
            }
        }
        
        if (!hasGroup) {
            throw new CommandException(Messages.MESSAGE_GROUP_EMPTY);
        }
        
        model.deleteGroup(toDel);
        model.updateFilteredGroupList(grpPredicate);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, toDel));
    }
}
