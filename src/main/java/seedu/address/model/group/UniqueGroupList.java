package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.util.UniqueList;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls. A group is considered unique
 * by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of groups uses
 * Group#isSameGroup(Group) for equality so as to ensure that the group being added or updated is unique in terms of
 * identity in the UniqueGroupList. However, the removal of a group uses Group#equals(Object) so as to ensure that the
 * group with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group)
 */
public class UniqueGroupList implements UniqueList<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    @Override
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    @Override
    public int size() {
        return internalList.size();
    }



    @Override
    public ObservableList<Group> getList() {
        return internalList;
    }


    /**
     * Adds a group to the list. The group must not already exist in the list.
     */
    @Override
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}. {@code target} must exist in the list.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the list.
     */
    @Override
    public void setElement(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.isSameGroup(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedGroup);
    }

    /**
     * Removes the equivalent group from the list. The group must exist in the list.
     */
    @Override
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    public void sort(Comparator<Group> comparator) {
        internalList.sort(comparator);
    }

    @Override
    public void setElements(UniqueList<Group> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code groups}. {@code groups} must not contain duplicate groups.
     */
    @Override
    public void setElementsWithList(List<Group> groups) {
        requireAllNonNull(groups);
        if (!elementsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueGroupList // instanceof handles nulls
            && internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
     */
    @Override
    public boolean elementsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).isSameGroup(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
