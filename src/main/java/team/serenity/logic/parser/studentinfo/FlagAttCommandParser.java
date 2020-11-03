package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.studentinfo.FlagAttCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.student.Student;

/**
 * Parses input arguments and creates a new FlagAttCommand object.
 * Current support:
 * flagatt name/NAME id/STUDENT_NUMBER
 * flagatt INDEX
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_MATRIC);

        Index index;
        String studentName;
        String studentNumber;
        Optional<Student> student;

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()
                && argMultimap.getPreamble().length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlagAttCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()) {
                studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
                studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_MATRIC).get());
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
}
