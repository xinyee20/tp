package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAttCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttCommand object.
 * Current support:
 * markatt Name grp/GROUP lsn/LESSON
 * markatt all grp/GROUP lsn/LESSON
 *
 */
public class MarkAttCommandParser implements Parser<MarkAttCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttCommand and returns a MarkAttCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAttCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            // Check whether keyword 'all is given', if yes, get all students, else throw error
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttCommand.MESSAGE_USAGE));
        }

        if () {// If single student specified, get student
            String studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_NAME).get());
            return new MarkAttCommand(studentName);
        }
        // else mark attendance of all students
        return new MarkAttCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
