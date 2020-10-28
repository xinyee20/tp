package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Exports the participation score sheet of a tutorial group as XLSX file.
 */
public class ExportScoreCommand extends Command {

    public static final String COMMAND_WORD = "exportscore";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports the participation score sheet of a specified tutorial group as a new XLSX file. "
        + "Parameters: "
        + PREFIX_GRP + "GROUP "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G04";

    public static final String MESSAGE_SUCCESS =
        "Participation score sheet of tutorial group %s has been exported as %s_participation.xlsx";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";

    private final GroupContainsKeywordPredicate grpPredicate;

    /**
     * Creates an ExportScoreCommand to add the specified {@code Group}.
     */
    public ExportScoreCommand(GroupContainsKeywordPredicate target) {
        this.grpPredicate = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredGroupList(this.grpPredicate);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_DOES_NOT_EXIST);
        }

        Group trgtGrp = model.getFilteredGroupList().get(0);

        model.exportParticipation(trgtGrp);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            trgtGrp.getGroupName().toString(), trgtGrp.getGroupName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportScoreCommand // instanceof handles nulls
            && this.grpPredicate.equals(((ExportScoreCommand) other).grpPredicate));
    }

}
