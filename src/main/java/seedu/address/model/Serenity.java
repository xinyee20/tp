package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;

public class Serenity implements ReadOnlySerenity {

    private final UniqueGroupList groups;

    {
        groups = new UniqueGroupList();
    }

    public Serenity() {}

    public Serenity(ReadOnlySerenity toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void resetData(ReadOnlySerenity newData) {
        requireNonNull(newData);

        setGroups(newData.getGroupList());
    }

    //// group-level operations

    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    public void addGroup(Group g) {
        groups.add(g);
    }

    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);

        groups.setGroup(target, editedGroup);
    }

    public void removeGroup(Group key) {
        groups.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return groups.asUnmodifiableObservableList().size() + " groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Serenity // instanceof handles nulls
            && groups.equals(((Serenity) other).groups));
    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }
}
