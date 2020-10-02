package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a tutorial Group in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Student {
    public static final String STUDENT_NAME_ERROR = "Name cannot be empty";
    public static final String STUDENT_NUMBER_ERROR = "Student number cannot be empty";
    private String name;
    private String studentNumber;


    /**
     * Constructs a {@code Student}.
     *
     * @param name          A valid name.
     * @param studentNumber A valid student number.
     */
    public Student(String name, String studentNumber) {
        requireAllNonNull(name, studentNumber);
        checkArgument(isValidString(name), STUDENT_NAME_ERROR);
        checkArgument(isValidStudentNumber(studentNumber), STUDENT_NUMBER_ERROR);
        this.name = name;
        this.studentNumber = studentNumber;
    }

    public static boolean isValidString(String s) {
        return s.length() > 0;
    }

    /**
     * Checks whether String s is a valid Student number
     * @param s Student number
     * @return Whether String is valid
     */
    public static boolean isValidStudentNumber(String s) {
        //8 digits long
        s = s.toLowerCase();
        return s.length() == 8 && s.charAt(0) == 'e';
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

    @Override
    public boolean equals(Object obj) {
        Student other = (Student) obj;
        return other.getName().equals(getName()) && other.getStudentNumber().equals(getStudentNumber());
    }
}
