package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.util.UniqueList;

public class StudentManager {

    private final Map<Group, UniqueList<Student>> mapToListOfStudents;

    public StudentManager() {
        this.mapToListOfStudents = new HashMap<>();
    }

    /**
     * Adds the specified {@code Student} to the specified {@code Group}.
     * @param group
     * @param student
     */
    public void addStudentToGroup(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentsOptional.isPresent()) {
            UniqueList<Student> students = studentsOptional.get();
            if (!students.contains(student)) {
                students.add(student);
            }
        }
    }

    // Student-level methods

    /**
     * Replaces listOfStudents from a particular {@code Group}
     * @param group Group of interest
     * @param students new list of students to replace with
     */
    public void addListOfStudentsToGroup(Group group, UniqueList<Student> students) {
        this.mapToListOfStudents.put(group, students);
    }

    /**
     * Checks if the specified {@code Student} exists in the {@code Group}.
     * @param group
     * @param student
     * @return whether Student exists in the Group
     */
    public boolean checkIfStudentExistsInGroup(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentsOptional.isEmpty()) {
            return false;
        } else {
            return studentsOptional.get().contains(student);
        }
    }

    /**
     * Gets listOfStudents from a particular {@code Group}.
     * @param group Group to check for
     * @return All students from a particular group
     */
    public Optional<UniqueList<Student>> getListOfStudentsFromGroup(Group group) {
        requireNonNull(group);
        return Optional.ofNullable(this.mapToListOfStudents.get(group));
    }

    /**
     * Replaces listOfStudents stored in {@code Group} with {@code newListOfStudents}.
     * @param group
     * @param newListOfStudents
     */
    public void setListOfStudentsToGroup(Group group, UniqueList<Student> newListOfStudents) {
        requireAllNonNull(group, newListOfStudents);
        this.mapToListOfStudents.put(group, newListOfStudents);
    }
}
