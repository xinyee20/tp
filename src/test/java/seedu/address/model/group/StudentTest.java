package seedu.address.model.group;

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

}
