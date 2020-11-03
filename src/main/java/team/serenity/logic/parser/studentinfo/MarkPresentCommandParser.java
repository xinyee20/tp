package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.studentinfo.MarkPresentCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.student.Student;

/**
 * Parses input arguments and creates a new MarkPresentCommand object.
 * Current support:
 * markpresent name/NAME id/STUDENT_NUMBER
 * markpresent all
 * markpresent INDEX
 */
public class MarkPresentCommandParser implements Parser<MarkPresentCommand> {

    public static final String MESSAGE_STUDENT_NOT_GIVEN = "Please ensure student name / id is given";

    /**
     * Parses the given {@code String} of arguments in the context of the MarkPresentCommand and
     * returns a MarkPresentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkPresentCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_MATRIC);

        Index index;
        String studentName;
        String studentNumber;
        Optional<Student> student;
        Optional<String> keyToAll = Optional.ofNullable(argMultimap.getPreamble());

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()
                && argMultimap.getPreamble().length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_MATRIC).isPresent()) {

                // If single student specified, get student
                studentName = SerenityParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());
                studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_MATRIC).get());
                student = Optional.ofNullable(new Student(studentName, studentNumber));

                return new MarkPresentCommand(student.get());

            } else if (keyToAll.get().equals("all")) {

                // mark attendance of all students
                return new MarkPresentCommand();

            } else {
                index = SerenityParserUtil.parseIndex(keyToAll.get());
                return new MarkPresentCommand(index);
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
        }

    }

}
