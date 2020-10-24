package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.util.UniqueList;

/**
 * Manages tutorial groups.
 */
public class GroupManager {

    private final UniqueList<Group> listOfGroups;

    /**
     * Instantiates a GroupManager.
     *
     * @param listOfGroups List of tutorial groups
     */
    public GroupManager(UniqueList<Group> listOfGroups) {
        requireNonNull(listOfGroups);
        this.listOfGroups = listOfGroups;
    }

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
        if (hasGroup(group)) {
            this.listOfGroups.remove(group);
        }
    }

    public ObservableList<Group> getListOfGroups() {
        return this.listOfGroups.asUnmodifiableObservableList();
    }
}
