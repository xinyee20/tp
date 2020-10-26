package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.logic.commands.studentinfo.MarkAbsentCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.student.Student;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
        }

        String studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
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
