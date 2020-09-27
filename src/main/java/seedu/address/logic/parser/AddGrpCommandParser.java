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

public class AddGrpCommandParser implements Parser<AddGrpCommand> {

    public AddGrpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_PATH)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_GRP).get();

        String filePath = argMultimap.getValue(PREFIX_PATH).get();
        Path path = Paths.get(filePath);
        CSVUtil csvutil = new CSVUtil(path);
        Set<Student> students = csvutil.readStudentsFromCsv();

        Group group = new Group(name, students);

        return new AddGrpCommand(group);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
