package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a {@code Student} interaction with a Class Stores the {@code Student} {@code Participation} and {@code
 * Attendance}
 */
public class StudentInfo {

    private final Student student;
    private final Participation participation;
    private final Attendance attendance;

    /**
     * Creates a {@code Score} from a {@code Student} {@code Participation} and {@code
     *  * Attendance}
     * @param student
     * @param participation
     * @param attendance
     */
    public StudentInfo(Student student, Participation participation, Attendance attendance) {
        requireAllNonNull(student, participation, attendance);
        this.student = student;
        this.participation = participation;
        this.attendance = attendance;
    }

    /**
     * Creates an empty {@code Score} from a {@code Student}
     * @param student
     */
    public StudentInfo(Student student) {
        this.student = student;
        this.participation = new Participation();
        this.attendance = new Attendance();
    }

    public Student getStudent() {
        return student;
    }

    public Participation getParticipation() {
        return participation;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    @Override
    public boolean equals(Object obj) {
        StudentInfo other = (StudentInfo) obj;
        return student.equals(other.getStudent()) && participation.equals(other.getParticipation())
            && attendance.equals(other.getAttendance());
    }

    @Override
    public String toString() {
        return String
            .format("Name: %s, participation: %s, attendance: %s", student, participation, attendance);
    }
}
