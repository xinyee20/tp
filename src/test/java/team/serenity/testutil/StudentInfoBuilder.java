package team.serenity.testutil;


import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;

public class StudentInfoBuilder {

    public static final Student DEFAULT_STUDENT = TypicalStudent.JAMES;
    public static final Attendance DEFAULT_ATTENDANCE = new Attendance();
    public static final Participation DEFAULT_PARTICIPATION = new Participation();

    private Student student;
    private Attendance attendance;
    private Participation participation;

    /**
     * Creates a {@code StudentInfoBuilder} with default details.
     */
    public StudentInfoBuilder() {
        this.student = DEFAULT_STUDENT;
        this.attendance = DEFAULT_ATTENDANCE;
        this.participation = DEFAULT_PARTICIPATION;
    }

    /**
     * Initializes the StudentInfo with the data of {@code studentInfoToCopy}.
     */
    public StudentInfoBuilder(StudentInfo studentInfoToCopy) {
        this.student = studentInfoToCopy.getStudent();
        this.attendance = studentInfoToCopy.getAttendance();
        this.participation = studentInfoToCopy.getParticipation();
    }

    /**
     * Parses the {@code students} and set it to the {@code studentInfo} that we are building.
     */
    public StudentInfoBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    /**
     * Parses the {@code attendance} and set it to the {@code studentInfo} that we are building.
     */
    public StudentInfoBuilder withAttendance(boolean attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    /**
     * Parses the {@code attendance} and set it to the {@code studentInfo} that we are building.
     */
    public StudentInfoBuilder withParticipation(int participation) {
        this.participation = new Participation(participation);
        return this;
    }

    public StudentInfo build() {
        return new StudentInfo(this.student, this.participation, this.attendance);
    }
}
