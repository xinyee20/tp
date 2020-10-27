package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import java.util.stream.Stream;

import team.serenity.logic.commands.studentinfo.ExportAttCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new ExportAttCommand object.
 */
public class ExportAttCommandParser implements Parser<ExportAttCommand> {

    private final ParseException exportAttCommandParserException = new ParseException(
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttCommand.MESSAGE_USAGE));

    @Override
    public ExportAttCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP) || !argMultimap.getPreamble().isEmpty()) {
            throw this.exportAttCommandParserException;
        }

        String grpName = argMultimap.getValue(PREFIX_GRP).get();

        return new ExportAttCommand(new GroupContainsKeywordPredicate(grpName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
