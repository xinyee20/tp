package team.serenity.testutil;


import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.StudentName;
import team.serenity.model.group.student.StudentNumber;

public class StudentBuilder {

    public static final StudentName DEFAULT_NAME = new StudentName("Aaron");
    public static final StudentNumber DEFAULT_STUDENT_NUMBER = new StudentNumber("A0123456U");

    private StudentName name;
    private StudentNumber studentNumber;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        this.name = DEFAULT_NAME;
        this.studentNumber = DEFAULT_STUDENT_NUMBER;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}
     */
    public StudentBuilder(Student studentToCopy) {
        this.name = studentToCopy.getStudentName();
        this.studentNumber = studentToCopy.getStudentNo();
    }

    /**
     * Sets the {@code name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new StudentName(name);
        return this;
    }

    /**
     * Sets the {@code student id} pf the {@code Student} that we are building.
     */
    public StudentBuilder withId(String id) {
        this.studentNumber = new StudentNumber(id);
        return this;
    }

    public Student build() {
        return new Student(this.name, this.studentNumber);
    }

}
