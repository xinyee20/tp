package team.serenity.model.group.studentinfo;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import team.serenity.model.group.student.Student;

/**
 * Represents a {@code Student} interaction with a Lesson.
 * Stores {@code Student}, {@code Participation} and {@code Attendance}.
 */
public class StudentInfo {

    private final Student student;
    private final Participation participation;
    private final Attendance attendance;

    /**
     * Creates a {@code StudentInfo} from a {@code Student}, {@code Participation} and {@code Attendance}.
     *
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
     * Creates an empty {@code StudentInfo} from a {@code Student}.
     * @param student
     */
    public StudentInfo(Student student) {
        this.student = student;
        this.participation = new Participation();
        this.attendance = new Attendance();
    }

    public Student getStudent() {
        return this.student;
    }

    public Participation getParticipation() {
        return this.participation;
    }

    public Attendance getAttendance() {
        return this.attendance;
    }

    /**
     * Check whether the student in Student Info matches the specific student given in the input.
     * @param student Student to be checked
     * @return Yes if student is correct, No if student is wrong
     */
    public boolean containsStudent(Student student) {
        boolean isCorrectStudent = this.student.equals(student);
        return isCorrectStudent;
    }

    /**
     * Marks the student present or absent for the class.
     * @param updatedAttendance The attendance of the student for the lesson
     * @return The updated Attendance
     */
    public StudentInfo updateAttendance(Attendance updatedAttendance) {
        StudentInfo updatedStudentInfo = new StudentInfo(this.student, this.participation, updatedAttendance);
        return updatedStudentInfo;
    }

    /**
     * Updates the student participation score for the class.
     * @param updatedScore The participation score of the student for the lesson
     * @return The updated Participation object
     */
    public StudentInfo updateParticipation(Participation updatedScore) {
        StudentInfo updatedStudentInfo = new StudentInfo(this.student, updatedScore, this.attendance);
        return updatedStudentInfo;
    }

    @Override
    public boolean equals(Object obj) {
        StudentInfo other = (StudentInfo) obj;
        return this.student.equals(other.getStudent())
            && this.participation.equals(other.getParticipation())
            && this.attendance.equals(other.getAttendance());
    }

    @Override
    public String toString() {
        return String
            .format("Name: %s, participation: %s, attendance: %s", this.student, this.participation, this.attendance);
    }
}
