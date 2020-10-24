package team.serenity.model.group;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import javafx.collections.ObservableList;
import team.serenity.commons.util.XlsxUtil;
import team.serenity.model.util.UniqueList;

/**
 * Represents a tutorial Group in Serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    // Identity field
    private final String name;

    // Data fields
    private final UniqueList<Student> students;
    private final UniqueList<Lesson> lessons;

    /**
     * Constructs a {@code Group}
     *
     * @param name     A valid name.
     * @param filePath A valid filePath.
     */
    public Group(String name, String filePath) {
        requireAllNonNull(name, filePath);
        this.name = name;
        XlsxUtil util = new XlsxUtil(filePath);
        this.students = new UniqueStudentList();
        this.students.setElementsWithList(new ArrayList<>(util.readStudentsFromXlsx()));
        // TODO: implement scores data
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromXlsx(util.readStudentsFromXlsx());
        this.lessons = new UniqueLessonList();
        this.lessons.setElementsWithList(new ArrayList<>(util.readLessonsFromXlsx(studentsInfo)));
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     */
    public Group(String name, UniqueList<Student> students) {
        requireAllNonNull(name, students);
        this.name = name;
        this.students = students;
        this.lessons = new UniqueLessonList();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     * @param lessons  A list of tutorial lessons.
     */
    public Group(String name, UniqueList<Student> students, UniqueList<Lesson> lessons) {
        requireAllNonNull(name, students, lessons);
        this.name = name;
        this.students = students;
        this.lessons = lessons;
    }

    public String getName() {
        return this.name;
    }

    public UniqueList<Student> getStudents() {
        return this.students;
    }

    public ObservableList<Student> getStudentsAsUnmodifiableObservableList() {
        return this.students.asUnmodifiableObservableList();
    }

    public ObservableList<Lesson> getLessonsAsUnmodifiableObservableList() {
        return this.lessons.asUnmodifiableObservableList();
    }

    public UniqueList<Lesson> getLessons() {
        return this.lessons;
    }

    public UniqueList<Lesson> getSortedLessons() {
        this.lessons.sort(Comparator.comparing(Lesson::getName));
        return this.lessons;
    }

    /**
     * Adds a Student to a Group
     *
     * @param student Student to be added
     */
    public void addStudentToGroup(Student student) {
        addToStudentList(student);
        addToStudentListInLessons(student);
    }

    /**
     * Removes a Student from the Group.
     *
     * @param student Student to be added
     */
    public void deleteStudentFromGroup(Student student) {
        deleteStudentFromStudentListInLessons(student);
    }


    private void addToStudentList(Student student) {
        this.students.add(student);
    }


    private void addToStudentListInLessons(Student student) {
        for (Lesson lesson : this.lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueList<StudentInfo> studentInfos = lesson.getStudentsInfo();
            studentInfos.add(newStudent);
            Lesson updatedLesson = new Lesson(lesson.getName(), studentInfos);
            this.lessons.setElement(lesson, updatedLesson);
        }
    }

    private void deleteStudentFromStudentListInLessons(Student student) {
        for (Lesson lesson : this.lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueList<StudentInfo> studentInfos = lesson.getStudentsInfo();
            studentInfos.remove(newStudent);
            Lesson updatedLesson = new Lesson(lesson.getName(), studentInfos);
            this.lessons.setElement(lesson, updatedLesson);
        }
    }

    /**
     * Returns true if both groups of the same name have at least one other identity field that is the same. This
     * defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    /**
     * Returns true if both groups have the same identity and data fields. This defines a stronger notion of equality
     * between two groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Group %s", this.name);
    }


}
