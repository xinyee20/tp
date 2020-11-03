package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;

/**
 * Manages tutorial groups.
 */
public class GroupManager implements ReadOnlyGroupManager {

    private final UniqueList<Group> listOfGroups;

    /**
     * Instantiates a new QuestionManager.
     */
    public GroupManager() {
        this.listOfGroups = new UniqueGroupList();
    }

    /**
     * Creates a GroupManager using the Group in the {@code toBeCopied}
     * @param toBeCopied
     */
    public GroupManager(ReadOnlyGroupManager toBeCopied) {
        this.listOfGroups = new UniqueGroupList();
        resetData(toBeCopied);
    }

    public GroupManager(UniqueList<Group> groups) {
        this.listOfGroups = groups;
    }

    // Methods that overrides the whole group list

    /**
     * Replaces the contents of the Group list with {@code newListOfGroups}.
     * {@code newListOfGroups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> newListOfGroups) {
        this.listOfGroups.setElementsWithList(newListOfGroups);
    }

    /**
     * Resets the existing data of this {@code GroupManager} with {@code newData}
     */
    public void resetData(ReadOnlyGroupManager newData) {
        requireNonNull(newData);
        setGroups(newData.getListOfGroups());
    }

    /**
     * Returns the list of groups as an unmodifiable list.
     */
    public ObservableList<Group> getListOfGroups() {
        return this.listOfGroups.asUnmodifiableObservableList();
    }

    // Group-level operations

    public boolean isEmpty() {
        return this.listOfGroups.size() == 0;
    }

    /**
     * Returns true if the group manager contains an equivalent group name as the given argument.
     */
    public boolean hasGroupName(GroupName toCheck) {
        requireNonNull(toCheck);
        return this.listOfGroups.stream().anyMatch(grp -> grp.getGroupName().equals(toCheck));
    }

    /**
     * Checks whether the group is valid to add to Group Manager.
     * A group is valid if it has a unique {@code GroupName} and none of the students from the group
     * exists in other existing groups in the Group Manager.
     *
     * @param target Group to check for
     * @return Whether given group is valid to be added to the Group Manager
     */
    public boolean isValidGroupToAdd(Group target) {
        requireNonNull(target);
        for (Group existingGroup : listOfGroups) {
            if (existingGroup.getGroupName().equals(target.getGroupName()) // Checks if group name is unique
                || hasAtLeast1SameStudent(existingGroup, target)) { // Checks if students are unique
                return false;
            }
        }
        return true;
    }

    private boolean hasAtLeast1SameStudent(Group existingGroup, Group target) {
        for (Student newStudent : target.getStudents()) {
            if (existingGroup.getStudents().contains(newStudent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes Student from a specified Group
     */
    public void deleteStudentFromGroup(Group group, Student student) {
        for (Group existingGroup: listOfGroups) {
            if (existingGroup.equals(group)) {
                group.deleteStudentFromGroup(student);
            }
        }
    }

    public Stream<Group> getStream() {
        return this.listOfGroups.stream();
    }

    /**
     * Adds given group to the list, if it doesn't exist yet.
     *
     * @param group Group to be added
     */
    public void addGroup(Group group) {
        requireNonNull(group);
        if (isValidGroupToAdd(group)) {
            this.listOfGroups.add(group);
        }
    }

    /**
     * Deletes a specified {@code Group} from the list.
     *
     * @param group
     */
    public void deleteGroup(Group group) {
        requireNonNull(group);
        this.listOfGroups.remove(group);
    }

    //util Methods

    @Override
    public int hashCode() {
        return this.listOfGroups.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this //short circuit if same Object
                || (obj instanceof GroupManager
                && this.listOfGroups.equals(((GroupManager) obj).listOfGroups));
    }

    @Override
    public String toString() {
        return "GroupManager : \n"
                + this.listOfGroups.getList().stream().map(Group::toString).collect(Collectors.joining("\n"))
                + "\n Total number of groups: " + this.listOfGroups.size();
    }
}
