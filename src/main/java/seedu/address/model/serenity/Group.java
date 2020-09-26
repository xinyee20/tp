package seedu.address.model.serenity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

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

    public String toString() {
        return name + "," + students.toString();
    }
}
