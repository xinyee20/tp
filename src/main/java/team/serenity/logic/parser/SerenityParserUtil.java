package team.serenity.logic.parser;

import static java.util.Objects.requireNonNull;

import team.serenity.commons.core.index.Index;
import team.serenity.commons.util.StringUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Participation;
import team.serenity.model.group.Student;
import team.serenity.model.group.question.Question;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class SerenityParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a {@code String studentName} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static String parseStudentName(String studentName) throws ParseException {
        requireNonNull(studentName);
        String trimmedName = studentName.trim();
        if (!Student.isValidName(trimmedName)) {
            throw new ParseException(Student.STUDENT_NAME_ERROR);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String studentId} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static String parseStudentID(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedId = studentId.trim();
        if (!Student.isValidStudentId(trimmedId)) {
            throw new ParseException(Student.STUDENT_ID_ERROR);
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
     * Parses a {@code String questionDescription} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static String parseDescription(String questionDescription) throws ParseException {
        requireNonNull(questionDescription);
        String trimmedQuestionDescription = questionDescription.trim();
        if (!Question.isValidDescription(trimmedQuestionDescription)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return trimmedQuestionDescription;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

}
