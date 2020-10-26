package team.serenity.model.group.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNumberTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentNumber(null));
    }
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> StudentNumber.isValidName(null));

        // invalid name
        assertFalse(StudentNumber.isValidName("")); // empty string
        assertFalse(StudentNumber.isValidName(" ")); // spaces only
        assertFalse(StudentNumber.isValidName("^")); // only non-alphanumeric characters
        assertFalse(StudentNumber.isValidName("a012345678911")); // exceed length
        assertFalse(StudentNumber.isValidName("a0123456R")); //a not capitalized
        assertFalse(StudentNumber.isValidName("A0123456r")); //r not capitalized
        assertFalse(StudentNumber.isValidName("B0123456R")); //not A

        // valid name
        assertTrue(StudentNumber.isValidName("A0123456M"));
        assertTrue(StudentNumber.isValidName("A0099334S"));
    }
}
