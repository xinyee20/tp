package team.serenity.model.group;

import static team.serenity.commons.util.AppUtil.checkArgument;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a tutorial Group in Serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {
    public static final String STUDENT_NAME_ERROR = "Name cannot be empty";
    public static final String STUDENT_ID_ERROR = "Student no cannot be empty "
        + "and must follow the format 'AXXXXXXXU' "
        + "where X is a digit from 0 to 9";
    private String name;
    private String studentNo;

    /**
     * Constructs a {@code Student}.
     *
     * @param name          A valid name.
<<<<<<< HEAD
     * @param studentNo A valid student ID.
=======
     * @param studentNo A valid student no.
>>>>>>> f29741bb... Edit Student files
     */
    public Student(String name, String studentNo) {
        requireAllNonNull(name, studentNo);
        checkArgument(isValidName(name), STUDENT_NAME_ERROR);
        checkArgument(isValidStudentId(studentNo), STUDENT_ID_ERROR);
        this.name = name;
        this.studentNo = studentNo;
    }

    public static boolean isValidName(String s) {
        return s.length() > 0;
    }

    /**
     * Checks whether String s is a valid student no.
     * @param s Student no.
     * @return Whether String is valid.
     */
    public static boolean isValidStudentId(String s) {
        //9 digits long
        s = s.toUpperCase();
        boolean matchesLength = s.length() == 9;
        boolean matchesChar = s.charAt(0) == 'A';
        if (!matchesChar || !matchesLength) {
            System.out.println(s);
        }
        return s.length() == 9 && s.charAt(0) == 'A';
    }

    public String getName() {
        return this.name;
    }

    public String getStudentNo() {
        return this.studentNo;
    }

    @Override
    public String toString() {
        return this.name + " " + this.studentNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Student) {
            Student other = (Student) obj;
            return other.getName().equals(getName()) && other.getStudentNo().equals(getStudentNo());
        } else {
            return false;
        }
    }
}
