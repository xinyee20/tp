package team.serenity.model.group.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;

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
        Question toSet = new QuestionBuilder(QUESTION_A).build();
        toSet.setGroupAndLesson("G10", "3-2");
        Question expectedQuestion = new QuestionBuilder().withDescription(QUESTION_A.getDescription())
                .withGroup("G10").withLesson("3-2").build();
        assertEquals(expectedQuestion, toSet);
    }

    @Test
    public void test_getGroup() {
        assertEquals(QUESTION_A.getGroup(), "G04");
        assertEquals(QUESTION_B.getGroup(), "G05");
    }
    @Test
    public void test_getLesson() {
        assertEquals(QUESTION_A.getLesson(), "2-2");
        assertEquals(QUESTION_B.getLesson(), "3-1");
    }

    @Test
    public void test_getDescription() {
        assertEquals(QUESTION_A.getDescription(), "What is the deadline for the report?");
        assertEquals(QUESTION_B.getDescription(), "What do we need to prepare for tomorrow's lesson?");
    }

    @Test
    public void test_equals() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_A, editedQuestion);

        // Different case
        assertNotEquals(QUESTION_A, QUESTION_B);
    }

    @Test
    public void test_hashCode() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_A.hashCode(), editedQuestion.hashCode());

        // Different case
        assertNotEquals(QUESTION_A.hashCode(), QUESTION_B.hashCode());
    }

    @Test
    public void test_toString() {
        // Same case
        Question editedQuestion = new Question("G04", "2-2", "What is the deadline for the report?");
        assertEquals(QUESTION_A.toString(), editedQuestion.toString());

        // Different case
        assertNotEquals(QUESTION_A.toString(), QUESTION_B.toString());
    }

}
