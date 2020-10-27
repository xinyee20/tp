package team.serenity.testutil;

import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.model.group.Group;

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
        sb.append(PREFIX_GRP + group.getGroupName().toString() + " ");
        // need more modifications
        return sb.toString();
    }

}
