package team.serenity.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalQuestions.QUESTION_1;
import static team.serenity.testutil.TypicalQuestions.QUESTION_2;

import org.junit.jupiter.api.Test;

import team.serenity.testutil.question.QuestionBuilder;

class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
        assertThrows(NullPointerException.class, () -> new Question(null, null, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Question(""));
        assertThrows(IllegalArgumentException.class, () -> new Question(" "));
    }

    @Test
    public void test_isValidDescription() {
        // null question
        assertThrows(NullPointerException.class, () -> Question.isValidDescription(null));

        // invalid questions
        assertFalse(Question.isValidDescription("")); // empty string
        assertFalse(Question.isValidDescription(" ")); // spaces only

        // valid questions
        assertTrue(Question.isValidDescription("When is the consultation held?"));
        assertTrue(Question.isValidDescription("-")); // one character
        assertTrue(Question.isValidDescription("What is the deadline for the report? "
                + "Do we submit it in Luminus folders?")); // long question
    }

    @Test
    public void test_setGroupAndLesson() {
        Question toSet = new QuestionBuilder(QUESTION_1).build();
        toSet.setGroupAndLesson("G10", "3-2");
        Question expectedQuestion = new QuestionBuilder().withDescription(QUESTION_1.getDescription())
                .withGroup("G10").withLesson("3-2").build();
        assertEquals(expectedQuestion, toSet);
    }

    @Test
    public void test_getGroup() {
        assertEquals(QUESTION_1.getGroup(), "G04");
        assertEquals(QUESTION_2.getGroup(), "G05");
    }
    @Test
    public void test_getLesson() {
        assertEquals(QUESTION_1.getLesson(), "2-2");
        assertEquals(QUESTION_2.getLesson(), "3-1");
    }

    @Test
    public void test_getDescription() {
        assertEquals(QUESTION_1.getDescription(), "What is the deadline for the report?");
        assertEquals(QUESTION_2.getDescription(), "When is the consultation held?");
    }

    @Test
    public void testEquals() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_1, editedQuestion);

        // Different case
        assertNotEquals(QUESTION_1, QUESTION_2);
    }

    @Test
    public void test_hashCode() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_1.hashCode(), editedQuestion.hashCode());

        // Different case
        assertNotEquals(QUESTION_1.hashCode(), QUESTION_2.hashCode());
    }

    @Test
    public void testToString() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_1.toString(), editedQuestion.toString());

        // Different case
        assertNotEquals(QUESTION_1.toString(), QUESTION_2.toString());

    }

}
