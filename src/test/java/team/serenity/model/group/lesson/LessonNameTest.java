package team.serenity.model.group.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonName(null));
    }
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "1";
        assertThrows(IllegalArgumentException.class, () -> new LessonName(invalidName));
    }

    @Test
    public void isValidName() {
        assertThrows(NullPointerException.class, () -> LessonName.isValidName(null));
        assertFalse(LessonName.isValidName(""));
        assertFalse(LessonName.isValidName(" "));
        assertFalse(LessonName.isValidName("1 - 1"));
        assertFalse(LessonName.isValidName("111-1"));
        assertFalse(LessonName.isValidName("1-111"));
        assertFalse(LessonName.isValidName("1-11"));
        assertFalse(LessonName.isValidName("11")); //missing hyphen

        // valid name
        assertTrue(LessonName.isValidName("1-1"));
        assertTrue(LessonName.isValidName("10-1"));
    }

    @Test
    public void equals() {
        LessonName first = new LessonName("1-1");

        assertTrue(first.equals(first)); //same object
        assertTrue(first.equals(new LessonName("1-1"))); //different object, same contents

        assertFalse(first.equals(null));
        assertFalse(first.equals(new LessonName("2-1"))); //different object, different contents
    }

}
