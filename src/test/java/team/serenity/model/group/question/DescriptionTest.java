package team.serenity.model.group.question;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Description(""));
        assertThrows(IllegalArgumentException.class, () -> new Description(" \t "));
    }

    @Test
    public void test_isValidDescription() {
        // null question
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid questions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid questions
        assertTrue(Description.isValidDescription("When is the consultation held?"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("What is the deadline for the report? "
                + "Do we submit it in Luminus folders?")); // long question
    }

    @Test
    public void test_toString() {
        assertEquals("1000", new Description("1000").toString());
        assertEquals("ToString", new Description("ToString").toString()); // Caps and non caps characters
        assertEquals("ToString 1000", new Description("ToString 1000").toString()); // Alphanumeric

        //Wrong toString
        assertNotEquals("ToString", new Description("NotToString").toString());
    }

    @Test
    public void test_equals() {
        // Equal Description Object
        assertEquals(new Description("Equals"), new Description("Equals")); // When 2 descriptions are the same.

        // Not Equal Description Object
        assertNotEquals(new Description("Equals"), new Description("NotEquals")); // When 2 descriptions are different.
    }

    @Test
    public void test_hashCode() {
        // Equal Hashcode
        assertEquals(new Description("Hashcode").hashCode(), new Description("Hashcode").hashCode());

        // Not Equal Hashcode
        assertNotEquals(new Description("hashcode").hashCode(), new Description("HashCode").hashCode());
    }

}
