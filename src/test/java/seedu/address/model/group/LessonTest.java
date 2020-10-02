package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudent.JAMES;
import static seedu.address.testutil.TypicalStudent.JOHN;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Set<Score> scores = new HashSet<>();
        Score score = new Score(JOHN, new Participation(), new Attendance());
        scores.add(score);
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidName, scores));
    }

    @Test
    public void constructor_emptyClass_throwsIllegalArgumentException() {
        String name = "1-1";
        Set<Student> students = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> new Lesson(name, new HashSet<>()));
    }

    @Test
    public void equals() {
        Set<Score> scores = new HashSet<>();
        Score score = new Score(JOHN);
        scores.add(score);
        Lesson oneOne = new Lesson("1-1", scores);
        Lesson oneOneClone = new Lesson("1-1", scores);
        Lesson classTwoOne = new Lesson("2-1", scores); //same students, different name
        Set<Score> newScores = new HashSet<>();
        newScores.add(new Score(JOHN));
        newScores.add(new Score(JAMES));
        Lesson oneOneWithTwoStudents = new Lesson("1-1", newScores);
        Lesson twoOneWithTwoStudents = new Lesson("2-1", newScores);
        assertTrue(oneOneClone.equals(oneOne)); //same
        assertFalse(oneOneWithTwoStudents.equals(oneOne)); //same class name, different students
        assertFalse(classTwoOne.equals(oneOne)); //different class name, same students
        assertFalse(twoOneWithTwoStudents.equals(oneOne)); //different class name, different students
    }
}
