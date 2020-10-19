package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.logic.commands.AddScoreCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Student;

public class AddScoreCommandParser implements Parser<AddScoreCommand> {

    public static final String MESSAGE_STUDENT_NOT_GIVEN = "Please ensure student name / id is given";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Score should be within range of 0 to 5";

    /**
     * Parses the given {@code String} of arguments in the context of the AddScoreCommand and
     * returns a AddScoreCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddScoreCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(userInput, PREFIX_NAME, PREFIX_ID, PREFIX_SCORE);

        String studentName;
        String studentNumber;
        Optional<Student> student;
        int score;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ID, PREFIX_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE));
        }

        studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_NAME).get());
        studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_ID).get());
        student = Optional.ofNullable(new Student(studentName, studentNumber));
        score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE).get());
        if (score < 0 || score > 5) {
            throw new ParseException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
        }

        return new AddScoreCommand(student.get(), score);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
