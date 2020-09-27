package seedu.address.model.group;

/**
 * Represents a tutorial Group in serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    private String name;
    private String studentNumber;

    /**
     * Constructs a {@code Student}.
     *
     * @param name A valid name.
     * @param studentNumber A valid student number.
     */
    public Student(String name, String studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    @Override
    public String toString() {
        return name + ": " + studentNumber;
    }
}
