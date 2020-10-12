package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.Optional;

import seedu.address.logic.commands.AddScoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Student;

public class AddScoreCommandParser implements Parser<AddScoreCommand> {

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
                        .tokenize(userInput, PREFIX_STUDENT, PREFIX_ID);

        String studentName;
        String studentNumber;
        Optional<Student> student;
        int score;

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent() && argMultimap.getValue(PREFIX_ID).isPresent()) {

            score = SerenityParserUtil.parseScore(argMultimap.getPreamble());
            if (score < 0 || score > 5) {
                throw new ParseException("Score should be between 0 to 5");
            }
            studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_STUDENT).get());
            studentNumber = SerenityParserUtil.parseStudentID(argMultimap.getValue(PREFIX_ID).get());
            student = Optional.ofNullable(new Student(studentName, studentNumber));

            return new AddScoreCommand(student.get(), score);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE));
        }
    }
}
