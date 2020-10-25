package team.serenity.model.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.NotFoundException;

public interface UniqueList<T> extends Iterable<T> {

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    boolean contains(T toCheck);

    ObservableList<T> getList();

    void sort(Comparator<T> comparator);

    Stream<T> stream();

    /**
     * Returns the size of the list.
     */
    public int size();

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) throws DuplicateException;

    /**
     * Replaces the element {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The element identity of {@code edited} must not be the same as another existing element in the list.
     */
    public void setElement(T target, T edited) throws NotFoundException, DuplicateException;

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove);

    public void setElementsWithUniqueList(UniqueList<T> replacement);

    /**
     * Replaces the contents of this list with {@code elements}.
     * {@code elements} must not contain duplicate elements.
     */
    public void setElementsWithList(List<T> elements) throws DuplicateException;

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList();

    @Override
    public Iterator<T> iterator();


    @Override
    public int hashCode();

    /**
     * Returns true if {@code elements} contains only unique elements.
     */
    public boolean elementsAreUnique(List<T> elements);

}
