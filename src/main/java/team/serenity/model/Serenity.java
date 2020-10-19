package team.serenity.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.util.UniqueList;

/**
 * Wraps all data at the serenity level Duplicates are not allowed (by .isSameGroup comparison)
 */
public class Serenity implements ReadOnlySerenity {

    private final UniqueList<Group> groups;

    public Serenity() {
        groups = new UniqueGroupList();
    }

    /**
     * Creates a Serenity object using the Groups in the {@code toBeCopied}
     */
    public Serenity(ReadOnlySerenity toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the group list with {@code groups}. {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setElementsWithList(groups);
    }


    /**
     * Resets the existing data of this {@code Serenity} with {@code newData}.
     */
    public void resetData(ReadOnlySerenity newData) {
        requireNonNull(newData);
        setGroups(newData.getGroupList());
    }

    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in serenity.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return this.groups.contains(group);
    }

    /**
     * Adds a group to the serenity. The group must not already exist in the serenity.
     */
    public void addGroup(Group g) {
        this.groups.add(g);
    }


    /**
     * Replaces the given group {@code group} in the list with {@code editedGroup}. {@code target} must exist in
     * serenity. The group identity of {@code editedGroup} must not be the same as another existing group in serenity.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);
        this.groups.setElement(target, editedGroup);
    }

    /**
     * Removes {@code key} from this {@code Serenity}. {@code key} must exist in serenity.
     */
    public void deleteGroup(Group key) {
        this.groups.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return this.groups.asUnmodifiableObservableList().size() + " groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return this.groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Serenity // instanceof handles nulls
            && this.groups.equals(((Serenity) other).groups));
    }

    @Override
    public int hashCode() {
        return this.groups.hashCode();
    }
}
