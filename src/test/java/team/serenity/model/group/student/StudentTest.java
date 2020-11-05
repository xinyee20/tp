package team.serenity.model.group.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Student((String) null, null));
        assertThrows(NullPointerException.class, () -> new Student((StudentName) null, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Student("", "E1234567"));
    }

    @Test
    public void constructor_invalidStudentNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Student("John", "A012334444333"));
    }

    @Test
    public void test_getStudentName() {
        assertTrue(new Student("John", "A1234567U").getStudentName().toString().equals("JOHN"));
    }

    @Test
    public void test_getStudentNumber() {
        assertTrue(new Student("John", "A1234567U").getStudentNo().toString().equals("A1234567U"));
    }

    @Test
    public void test_toString() {
        assertTrue(new Student("John", "A1234567U").toString().equals("JOHN A1234567U"));
    }

    @Test
    public void test_equals() {
        String studentName = "John";
        String studentId = "A1234567U";
        Student first = new Student(studentName, studentId);
        Student second = new Student(studentName, studentId);
        Student differentName = new Student("James", studentId);
        Student differentId = new Student(studentName, "A7654321U");

        //same object
        assertTrue(first.equals(first));

        //same value
        assertTrue(first.equals(second));

        //different values
        assertFalse(first.equals(differentName));
        assertFalse(first.equals(differentId));

        //compare with null
        assertFalse(first.equals(null));
    }

}
