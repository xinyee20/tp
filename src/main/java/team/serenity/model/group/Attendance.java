package team.serenity.model.group;

/**
 * Represents a {@code Student} attendance in a Class
 */
public class Attendance {

    private final boolean isPresent;
    private final boolean isFlagged;

    /**
     * Creates a default Attendance object that is not flagged and not present
     */
    public Attendance() {
        this.isFlagged = false;
        this.isPresent = false;
    }

    /**
     * Creates an Attendance object that is not flagged and with presence marked accordingly
     * @param isPresent True if student is present and false if student is absent
     */
    public Attendance(boolean isPresent) {
        this.isPresent = isPresent;
        this.isFlagged = false;
    }

    @Override
    public String toString() {
        return Boolean.toString(isPresent);
    }

    public boolean getAttendance() {
        return isPresent;
    }

    public Attendance setAttendance(boolean isPresent) {
        Attendance updatedAttendance = new Attendance(isPresent);
        return updatedAttendance;
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    @Override
    public boolean equals(Object obj) {
        Attendance other = (Attendance) obj;
        return other.getAttendance() == getAttendance() && other.getFlagged() == getFlagged();
    }
}
