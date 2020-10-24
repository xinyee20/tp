package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.group.UniqueGroupList;
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

    /**
     * Checks whether group exists.
     *
     * @param target Group to check for
     * @return Whether given group exists
     */
    public boolean hasGroup(Group target) {
        requireNonNull(target);
        for (Group group : listOfGroups) {
            if (group.getName().equals(target.getName()) || hasAtLeast1SameStudent(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasGroup() {
        return this.listOfGroups.size() > 0;
    }

    // TODO: improve this method's efficiency
    private boolean hasAtLeast1SameStudent(Group target) {
        for (Student targetStudent : target.getStudents()) {
            for (Group group : listOfGroups) {
                for (Student groupStudent : group.getStudents()) {
                    if (groupStudent.getName().equals(targetStudent.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        if (!hasGroup(group)) {
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
