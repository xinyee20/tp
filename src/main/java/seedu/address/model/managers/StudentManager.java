package seedu.address.model.managers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.group.Group;
import seedu.address.model.group.Student;
import seedu.address.model.group.UniqueStudentList;

public class StudentManager {
    private final HashMap<Group, UniqueStudentList> studentLists;
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
        Optional<UniqueStudentList> studentsOptional = Optional.of(studentLists.get(group));
        if (studentsOptional.isPresent()) {
            UniqueStudentList students = studentsOptional.get();
            students.add(student);
        }
    }

    /**
     * Checks if the specified Student exists in the Group
     * @param group
     * @param student
     * @return whether Student exists in the Group
     */
    public boolean checkIfStudentExists(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueStudentList> studentsOptional = Optional.of(studentLists.get(group));
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
    public Optional<UniqueStudentList> getStudents(Group group) {
        requireNonNull(group);
        return Optional.of(studentLists.get(group));
    }


}
