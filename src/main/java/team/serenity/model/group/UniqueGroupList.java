package team.serenity.model.group;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.DuplicateGroupException;
import team.serenity.model.group.exceptions.GroupNotFoundException;
import team.serenity.model.group.exceptions.NotFoundException;
import team.serenity.model.util.UniqueList;

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
        return this.internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Converts to stream
     */
    @Override
    public Stream<Group> stream() {
        return internalList.stream();
    }

    @Override
    public int size() {
        return this.internalList.size();
    }



    @Override
    public ObservableList<Group> getList() {
        return this.internalList;
    }


    /**
     * Adds a group to the list. The group must not already exist in the list.
     *
     * @throws DuplicateException if the group already exists.
     */
    @Override
    public void add(Group toAdd) throws DuplicateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}. {@code target} must exist in the list.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the list.
     */
    @Override
    public void setElement(Group target, Group editedGroup) throws NotFoundException, DuplicateException {
        requireAllNonNull(target, editedGroup);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.isSameGroup(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        this.internalList.set(index, editedGroup);
    }

    /**
     * Removes the equivalent group from the list. The group must exist in the list.
     */
    @Override
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    public void sort(Comparator<Group> comparator) {
        this.internalList.sort(comparator);
    }

    @Override
    public void setElementsWithUniqueList(UniqueList<Group> replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code groups}. {@code groups} must not contain duplicate groups.
     */
    @Override
    public void setElementsWithList(List<Group> groups) throws DuplicateException {
        requireAllNonNull(groups);
        if (!elementsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }
        this.internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Group> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueGroupList // instanceof handles nulls
            && this.internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
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
