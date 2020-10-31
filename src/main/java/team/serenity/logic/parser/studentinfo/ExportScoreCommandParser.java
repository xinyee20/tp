package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import java.util.stream.Stream;

import team.serenity.logic.commands.studentinfo.ExportScoreCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new ExportScoreCommand object.
 */
public class ExportScoreCommandParser implements Parser<ExportScoreCommand> {

    private final ParseException exportScoreCommandParserException = new ParseException(
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportScoreCommand.MESSAGE_USAGE));

    @Override
    public ExportScoreCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP) || !argMultimap.getPreamble().isEmpty()) {
            throw this.exportScoreCommandParserException;
        }

        String grpName = argMultimap.getValue(PREFIX_GRP).get();

        return new ExportScoreCommand(new GroupContainsKeywordPredicate(grpName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
