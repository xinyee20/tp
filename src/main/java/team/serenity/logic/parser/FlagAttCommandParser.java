package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.FlagAttCommand;
import team.serenity.logic.commands.MarkPresentCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Student;

/**
 * Parses input arguments and creates a new FlagAttCommand object.
 * Current support:
 * flagatt name/NAME id/STUDENT_NUMBER
 */
public class FlagAttCommandParser implements Parser<FlagAttCommand> {

    public static final String MESSAGE_STUDENT_NOT_GIVEN = "Please ensure student name / id is given";

    /**
     * Parses the given {@code String} of arguments in the context of the FlagAttCommand and
     * returns a FlagAttCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FlagAttCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ID);

        Index index;
        String studentName;
        String studentNumber;
        Optional<Student> student;

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_ID).isPresent()) {
                studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
                studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_ID).get());
                student = Optional.ofNullable(new Student(studentName, studentNumber));

                return new FlagAttCommand(student.get());

            } else {
                index = SerenityParserUtil.parseIndex(argMultimap.getPreamble());
                return new FlagAttCommand(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlagAttCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
