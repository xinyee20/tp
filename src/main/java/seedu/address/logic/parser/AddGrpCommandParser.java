package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddGrpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;

/**
 * Parses input arguments and creates a new AddGrpCommand object
 */
public class AddGrpCommandParser implements Parser<AddGrpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGrpCommand
     * and returns an AddGrpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGrpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_PATH) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_GRP).get();

        String filePath = argMultimap.getValue(PREFIX_PATH).get();
        Path path = Paths.get(filePath);
        CsvUtil csvUtil = new CsvUtil(path);
        Set<Student> students = csvUtil.readStudentsFromCsv();

        Group group = new Group(name, students);

        return new AddGrpCommand(group);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
