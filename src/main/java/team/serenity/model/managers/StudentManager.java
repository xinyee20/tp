package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.exceptions.GroupNotFoundException;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;

public class StudentManager implements ReadOnlyStudentManager {

    private final Map<GroupName, UniqueList<Student>> mapToListOfStudents;

    /**
     * Instantiates a new LessonManager
     */
    public StudentManager() {
        this.mapToListOfStudents = new HashMap<>();
    }

    /**
     * Creates a LessonManager using the Lesson in the {@code toBeCopied}
     */
    public StudentManager(ReadOnlyStudentManager toBeCopied) {
        this.mapToListOfStudents = new HashMap<>();
        resetData(toBeCopied);
    }

    // Methods that overrides the whole student map

    /**
     * Replaces the contents of the student map with {@code newStudentMap}.
     * {@code newStudentMap} must not contain duplicate students.
     */
    public void setStudents(Map<GroupName, UniqueList<Student>> newStudentMap) {
        this.mapToListOfStudents.clear();
        this.mapToListOfStudents.putAll(newStudentMap);
    }

    /**
     * Resets existing data of this {@code StudentManager} with {@code newData}
     */
    public void resetData(ReadOnlyStudentManager newData) {
        requireNonNull(newData);
        setStudents(newData.getStudentMap());
    }

    @Override
    public Map<GroupName, UniqueList<Student>> getStudentMap() {
        return this.mapToListOfStudents;
    }

    //Student-level methods

    /**
     * Adds the specified {@code Student} to the specified {@code Group}.
     * @param group
     * @param student
     */
    public void addStudentToGroup(GroupName group, Student student) throws GroupNotFoundException {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentsOptional.isPresent()) {
            studentsOptional.get().add(student);
        } else {
            throw new GroupNotFoundException();
        }
    }

    // Student-level methods

    /**
     * Replaces listOfStudents from a particular {@code Group}
     * @param group Group of interest
     * @param students new list of students to replace with
     */
    public void addListOfStudentsToGroup(GroupName group, UniqueList<Student> students) {
        requireAllNonNull(group, students);
        this.mapToListOfStudents.put(group, students);
    }

    /**
     * Returns true if the student manager contains an equivalent student as the given argument.
     */
    public boolean hasStudent(Student toCheck) {
        requireNonNull(toCheck);
        return this.mapToListOfStudents.values().stream().anyMatch(students -> students.contains(toCheck));
    }

    /**
     * Checks if the specified {@code Student} exists in the {@code Group}.
     * @param group
     * @param student
     * @return whether Student exists in the Group
     */
    public boolean checkIfStudentExistsInGroup(GroupName group, Student student) throws GroupNotFoundException {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentsOptional = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentsOptional.isPresent()) {
            return studentsOptional.get().contains(student);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Gets listOfStudents from a particular {@code Group}.
     * @param group Group to check for
     * @return All students from a particular group
     */
    public UniqueList<Student> getListOfStudentsFromGroup(GroupName group) throws GroupNotFoundException {
        requireNonNull(group);
        Optional<UniqueList<Student>> studentList = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentList.isPresent()) {
            return studentList.get();
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Replaces listOfStudents stored in {@code Group} with {@code newListOfStudents}.
     * @param group
     * @param newListOfStudents
     */
    public void setListOfStudentsToGroup(GroupName group, UniqueList<Student> newListOfStudents) {
        requireAllNonNull(group, newListOfStudents);
        this.mapToListOfStudents.put(group, newListOfStudents);
    }

    /**
     * Deletes a student from the specified group.
     * @param group group to delete the student from
     * @param student student to be deleted
     * @throws GroupNotFoundException
     */
    public void deleteStudentFromGroup(GroupName group, Student student) throws GroupNotFoundException {
        requireAllNonNull(group, student);
        Optional<UniqueList<Student>> studentList = Optional.ofNullable(this.mapToListOfStudents.get(group));
        if (studentList.isPresent()) {
            studentList.get().remove(student);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Delete all students in the group.
     */
    public void deleteAllStudentsFromGroup(Group group) {
        requireNonNull(group);
        this.mapToListOfStudents.remove(group.getGroupName());
    }

    //util methods
    @Override
    public int hashCode() {
        return this.mapToListOfStudents.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this //short Circuit if same object
                || (obj instanceof StudentManager
                && this.mapToListOfStudents.equals(((StudentManager) obj).mapToListOfStudents));
    }

    @Override
    public String toString() {
        return "StudentManager : \n";
    }


}
