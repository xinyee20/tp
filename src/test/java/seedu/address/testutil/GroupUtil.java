package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;

import seedu.address.logic.commands.AddGrpCommand;
import seedu.address.model.group.Group;

/**
 * A utility class for Group.
 */
public class GroupUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String getAddGrpCommand(Group group) {
        return AddGrpCommand.COMMAND_WORD + " " + getGroupDetails(group);
    }

    /**
     * Returns the part of command string for the given {@code group}'s details.
     */
    public static String getGroupDetails(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_GRP + group.getName() + " ");
        // need more modifications
        return sb.toString();
    }

}
