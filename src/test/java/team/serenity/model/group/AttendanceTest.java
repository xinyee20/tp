package team.serenity.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void testGetAttendance() {
        Attendance test = new Attendance(false, true);
        assertFalse(test.getAttendance());
    }

    @Test
    public void testGetFlagged() {
        Attendance test = new Attendance(false, true);
        assertTrue(test.getFlagged());
    }

    @Test
    public void nullConstructor_success() {
        Attendance test = new Attendance();
        assertFalse(test.getAttendance());
        assertFalse(test.getFlagged());
    }

    @Test
    public void constructor_markPresent_success() {
        Attendance test = new Attendance(true);
        assertTrue(test.getAttendance());
    }

    @Test
    public void constructor_markPresentAbsent_success() {
        Attendance test = new Attendance(false);
        assertFalse(test.getAttendance());
    }

    @Test
    public void constructor_flagAttendanceWithStudentAbsent_success() {
        Attendance test = new Attendance(false, true);
        assertTrue(test.getFlagged());
    }

    @Test
    public void constructor_flagAttendanceWithStudentPresent_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Attendance(true, true));
    }


    @Test
    void setNewAttendance() {
        Attendance newAttendance = new Attendance(true);
        Attendance originalAttendance = new Attendance();
        assertEquals(newAttendance, originalAttendance.setNewAttendance(true));
    }

    @Test
    void testEquals() {
        Attendance testOne = new Attendance(true);
        Attendance testTwo = new Attendance(true);
        Attendance testThree = new Attendance(false);
        assertTrue(testOne.equals(testTwo));
        assertFalse(testOne.equals(testThree));
    }

    @Test
    void testToString() {
        String expectedString = "true";
        assertEquals(expectedString, new Attendance(true).toString());
    }
}
