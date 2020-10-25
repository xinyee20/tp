package team.serenity.testutil;

import team.serenity.model.group.Student;

public class StudentBuilder {

    public static final String DEFAULT_NAME = "Ryan Lim";
    public static final String DEFAULT_STUDENT_ID = "A0123456U";

    private String name;
    private String studentId;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        this.name = DEFAULT_NAME;
        this.studentId = DEFAULT_STUDENT_ID;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}
     */
    public StudentBuilder(Student studentToCopy) {
        this.name = studentToCopy.getName();
        this.studentId = studentToCopy.getName();
    }

    /**
     * Sets the {@code name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code student id} pf the {@code Student} that we are building.
     */
    public StudentBuilder withId(String id) {
        this.studentId = id;
        return this;
    }

    public Student build() {
        return new Student(this.name, this.studentId);
    }

}
