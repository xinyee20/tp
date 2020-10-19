package seedu.address.model.managers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.group.Group;
import seedu.address.model.group.Student;
import seedu.address.model.util.UniqueList;

public class StudentManager {
    private final HashMap<Group, UniqueList<Student>> studentLists;
    public StudentManager() {
        studentLists = new HashMap<>();
    }

    /**
     * Adds the Student to the specified Group
     * @param group
     * @param student
     */
    public void addStudent(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(studentLists.get(group));
        if (studentsOptional.isPresent()) {
            UniqueList<Student> students = studentsOptional.get();
            students.add(student);
        }
    }

    public void addGroup(Group group, UniqueList<Student> students) {
        studentLists.put(group, students);
    }

    /**
     * Checks if the specified Student exists in the Group
     * @param group
     * @param student
     * @return whether Student exists in the Group
     */
    public boolean checkIfStudentExists(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(studentLists.get(group));
        if (studentsOptional.isEmpty()) {
            return false;
        } else {
            return studentsOptional.get().contains(student);
        }
    }

    /**
     * Gets all Students from a particular Group
     * @param group Group to check for
     * @return All students from a particular group
     */
    public Optional<UniqueList<Student>> getStudents(Group group) {
        requireNonNull(group);
        return Optional.ofNullable(studentLists.get(group));
    }

    /**
     * Replaces Group's students with a new set of students
     * @param group
     * @param students
     */
    public void setGroup(Group group, UniqueList<Student> students) {
        requireAllNonNull(group, students);
        studentLists.put(group, students);
    }
}
