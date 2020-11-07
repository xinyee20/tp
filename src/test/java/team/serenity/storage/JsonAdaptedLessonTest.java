package team.serenity.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalLesson.LESSON_1_1;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.lesson.Lesson;

class JsonAdaptedLessonTest {

    @Test
    public void constructor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedLesson(null));
    }

    @Test
    public void toModelType() throws Exception {
        Lesson expectedLesson = LESSON_1_1;
        assertEquals(new JsonAdaptedLesson(expectedLesson).toModelType(), (expectedLesson));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws Exception {
        String emptyName = "";
        String doubleDigit = "1-11";
        String invalidFormat = "1--1";
        JsonAdaptedLesson emptyLessonName = new JsonAdaptedLesson(emptyName, new ArrayList<>());
        JsonAdaptedLesson invalidLessonName = new JsonAdaptedLesson(doubleDigit, new ArrayList<>());
        JsonAdaptedLesson invalidFormatLessonName = new JsonAdaptedLesson(invalidFormat, new ArrayList<>());
        assertThrows(IllegalValueException.class, () -> emptyLessonName.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidLessonName.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidFormatLessonName.toModelType());
    }
}
