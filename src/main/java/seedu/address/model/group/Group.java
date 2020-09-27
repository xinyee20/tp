package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {

    private String name;

    private Set<Student> students;
    private Set<Class> classes;

    public Group(String name, Set<Student> students) {
        requireAllNonNull(name, students);
        this.name = name;
        this.students = students;
        this.classes = new HashSet<>();
    }

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

    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getClasses().equals(getClasses());
    }

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

    public String toString() {
        return name + "," + students.toString();
    }
}
