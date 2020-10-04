package seedu.address.model;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * A modifiable observable list to support rendering of different
 * set of information when navigating to different groups
 */
public class ArrayObservableList<E> extends ModifiableObservableListBase<E> {

    private final List<E> delegate = new ArrayList<>();

    public ArrayObservableList(ObservableList<E> list) {
        for (E e : list) {
            delegate.add(e);
        }
    }

    public E get(int index) {
        return delegate.get(index);
    }

    public int size() {
        return delegate.size();
    }

    protected void doAdd(int index, E element) {
        delegate.add(index, element);
    }

    protected E doSet(int index, E element) {
        return delegate.set(index, element);
    }

    protected E doRemove(int index) {
        return delegate.remove(index);
    }
}
