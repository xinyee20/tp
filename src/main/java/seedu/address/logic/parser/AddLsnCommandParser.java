package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LSN;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddLsnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GrpContainsKeywordPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_LSN);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_LSN) || !argMultimap.getPreamble()
                .isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLsnCommand.MESSAGE_USAGE));
        }

        String grpName = argMultimap.getValue(PREFIX_GRP).get();
        String lsnName = argMultimap.getValue(PREFIX_LSN).get();

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
