package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ADD_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SUBTRACT_SCORE;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.SetScoreCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Student;

/**
 * Parses input arguments and creates a new SetScoreCommand object.
 * Current support:
 * setscore name/NAME id/STUDENT_NUMBER add/SCORE_TO_ADD
 * setscore name/NAME id/STUDENT_NUMBER subtract/SCORE_TO_SUBTRACT
 * setscore INDEX add/SCORE_TO_ADD
 * setscore INDEX subtract/SCORE_TO_SUBTRACT
 */
public class SetScoreCommandParser implements Parser<SetScoreCommand> {

    public static final String MESSAGE_STUDENT_NOT_GIVEN = "Please ensure student name / id is given";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Score should be within range of 0 to 5";

    /**
     * Parses the given {@code String} of arguments in the context of the SetScoreCommand and
     * returns a SetScoreCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetScoreCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_NAME, PREFIX_ID, PREFIX_ADD_SCORE, PREFIX_SUBTRACT_SCORE);

        Index index;
        String studentName;
        String studentNumber;
        Optional<Student> student;
        int score;

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ID)
                && (!arePrefixesPresent(argMultimap, PREFIX_ADD_SCORE)
                && !arePrefixesPresent(argMultimap, PREFIX_SUBTRACT_SCORE)))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_ADD_SCORE).isPresent()
                && argMultimap.getValue(PREFIX_SUBTRACT_SCORE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_ID).isPresent()) {
            studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
            studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_ID).get());
            student = Optional.ofNullable(new Student(studentName, studentNumber));

            if (argMultimap.getValue(PREFIX_ADD_SCORE).isPresent()) {
                score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_ADD_SCORE).get());
                return new SetScoreCommand(student.get(), score, true);
            }

            score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_SUBTRACT_SCORE).get());
            return new SetScoreCommand(student.get(), score, false);
        } else {
            index = SerenityParserUtil.parseIndex(argMultimap.getPreamble());

            if (argMultimap.getValue(PREFIX_ADD_SCORE).isPresent()) {
                score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_ADD_SCORE).get());
                return new SetScoreCommand(index, score, true);
            }

            score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_SUBTRACT_SCORE).get());
            return new SetScoreCommand(index, score, false);
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
