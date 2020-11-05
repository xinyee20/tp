package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import java.util.stream.Stream;

import team.serenity.logic.commands.ViewAttCommand;
import team.serenity.logic.commands.ViewScoreCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;

/**
 * Parses input arguments and creates a new ViewScoreCommand object.
 */
public class ViewScoreCommandParser implements Parser<ViewScoreCommand> {

    private final ParseException viewScoreCommandParserException = new ParseException(
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewScoreCommand.MESSAGE_USAGE));

    @Override
    public ViewScoreCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GRP);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_GRP) || !argMultimap.getPreamble().isEmpty()) {
            throw this.viewScoreCommandParserException;
        }

        GroupName groupName = SerenityParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GRP).get());

        return new ViewScoreCommand(new GroupContainsKeywordPredicate(groupName.toString().toUpperCase()));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
