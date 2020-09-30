package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.util.CsvUtil;


/**
 * Represents a tutorial Group in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Group {

    // Identity field
    private String name;

    // Data fields
    private Set<Student> students;
    private Set<Lesson> lessons;

    /**
     * Constructs a {@code Group}
     *
     * @param name     A valid name.
     * @param filePath A valid filePath.
     */
    public Group(String name, Path filePath) {
        requireAllNonNull(name, filePath);
        this.name = name;
        CsvUtil util = new CsvUtil(filePath);
        students = util.readStudentsFromCsv();
        //todo: implement studentInfo data
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromCsv(students);
        lessons = util.readLessonsFromCsv(studentsInfo);
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     */
    public Group(String name, Set<Student> students) {
        requireAllNonNull(name, students);
        this.name = name;
        this.students = students;
        this.lessons = new HashSet<>();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     * @param classes  A list of tutorial classes.
     */
    public Group(String name, Set<Student> students, Set<Lesson> classes) {
        requireAllNonNull(name, students, classes);
        this.name = name;
        this.students = students;
        this.lessons = classes;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    public Set<Lesson> getSortedLessons() {
        TreeSet<Lesson> sortedSet = new TreeSet<>(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        sortedSet.addAll(lessons);
        return sortedSet;
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
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, students, lessons);
    }

    @Override
    public String toString() {
        return name + "," + students.toString();
    }
}
