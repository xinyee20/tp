package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.group.exceptions.DuplicateException;
import seedu.address.model.group.exceptions.NotFoundException;

public class UniqueList<T extends UniqueItem> implements Iterable<T> {
    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    public void sort(Comparator<T> cmp) {
        internalList.sort(cmp);
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The element identity of {@code edited} must not be the same as another existing element in the list.
     */
    public void setElement(T target, T edited) {
        CollectionUtil.requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NotFoundException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new NotFoundException();
        }
    }

    public void setElements(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code elements}.
     * {@code elements} must not contain duplicate elements.
     */
    public void setElements(List<T> elements) {
        CollectionUtil.requireAllNonNull(elements);
        if (!elementsAreUnique(elements)) {
            throw new DuplicateException();
        }

        internalList.setAll(elements);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueList)) {
            return false;
        }

        @SuppressWarnings("unchecked") UniqueList<T> otherList = (UniqueList<T>) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code elements} contains only unique elements.
     */
    private boolean elementsAreUnique(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(i).isSame(elements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
