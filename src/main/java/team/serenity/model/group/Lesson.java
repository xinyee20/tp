package team.serenity.model.group;

import static team.serenity.commons.util.AppUtil.checkArgument;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import team.serenity.model.util.UniqueList;

/**
 * Represents a tutorial class in Serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {

    public static final String NAME_CONSTRAINT = "Class name cannot be empty";
    public static final String STUDENTS_INFO_CONSTRAINT = "Students information cannot be empty";

    private final String name;
    private final UniqueList<StudentInfo> studentsInfo;

    /**
     * Constructs a {@code Lesson}.
     * @param name A valid name.
     * @param studentsInfo A valid list of studentInfo.
     */
    public Lesson(String name, UniqueList<StudentInfo> studentsInfo) {
        requireAllNonNull(name, studentsInfo);
        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidStudentInfo(studentsInfo), STUDENTS_INFO_CONSTRAINT);
        this.name = name;
        this.studentsInfo = studentsInfo;
    }

    /**
     * Constructs a {@code Lesson}.
     * @param name A valid name.
     * @param studentsInfo A valid list of studentInfo.
     * @param questionList A list of questions.
     */
    public Lesson(String name, UniqueList<StudentInfo> studentsInfo, UniqueList<Question> questionList) {
        requireAllNonNull(name, studentsInfo);
        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidStudentInfo(studentsInfo), STUDENTS_INFO_CONSTRAINT);
        this.name = name;
        this.studentsInfo = studentsInfo;
    }

    private UniqueList<StudentInfo> generateStudentInfo(ObservableList<Student> students) {
        UniqueList<StudentInfo> studentInfo = new UniqueStudentInfoList();
        for (Student s : students) {
            studentInfo.add(new StudentInfo(s, new Participation(), new Attendance()));
        }
        return studentInfo;
    }

    boolean isValidName(String name) {
        return name.length() > 0;
    }

    public String getName() {
        return this.name;
    }

    boolean isValidStudentInfo(UniqueList<StudentInfo> studentsInfo) {
        return studentsInfo.size() > 0;
    }

    public UniqueList<StudentInfo> getStudentsInfo() {
        return this.studentsInfo;
    }

    public ObservableList<StudentInfo> getStudentsInfoAsUnmodifiableObservableList() {
        return this.studentsInfo.asUnmodifiableObservableList();
    }

    public boolean isSame(Lesson otherLsn) {
        return otherLsn.getName().equals(getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Lesson)) {
            return false;
        }

        Lesson otherClass = (Lesson) obj;
        return otherClass.getName().equals(getName())
                && otherClass.getStudentsInfo().equals(getStudentsInfo());
    }

    @Override
    public String toString() {
        return String.format("Lesson %s", this.name);
    }
}

