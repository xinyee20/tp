package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import team.serenity.logic.commands.AddLsnCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GrpContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new AddGrpCommand object
 */
public class AddLsnCommandParser implements Parser<AddLsnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGrpCommand and returns an AddGrpCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLsnCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GRP, CliSyntax.PREFIX_LSN);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_GRP, CliSyntax.PREFIX_LSN) || !argMultimap.getPreamble()
                .isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLsnCommand.MESSAGE_USAGE));
        }

        String grpName = argMultimap.getValue(CliSyntax.PREFIX_GRP).get();
        String lsnName = argMultimap.getValue(CliSyntax.PREFIX_LSN).get();

        return new AddLsnCommand(lsnName, new GrpContainsKeywordPredicate(grpName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
