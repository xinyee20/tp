package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SET_SCORE;

import java.util.Optional;
import java.util.stream.Stream;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.studentinfo.SetScoreCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.student.Student;

/**
 * Parses input arguments and creates a new SetScoreCommand object.
 * Current support:
 * setscore name/NAME id/STUDENT_NUMBER score/SCORE
 * setscore INDEX score/SCORE
 */
public class SetScoreCommandParser implements Parser<SetScoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetScoreCommand and
     * returns a SetScoreCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetScoreCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_NAME, PREFIX_MATRIC, PREFIX_SET_SCORE);

        Index index;
        String studentName;
        String studentNumber;
        Optional<Student> student;
        int score;

        if (!arePrefixesPresent(argMultimap, PREFIX_SET_SCORE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && !argMultimap.getValue(PREFIX_MATRIC).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()
                && argMultimap.getPreamble().length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()) {
            studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
            studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_MATRIC).get());
            student = Optional.ofNullable(new Student(studentName, studentNumber));
            score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_SET_SCORE).get());
            return new SetScoreCommand(student.get(), score);
        } else {
            index = SerenityParserUtil.parseIndex(argMultimap.getPreamble());
            score = SerenityParserUtil.parseScore(argMultimap.getValue(PREFIX_SET_SCORE).get());
            return new SetScoreCommand(index, score);
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
