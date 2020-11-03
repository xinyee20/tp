package team.serenity.model.group.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.testutil.question.QuestionBuilder;

class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null, null, null));
    }

    @Test
    public void test_setGroupAndLesson() {
        Question toSet = new QuestionBuilder(QUESTION_A).build();
        Question newQuestion = toSet.setGroupAndLesson(new GroupName("G10"), new LessonName("3-2"));
        Question expectedQuestion = new QuestionBuilder().withDescription(QUESTION_A.getDescription().description)
                .withGroupName("G10").withLessonName("3-2").build();
        assertEquals(expectedQuestion, newQuestion);
    }

    @Test
    public void test_getGroup() {
        assertEquals(QUESTION_A.getGroupName(), new GroupName("G04"));
        assertEquals(QUESTION_B.getGroupName(), new GroupName("G05"));
    }
    @Test
    public void test_getLesson() {
        assertEquals(QUESTION_A.getLessonName(), new LessonName("2-2"));
        assertEquals(QUESTION_B.getLessonName(), new LessonName("3-1"));
    }

    @Test
    public void test_getDescription() {
        assertEquals(QUESTION_A.getDescription(), new Description("What is the deadline for the report?"));
        assertEquals(QUESTION_B.getDescription(), new Description("What do we need to prepare for tomorrow's lesson?"));
    }

    @Test
    public void test_equals() {
        // Same case
        Question editedQuestion = new QuestionBuilder().withGroupName("G04").withLessonName("2-2")
            .withDescription("What is the deadline for the report?").build();
        assertEquals(QUESTION_A, editedQuestion);

        // Different case
        assertNotEquals(QUESTION_A, QUESTION_B);
    }

    @Test
    public void test_hashCode() {
        // Same case
        Question editedQuestion = new QuestionBuilder().withGroupName("G04").withLessonName("2-2")
            .withDescription("What is the deadline for the report?").build();
        assertEquals(QUESTION_A.hashCode(), editedQuestion.hashCode());

        // Different case
        assertNotEquals(QUESTION_A.hashCode(), QUESTION_B.hashCode());
    }

    @Test
    public void test_toString() {
        // Same case
        Question editedQuestion = new QuestionBuilder().withGroupName("G04").withLessonName("2-2")
            .withDescription("What is the deadline for the report?").build();
        assertEquals(QUESTION_A.toString(), editedQuestion.toString());

        // Different case
        assertNotEquals(QUESTION_A.toString(), QUESTION_B.toString());
    }

}
