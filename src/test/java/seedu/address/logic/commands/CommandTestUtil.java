package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QN;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    // Valid descriptions for Serenity

    public static final String VALID_GRP_GROUP_A = "G04";
    public static final String VALID_GRP_GROUP_B = "G05";
    public static final Path VALID_PATH_GROUP_A = Paths.get("LUMINUS_GROUP_A.csv");
    public static final Path VALID_PATH_GROUP_B = Paths.get("LUMINUS_GROUP_B.csv");
    public static final String VALID_QN_A = "What is the deadline for the report?";
    public static final String VALID_QN_B = "When is the consultation held?";

    public static final String GRP_DESC_GROUP_A = " " + PREFIX_GRP + VALID_GRP_GROUP_A;
    public static final String GRP_DESC_GROUP_B = " " + PREFIX_GRP + VALID_GRP_GROUP_B;
    public static final String PATH_DESC_GROUP_A = " " + PREFIX_PATH + VALID_PATH_GROUP_A;
    public static final String PATH_DESC_GROUP_B = " " + PREFIX_PATH + VALID_PATH_GROUP_B;
    public static final String QN_DESC_GROUP_A = " " + PREFIX_QN + VALID_QN_A;
    public static final String QN_DESC_GROUP_B = " " + PREFIX_QN + VALID_QN_B;

    public static final String INVALID_QN_DESC = " " + PREFIX_QN; // empty string not allowed in questions

    // ToDo: add invalid descriptions for Serenity

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
