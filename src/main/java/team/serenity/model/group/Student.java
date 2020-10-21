package team.serenity.model.group;

import static team.serenity.commons.util.AppUtil.checkArgument;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a tutorial Group in Serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {
    public static final String STUDENT_NAME_ERROR = "Name cannot be empty";
    public static final String STUDENT_NUMBER_ERROR = "Student number cannot be empty "
        + "and must follow the format 'eXXXXXXX' "
        + "where X is a digit from 0 to 9";
    private String name;
    private String studentId;

    /**
     * Constructs a {@code Student}.
     *
     * @param name          A valid name.
     * @param studentId A valid student number.
     */
    public Student(String name, String studentId) {
        requireAllNonNull(name, studentId);
        checkArgument(isValidName(name), STUDENT_NAME_ERROR);
        checkArgument(isValidStudentId(studentId), STUDENT_NUMBER_ERROR);
        this.name = name;
        this.studentId = studentId;
    }

    public static boolean isValidName(String s) {
        return s.length() > 0;
    }

    /**
     * Checks whether String s is a valid Student ID.
     * @param s Student ID
     * @return Whether String is valid
     */
    public static boolean isValidStudentId(String s) {
        //8 digits long
        s = s.toLowerCase();
        boolean matchesLength = s.length() == 8;
        boolean matchesChar = s.charAt(0) == 'e';
        if (!matchesChar || !matchesLength) {
            System.out.println(s);
        }
        return s.length() == 8 && s.charAt(0) == 'e';
    }

    public String getName() {
        return this.name;
    }

    public String getStudentId() {
        return this.studentId;
    }

    @Override
    public String toString() {
        return this.name + " " + this.studentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Student) {
            Student other = (Student) obj;
            return other.getName().equals(getName()) && other.getStudentId().equals(getStudentId());
        } else {
            return false;
        }
    }
}
