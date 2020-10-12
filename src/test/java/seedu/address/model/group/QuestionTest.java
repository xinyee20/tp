package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalQuestions.QUESTION_1;
import static seedu.address.testutil.TypicalQuestions.QUESTION_2;

import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Question(""));
        assertThrows(IllegalArgumentException.class, () -> new Question(" "));
    }


    @Test
    public void test_getQuestion() {
        assertEquals(QUESTION_1.getQuestion(), "What is the deadline for the report?");
        assertEquals(QUESTION_2.getQuestion(), "When is the consultation held?");
    }

    @Test
    public void test_isValidQuestion() {
        // null question
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid questions
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid questions
        assertTrue(Question.isValidQuestion("When is the consultation held?"));
        assertTrue(Question.isValidQuestion("-")); // one character
        assertTrue(Question.isValidQuestion("What is the deadline for the report? "
                + "Do we submit it in Luminus folders?")); // long question
    }

}
