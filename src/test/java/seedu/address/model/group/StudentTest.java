package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Student(null, null));
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
        assertTrue(new Student("John", "E1234567").getName().equals("John"));
    }

    @Test
    public void test_getStudentNumber() {
        assertTrue(new Student("John", "E1234567").getStudentNumber().equals("E1234567"));
    }

    @Test
    public void test_toString() {
        assertTrue(new Student("John", "E1234567").toString().equals("John E1234567"));
    }

    @Test
    public void test_equals() {
        String studentName = "John";
        String studentId = "E1234567";
        Student first = new Student(studentName, studentId);
        Student second = new Student(studentName, studentId);
        Student differentName = new Student("James", studentId);
        Student differentId = new Student(studentName, "E7654321");

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
