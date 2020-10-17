package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.logic.commands.UnmarkAttCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Student;

/**
 * Parses input arguments and creates a new UnMarkAttCommand object.
 * Current support:
 * unmarkatt name/NAME id/STUDENT_NUMBER
 */
public class UnmarkAttCommandParser implements Parser<UnmarkAttCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAttCommand and
     * returns a UnmarkAttCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnmarkAttCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, CliSyntax.PREFIX_STUDENT, CliSyntax.PREFIX_ID);

        String studentName;
        String studentNumber;
        Optional<Student> student;

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_STUDENT, CliSyntax.PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttCommand.MESSAGE_USAGE));
        }

        studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(CliSyntax.PREFIX_STUDENT).get());
        studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(CliSyntax.PREFIX_ID).get());
        student = Optional.ofNullable(new Student(studentName, studentNumber));

        return new UnmarkAttCommand(student.get());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
