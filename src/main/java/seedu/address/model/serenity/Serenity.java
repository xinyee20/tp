package seedu.address.model.serenity;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.ReadOnlySerenity;

public class Serenity implements ReadOnlySerenity {

    private GroupList groups;

    {
        groups = new GroupList();
    }

    public Serenity() {
        groups = new GroupList();
    }

    public Serenity(ReadOnlySerenity toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void resetData(ReadOnlySerenity newData) {
        requireNonNull(newData);

        setGroups(newData.getGroupList());
    }

    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.getGroups().contains(group);
    }

    public void addGroup(Group g) {
        groups.getGroups().add(g);
    }

    //// util methods

    @Override
    public String toString() {
        return groups.getGroups().size() + " groups";
        // TODO: refine later
    }

    @Override
    public List<Group> getGroupList() {
        return groups.getGroups();
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
