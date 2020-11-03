package team.serenity.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupName(null));
    }
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = " ";
        assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidName));
    }

    @Test
    public void isValidName() {
        assertThrows(NullPointerException.class, () -> GroupName.isValidName(null));
        assertFalse(GroupName.isValidName(""));
        assertFalse(GroupName.isValidName(" "));
        assertFalse(GroupName.isValidName("11"));
        assertFalse(GroupName.isValidName("1"));
        assertFalse(GroupName.isValidName("g01"));
        assertFalse(GroupName.isValidName("g001"));
        assertFalse(GroupName.isValidName("G0"));
        assertFalse(GroupName.isValidName("G001"));
        // valid name
        assertTrue(GroupName.isValidName("G03"));
    }

    @Test
    public void equals() {
        GroupName first = new GroupName("G01");

        assertTrue(first.equals(first)); //same object
        assertTrue(first.equals(new GroupName("G01"))); //different object, same contents

        assertFalse(first.equals(null));
        assertFalse(first.equals(new GroupName("G02"))); //different object, different contents
    }
}
