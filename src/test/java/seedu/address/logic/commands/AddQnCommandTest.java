package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalQuestions.QUESTION_1;
import static seedu.address.testutil.TypicalQuestions.QUESTION_2;

import org.junit.jupiter.api.Test;

class AddQnCommandTest {

    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddQnCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addQnSuccessful() {
        // TODO: assertEquals upon successful adding of Question
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        // TODO: assertThrows when adding a duplicate Question
    }

    @Test
    public void equals() {
        AddQnCommand addQnACommand = new AddQnCommand(QUESTION_1);
        AddQnCommand addQnBCommand = new AddQnCommand(QUESTION_2);

        // same object -> returns true
        assertTrue(addQnACommand.equals(addQnACommand));

        // same values -> returns true
        AddQnCommand addQnACommandCopy = new AddQnCommand(QUESTION_1);
        assertTrue(addQnACommandCopy.equals(addQnACommand));

        // different types -> returns false
        assertFalse(addQnACommand.equals(1));

        // null -> returns false
        assertFalse(addQnACommand.equals(null));

        // different group -> returns false
        assertFalse(addQnACommand.equals(addQnBCommand));
    }

}
