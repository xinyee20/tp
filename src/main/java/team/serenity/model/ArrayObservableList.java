package team.serenity.model;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

/**
 * A modifiable observable list to support rendering of different
 * set of information when navigating to different groups.
 */
public class ArrayObservableList<E> extends ModifiableObservableListBase<E> {

    private final List<E> delegate = new ArrayList<>();

    /**
     * Creates a generic ArrayObservableList.
     */
    public ArrayObservableList(ObservableList<E> list) {
        for (E e : list) {
            this.delegate.add(e);
        }
    }

    public E get(int index) {
        return this.delegate.get(index);
    }

    public int size() {
        return this.delegate.size();
    }

    protected void doAdd(int index, E element) {
        this.delegate.add(index, element);
    }

    protected E doSet(int index, E element) {
        return this.delegate.set(index, element);
    }

    protected E doRemove(int index) {
        return this.delegate.remove(index);
    }
}
