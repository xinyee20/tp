package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;

import java.util.stream.Stream;

import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;

/**
 * Parses input arguments and creates a new AddGrpCommand object.
 */
public class AddGrpCommandParser implements Parser<AddGrpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGrpCommand and
     * returns an AddGrpCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGrpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_PATH) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE));
        }

        GroupName groupName = SerenityParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GRP).get());
        XlsxUtil grpExcelData = SerenityParserUtil.parseFilePath(argMultimap.getValue(PREFIX_PATH).get());

        Group group = new Group(groupName, grpExcelData);
        return new AddGrpCommand(group);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
