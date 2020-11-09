package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ADD_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SET_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SUBTRACT_SCORE;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.commands.question.EditQnCommand;
import team.serenity.model.Model;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;
import team.serenity.model.managers.QuestionManager;
import team.serenity.testutil.question.EditQuestionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid descriptions for Serenity
    public static final String VALID_GROUP_NAME_G01 = "G01";
    public static final String VALID_GROUP_NAME_G02 = "G02";
    public static final String VALID_GROUP_NAME_G04 = "G04";
    public static final String VALID_GROUP_NAME_G05 = "G05";
    public static final String VALID_LESSON_NAME_1_1 = "1-1";
    public static final String VALID_LESSON_NAME_1_2 = "1-2";
    public static final String VALID_PATH_G04 = "CS2101_G04.xlsx";
    public static final String VALID_PATH_G05 = "CS2101_G05.xlsx";
    public static final String VALID_XLSX_NO_LESSONS = "CS2101_nolessons.xlsx";
    public static final String VALID_XLSX_NO_TITLE = "CS2101_notitle.xlsx";

    public static final String VALID_QN_DESC_A = "What is the deadline for the report?";
    public static final String VALID_QN_DESC_B = "When is the consultation held?";
    public static final String VALID_STUDENT_NAME_AARON = "Aaron Tan";
    public static final String VALID_STUDENT_NUMBER_AARON = "A0123456A";
    public static final String VALID_INDEX = "1";
    public static final String VALID_SCORE = "1";
    public static final String VALID_ADD = "1";
    public static final String VALID_SUB = "1";

    public static final String NON_INTEGER = "A";
    public static final String NEGATIVE_INTEGER = "-1";

    public static final String GRP_DESC_GROUP_G01 = " " + PREFIX_GRP + VALID_GROUP_NAME_G01;
    public static final String GRP_DESC_GROUP_G02 = " " + PREFIX_GRP + VALID_GROUP_NAME_G02;
    public static final String GRP_DESC_GROUP_G04 = " " + PREFIX_GRP + VALID_GROUP_NAME_G04;
    public static final String GRP_DESC_GROUP_G05 = " " + PREFIX_GRP + VALID_GROUP_NAME_G05;
    public static final String LESSON_DESC_LESSON_1_1 = " " + PREFIX_LSN + VALID_LESSON_NAME_1_1;
    public static final String LESSON_DESC_LESSON_1_2 = " " + PREFIX_LSN + VALID_LESSON_NAME_1_2;
    public static final String PATH_DESC_GROUP_G04 = " " + PREFIX_PATH + VALID_PATH_G04;
    public static final String PATH_DESC_GROUP_G05 = " " + PREFIX_PATH + VALID_PATH_G05;
    public static final String QN_DESC_GROUP_A = " " + PREFIX_QN + VALID_QN_DESC_A;
    public static final String QN_DESC_GROUP_B = " " + PREFIX_QN + VALID_QN_DESC_B;
    public static final String STUDENT_DESC_AARON =
            " " + PREFIX_NAME + VALID_STUDENT_NAME_AARON + " " + PREFIX_MATRIC + VALID_STUDENT_NUMBER_AARON;
    public static final String STUDENT_NAME_DESC = " " + PREFIX_NAME + VALID_STUDENT_NAME_AARON;
    public static final String STUDENT_NUMBER_DESC = " " + PREFIX_MATRIC + VALID_STUDENT_NUMBER_AARON;
    public static final String SET_SCORE_DESC = " " + PREFIX_SET_SCORE + VALID_SCORE;
    public static final String ADD_SCORE_DESC = " " + PREFIX_ADD_SCORE + VALID_ADD;
    public static final String SUB_SCORE_DESC = " " + PREFIX_SUBTRACT_SCORE + VALID_SUB;

    // Invalid descriptions for Serenity
    public static final String INVALID_GROUP_WITHOUT_NAME = " " + PREFIX_PATH + VALID_PATH_G04;
    public static final String INVALID_GROUP_WITHOUT_PATH = " " + PREFIX_GRP + VALID_GROUP_NAME_G04;
    public static final String INVALID_GROUP_NAME_TOO_MANY_CHARS = " " + PREFIX_GRP + "G044";
    public static final String INVALID_GROUP_NAME_TOO_LESS_CHARS = " " + PREFIX_GRP + "G";
    public static final String INVALID_GROUP_NAME_INVALID_CHARS = " " + PREFIX_GRP + "!!!";
    public static final String INVALID_GROUP_NAME_NON_ALPHABET = " " + PREFIX_GRP + "004";
    public static final String INVALID_GROUP_NAME_NON_DIGITS = " " + PREFIX_GRP + "Gxx";
    public static final String INVALID_GROUP_NAME_EMPTY = " " + PREFIX_GRP + "";
    public static final String INVALID_GROUP_NAME_DASH = " " + PREFIX_GRP + "G-04";
    public static final String INVALID_GROUP_NAME_MULTIPLE = " " + PREFIX_GRP + "G04 G05 G06";

    public static final String INVALID_PATH_NON_XLSX = " " + PREFIX_PATH + "CS2101_G04.xls";
    public static final String INVALID_PATH = " " + PREFIX_PATH + "invalid.xlsx";
    public static final String INVALID_PATH_EMPTY = " " + PREFIX_PATH + "";

    public static final String INVALID_XLSX_EMPTY = "CS2101_empty.xlsx";
    public static final String INVALID_XLSX_NO_HEADER_COLUMNS = "CS2101_noheadercolumns.xlsx";
    public static final String INVALID_XLSX_NO_STUDENTS = "CS2101_nostudents.xlsx";
    public static final String INVALID_XLSX_WRONG_HEADER_COLUMNS = "CS2101_wrongheadercolumns.xlsx";

    public static final String INVALID_LESSON_NAME_TEN = " " + PREFIX_LSN + "1-10";
    public static final String INVALID_QN_DESC = " " + PREFIX_QN; // empty string not allowed in questions
    public static final String INVALID_STUDENT_WITHOUT_NAME = " " + PREFIX_MATRIC + VALID_STUDENT_NUMBER_AARON;
    public static final String INVALID_STUDENT_WITHOUT_NUMBER = " " + PREFIX_NAME + VALID_STUDENT_NAME_AARON;
    public static final String INVALID_PREFIX = "a/";
    public static final String INVALID_INDEX = "A";
    public static final String INVALID_INDEX_NEGATIVE = "-1";
    public static final String INVALID_INDEX_ZERO = "0";
    public static final String NON_INTEGER_SET_SCORE = " " + PREFIX_SET_SCORE + NON_INTEGER;
    public static final String NEG_NUMBER_SET_SCORE = " " + PREFIX_SET_SCORE + NEGATIVE_INTEGER;
    public static final String NON_INTEGER_ADD_SCORE = " " + PREFIX_ADD_SCORE + "A";
    public static final String NEG_NUMBER_ADD_SCORE = " " + PREFIX_ADD_SCORE + NEGATIVE_INTEGER;
    public static final String NON_INTEGER_SUB_SCORE = " " + PREFIX_SUBTRACT_SCORE + "A";
    public static final String NEG_NUMBER_SUB_SCORE = " " + PREFIX_SUBTRACT_SCORE + NEGATIVE_INTEGER;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String EMPTY_PREAMBLE = "";

    public static final EditQnCommand.EditQuestionDescriptor EDITED_QN_A;
    public static final EditQnCommand.EditQuestionDescriptor EDITED_QN_B;

    static {
        EDITED_QN_A = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_G04)
                .withLessonName(VALID_LESSON_NAME_1_1).withDescription(VALID_QN_DESC_A).build();
        EDITED_QN_B = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_G05)
                .withLessonName(VALID_LESSON_NAME_1_2).withDescription(VALID_QN_DESC_B).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br> - the returned {@link CommandResult} matches {@code
     * expectedCommandResult} <br> - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that takes a string
     * {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
        Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage} to test for viewQnCommand.
     */
    public static void assertViewQnCommandSuccess(Command command, Model actualModel,
                                                  String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.UiAction.VIEW_QN);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> - the
     * CommandException message matches {@code expectedMessage} <br> - the question manager, filtered question list and
     * selected question in {@code actualModel} remain unchanged
     */
    public static void assertQuestionCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        QuestionManager expectedQuestionManager = new QuestionManager(actualModel.getQuestionManager());
        List<Question> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQuestionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedQuestionManager, actualModel.getQuestionManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredQuestionList());
    }

    /**
     * Updates {@code model}'s filtered question list to show only the question at the given {@code targetIndex}
     * in the {@code model}'s question list.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionList().size());

        Question question = model.getFilteredQuestionList().get(targetIndex.getZeroBased());
        final String[] splitWord = question.getDescription().description.split("\\s+");
        QuestionContainsKeywordPredicate predicate =
                new QuestionContainsKeywordPredicate(Arrays.asList(splitWord[splitWord.length - 1]));
        model.updateFilteredQuestionList(predicate);

        assertEquals(1, model.getFilteredQuestionList().size());
    }

}
