package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a tutorial Group in serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    // Identity field
    private String name;

    // Data fields
    private Set<Student> students;
    private Set<Class> classes;

    /**
     * Constructs a {@code Group}.
     *
     * @param name A valid name.
     * @param students A list of students.
     */
    public Group(String name, Set<Student> students) {
        requireAllNonNull(name, students);
        this.name = name;
        this.students = students;
        this.classes = new HashSet<>();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name A valid name.
     * @param students A list of students.
     * @param classes A list of tutorial classes.
     */
    public Group(String name, Set<Student> students, Set<Class> classes) {
        requireAllNonNull(name, students, classes);
        this.name = name;
        this.students = students;
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public Set<Class> getClasses() {
        return Collections.unmodifiableSet(classes);
    }

    /**
     * Returns true if both groups of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getClasses().equals(getClasses());
    }

    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two groups.
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
            && otherGroup.getClasses().equals(getClasses());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, students, classes);
    }

    @Override
    public String toString() {
        return name + "," + students.toString();
    }
}
