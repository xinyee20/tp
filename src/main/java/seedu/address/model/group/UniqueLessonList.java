package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateLessonException;
import seedu.address.model.group.exceptions.LessonNotFoundException;

/**
 * A list of Lessons that enforces uniqueness between its elements and does not allow nulls.
 * A Lesson is considered unique by comparing using {@code Lesson#equal(Object)}.
 */
public class UniqueLessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds a lesson to the list. The lesson must not already exist in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code lesson}. {@code target} must exist in the list.
     * The lesson identity of {@code lesson} must not be the same as another existing lesson in the list.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.equals(editedLesson) && contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        internalList.set(index, editedLesson);
    }

    /**
     * Removes the equivalent lesson from the list. The lesson must exist in the list.
     */
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSame(toRemove)) {
                internalList.remove(i);
                return;
            }
        }
        throw new LessonNotFoundException();
    }

    /**
     * Replaces all the lessons from the list with a new list of lessons
     */
    public void lessons(UniqueLessonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code lessons}. {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }
        internalList.setAll(lessons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void sort(Comparator<Lesson> comparator) {
        internalList.sort(comparator);
    }


    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLessonList // instanceof handles nulls
                && internalList.equals(((UniqueLessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code lessons} contains only unique lessons.
     */
    private boolean lessonsAreUnique(List<Lesson> lessons) {
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).equals(lessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
