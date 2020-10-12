package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Participation;
import seedu.address.model.group.Question;
import seedu.address.model.group.Student;

public class SerenityParserUtil {

    /**
     * Parses a {@code String student} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static String parseStudent(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Student.isValidString(trimmedName)) {
            throw new ParseException(Student.STUDENT_NAME_ERROR);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String id} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static String parseStudentID(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Student.isValidStudentNumber(trimmedId)) {
            throw new ParseException(Student.STUDENT_NUMBER_ERROR);
        }
        return trimmedId;
    }

    /**
     * Parses {@code String inputScore} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified score is invalid.
     */
    public static int parseScore(String inputScore) throws ParseException {
        String trimmedScore = inputScore.trim();
        int score;
        try {
            score = Integer.parseInt(trimmedScore);
            return score;
        } catch (Exception e) {
            throw new ParseException(Participation.SCORE_ERROR);
        }
    }

    /**
     * Parses a {@code String question} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static String parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return trimmedQuestion;
    }
}
