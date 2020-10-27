package team.serenity.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.storage.question.JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;

import org.junit.jupiter.api.Test;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;

class JsonAdaptedQuestionTest {

    public static final String INVALID_DESCRIPTION = " ";

    public static final String VALID_GROUP = QUESTION_A.getGroupName().groupName;
    public static final String VALID_LESSON = QUESTION_A.getLessonName().lessonName;
    public static final String VALID_DESCRIPTION = QUESTION_A.getDescription().description;

    @Test
    public void toModelType_validQuestion_returnsQuestion() throws Exception {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(QUESTION_A);
        assertEquals(QUESTION_A, question.toModelType());
    }

    @Test
    public void toModelType_nullGroup_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(null, VALID_LESSON, VALID_DESCRIPTION);
        String exceptedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GroupName.class.getSimpleName());
        assertThrows(IllegalValueException.class, exceptedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullLesson_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_GROUP, null, VALID_DESCRIPTION);
        String exceptedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonName.class.getSimpleName());
        assertThrows(IllegalValueException.class, exceptedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_GROUP, VALID_LESSON, INVALID_DESCRIPTION);
        String exceptedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, exceptedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_GROUP, VALID_LESSON, null);
        String exceptedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, exceptedMessage, question::toModelType);
    }


}
