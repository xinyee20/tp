package seedu.address.model.managers;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;

/**
 * Manages tutorial groups
 */
public class GroupManager {

    private final UniqueGroupList groups;

    /**
     * Instantiates a GroupManager
     *
     * @param groups List of tutorial groups
     */
    public GroupManager(UniqueGroupList groups) {
        requireNonNull(groups);
        this.groups = groups;
    }

    /**
     * Checks whether group exists
     *
     * @param group Group to check for
     * @return Whether given group exists
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * Adds given group to the list, if it doesn't exist yet
     *
     * @param group Group to be added
     */
    public void addGroup(Group group) {
        requireNonNull(group);
        if (!hasGroup(group)) {
            groups.add(group);
        }
    }

    /**
     * Removes given group from the list
     * @param group
     */
    public void deleteGroup(Group group) {
        requireNonNull(group);
        if (hasGroup(group)) {
            groups.remove(group);
        }
    }


    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }
}
