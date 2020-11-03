package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new ViewGrpCommand object.
 */
public class ViewGrpCommandParser implements Parser<ViewGrpCommand> {

    private final ParseException viewGrpCommandParserException = new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGrpCommand.MESSAGE_USAGE));

    @Override
    public ViewGrpCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GRP);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_GRP) || !argMultimap.getPreamble().isEmpty()) {
            throw this.viewGrpCommandParserException;
        }

        String[] grpKeyword = argMultimap.getValue(CliSyntax.PREFIX_GRP).get().split("\\s+");

        if (grpKeyword.length > 1) {
            throw this.viewGrpCommandParserException;
        }

        String groupName = SerenityParserUtil.parseGroupName(grpKeyword[0]).toString();

        return new ViewGrpCommand(new GroupContainsKeywordPredicate(groupName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
