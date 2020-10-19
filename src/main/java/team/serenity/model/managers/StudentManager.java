package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.util.UniqueList;

public class StudentManager {

    private final HashMap<Group, UniqueList<Student>> mapToListOfStudents;

    public StudentManager() {
        this.mapToListOfStudents = new HashMap<>();
    }

    /**
     * Adds the Student to the specified Group
     * @param group
     * @param student
     */
    public void addStudentToGroup(Group group, Student student) {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentsOptional.isPresent()) {
            UniqueList<Student> students = studentsOptional.get();
            students.add(student);
        }
    }

    public void addListOfStudentsToGroup(Group group, UniqueList<Student> students) {
        this.mapToListOfStudents.put(group, students);
    }

    /**
     * Checks if the specified Student exists in the Group
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
     * Gets all Students from a particular Group
     * @param group Group to check for
     * @return All students from a particular group
     */
    public Optional<UniqueList<Student>> getListOfStudentsFromGroup(Group group) {
        requireNonNull(group);
        return Optional.ofNullable(this.mapToListOfStudents.get(group));
    }

    /**
     * Replaces Group's students with a new set of students
     * @param group
     * @param listOfStudents
     */
    public void setListOfStudentsToGroup(Group group, UniqueList<Student> listOfStudents) {
        requireAllNonNull(group, listOfStudents);
        this.mapToListOfStudents.put(group, listOfStudents);
    }
}
