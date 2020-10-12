package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QN;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGrpCommand;
import seedu.address.logic.commands.AddQnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Question;

/**
 * Parses input arguments and creates a new AddQnCommand object
 */
public class AddQnCommandParser implements Parser<AddQnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddQnCommand and
     * returns an AddQnCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddQnCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QN);

        if (!arePrefixesPresent(argMultimap, PREFIX_QN) || !argMultimap.getPreamble()
                .isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE));
        }

        String question = argMultimap.getValue(PREFIX_QN).get();

        Question newQuestion = new Question(question);

        return new AddQnCommand(newQuestion);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
