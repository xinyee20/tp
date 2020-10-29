package team.serenity.model.group.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentName(null));
    }
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> StudentName.isValidName(null));

        // invalid name
        assertFalse(StudentName.isValidName("")); // empty string
        assertFalse(StudentName.isValidName(" ")); // spaces only
        assertFalse(StudentName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(StudentName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(StudentName.isValidName("peter jack")); // alphabets only
        assertTrue(StudentName.isValidName("12345")); // numbers only
        assertTrue(StudentName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(StudentName.isValidName("Capital Tan")); // with capital letters
        assertTrue(StudentName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        StudentName john = new StudentName("John");
        assertTrue(john.equals(john)); //same object
        assertTrue(john.equals(new StudentName("John"))); //different object, same contents
        assertTrue(john.equals(new StudentName("JOHN"))); //different object, same name in caps

        assertFalse(john.equals(new StudentName("John "))); //different object, same name with space
        assertFalse(john.equals(null));
        assertFalse(john.equals(new StudentName("Sally"))); //different object, different contents
    }

}
