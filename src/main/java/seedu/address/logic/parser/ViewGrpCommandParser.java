package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;


import java.util.Arrays;
import java.util.stream.Stream;


import seedu.address.logic.commands.AddGrpCommand;
import seedu.address.logic.commands.ViewGrpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GrpContainsKeywordPredicate;

public class ViewGrpCommandParser implements Parser<ViewGrpCommand> {

    private final ParseException ViewGrpCommandParserException = new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGrpCommand.MESSAGE_USAGE));

    @Override
    public ViewGrpCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP) || !argMultimap.getPreamble()
                .isEmpty()) {
            throw ViewGrpCommandParserException;
        }

        String[] grpKeyword = argMultimap.getValue(PREFIX_GRP).get().split("\\s+");

        if (grpKeyword.length > 1) {
            throw ViewGrpCommandParserException;
        }

        return new ViewGrpCommand(new GrpContainsKeywordPredicate(grpKeyword[0]));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
