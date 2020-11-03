package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import team.serenity.logic.commands.ViewScoreCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

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

        String[] grpKeyword = argMultimap.getValue(CliSyntax.PREFIX_GRP).get().split("\\s+");

        if (grpKeyword.length > 1) {
            throw this.viewScoreCommandParserException;
        }

        String groupName = SerenityParserUtil.parseGroupName(grpKeyword[0]).toString();

        return new ViewScoreCommand(new GroupContainsKeywordPredicate(groupName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
