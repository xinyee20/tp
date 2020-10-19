package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;

import java.util.stream.Stream;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.commands.AddQnCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Question;

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

        if (!arePrefixesPresent(argMultimap, PREFIX_QN) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE));
        }

        Question question = SerenityParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QN).get());

        return new AddQnCommand(question);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
