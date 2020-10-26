package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ADD_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SET_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SUBTRACT_SCORE;

import java.nio.file.Path;
import java.nio.file.Paths;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid descriptions for Serenity
    public static final String VALID_GRP_GROUP_A = "G04";
    public static final String VALID_GRP_GROUP_B = "G05";
    public static final Path VALID_PATH_GROUP_A = Paths.get("LUMINUS_GROUP_A.csv");
    public static final Path VALID_PATH_GROUP_B = Paths.get("LUMINUS_GROUP_B.csv");
    public static final String VALID_LSN_A = "2-2";
    public static final String VALID_LSN_B = "3-1";
    public static final String VALID_QN_DESC_A = "What is the deadline for the report?";
    public static final String VALID_QN_DESC_B = "When is the consultation held?";
    public static final String VALID_STUDENT_NAME = "Ryan Lim";
    public static final String VALID_STUDENT_NUMBER_A = "A0123456U";
    public static final String VALID_INDEX = "1";
    public static final String VALID_SCORE = "1";
    public static final String VALID_ADD = "1";
    public static final String VALID_SUB = "1";

    public static final String GRP_DESC_GROUP_A = " " + PREFIX_GRP + VALID_GRP_GROUP_A;
    public static final String GRP_DESC_GROUP_B = " " + PREFIX_GRP + VALID_GRP_GROUP_B;
    public static final String PATH_DESC_GROUP_A = " " + PREFIX_PATH + VALID_PATH_GROUP_A;
    public static final String PATH_DESC_GROUP_B = " " + PREFIX_PATH + VALID_PATH_GROUP_B;
    public static final String QN_DESC_GROUP_A = " " + PREFIX_QN + VALID_QN_DESC_A;
    public static final String QN_DESC_GROUP_B = " " + PREFIX_QN + VALID_QN_DESC_B;
    public static final String STUDENT_DESC = " " + PREFIX_NAME + VALID_STUDENT_NAME + " " + PREFIX_MATRIC + VALID_STUDENT_NUMBER_A;
    public static final String SET_SCORE_DESC = " " + PREFIX_SET_SCORE + VALID_SCORE;
    public static final String ADD_SCORE_DESC = " " + PREFIX_ADD_SCORE + VALID_ADD;
    public static final String SUB_SCORE_DESC = " " + PREFIX_SUBTRACT_SCORE + VALID_SUB;

    // Invalid descriptions for Serenity
    public static final String INVALID_QN_DESC = " " + PREFIX_QN; // empty string not allowed in questions
    public static final String INVALID_STUDENT_WITHOUT_NAME = " " + PREFIX_MATRIC + VALID_STUDENT_NUMBER_A;
    public static final String INVALID_STUDENT_WITHOUT_NUMBER = " " + PREFIX_NAME + VALID_STUDENT_NAME;
    public static final String INVALID_INDEX = "A";
    public static final String INVALID_SET_SCORE = " " + PREFIX_SET_SCORE + "A";
    public static final String INVALID_ADD_SCORE = " " + PREFIX_ADD_SCORE + "A";
    public static final String INVALID_SUB_SCORE = " " + PREFIX_SUBTRACT_SCORE + "A";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

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
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> - the
     * CommandException message matches {@code expectedMessage} <br> - the address book, filtered person list and
     * selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // TODO
        /*
        FOR REFERENCE (AB3)
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
         */
    }

    /**
     * Updates {@code model}'s filtered question list to show only the question at the given {@code targetIndex}
     * in the {@code model}'s question list.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        // TODO: Wen Jin to complete when implementing view/find question
        /*
        FOR REFERENCE (AB3)
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
         */
    }

}
