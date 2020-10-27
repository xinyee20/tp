package team.serenity.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.lesson.LessonName;

public class GroupLessonKeyTest {

    @Test
    public void constructor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupLessonKey(null, null));
        assertThrows(NullPointerException.class, () -> new GroupLessonKey(new GroupName("G03"), null));
        assertThrows(NullPointerException.class, () -> new GroupLessonKey(null, new LessonName("1-1")));
    }

    @Test
    public void hashCode_checkEquality() {
        GroupName gThree = new GroupName("G03");
        GroupName gFour = new GroupName("G04");
        LessonName oneOne = new LessonName("1-1");
        LessonName oneTwo = new LessonName("1-2");

        GroupLessonKey keyOne = new GroupLessonKey(gThree, oneOne);
        GroupLessonKey keyTwo = new GroupLessonKey(gThree, oneTwo);

        GroupLessonKey keyThree = new GroupLessonKey(gFour, oneOne);
        GroupLessonKey keyFour = new GroupLessonKey(gFour, oneTwo);

        GroupLessonKey keyFive = new GroupLessonKey(new GroupName("G03"), new LessonName("1-1"));

        assertFalse(keyOne.hashCode() == keyTwo.hashCode()); //same group, different lesson
        assertFalse(keyOne.hashCode() == keyThree.hashCode()); //different group, same lesson
        assertFalse(keyOne.hashCode() == keyFour.hashCode()); //different group, different lesson

        assertTrue(keyOne.hashCode() == keyFive.hashCode()); //different object, same contents
    }
}
