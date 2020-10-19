package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAbsentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Student;

/**
 * Parses input arguments and creates a new MarkAbsentCommand object.
 * Current support:
 * markabsent name/NAME id/STUDENT_NUMBER
 */
public class MarkAbsentCommandParser implements Parser<MarkAbsentCommand> {

    public static final String MESSAGE_STUDENT_NOT_GIVEN = "Please ensure student name / id is given";

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAbsentAttCommand and
     * returns a MarkAbsentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAbsentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_STUDENT, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
        }

        String studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_STUDENT).get());
        String studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_ID).get());
        Optional<Student> student = Optional.ofNullable(new Student(studentName, studentNumber));

        return new MarkAbsentCommand(student.get());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
