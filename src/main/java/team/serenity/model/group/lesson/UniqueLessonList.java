package team.serenity.model.group.lesson;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.DuplicateLessonException;
import team.serenity.model.group.exceptions.LessonNotFoundException;
import team.serenity.model.group.exceptions.NotFoundException;
import team.serenity.model.util.UniqueList;

/**
 * A list of Lessons that enforces uniqueness between its elements and does not allow nulls.
 * A Lesson is considered unique by comparing using {@code Lesson#equal(Object)}.
 */
public class UniqueLessonList implements UniqueList<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    @Override
    public int size() {
        return this.internalList.size();
    }

    @Override
    public ObservableList<Lesson> getList() {
        return this.internalList;
    }

    @Override
    public Stream<Lesson> stream() {
        return this.internalList.stream();
    }

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    @Override
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds a lesson to the list. The lesson must not already exist in the list.
     */
    @Override
    public void add(Lesson toAdd) throws DuplicateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code lesson}. {@code target} must exist in the list.
     * The lesson identity of {@code lesson} must not be the same as another existing lesson in the list.
     */
    public void setElement(Lesson target, Lesson editedLesson) throws NotFoundException, DuplicateException {
        requireAllNonNull(target, editedLesson);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.equals(editedLesson) && contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        this.internalList.set(index, editedLesson);
    }

    /**
     * Removes the equivalent lesson from the list. The lesson must exist in the list.
     */
    @Override
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        for (int i = 0; i < this.internalList.size(); i++) {
            if (this.internalList.get(i).isSame(toRemove)) {
                this.internalList.remove(i);
                return;
            }
        }
        throw new LessonNotFoundException();
    }

    /**
     * Replaces all the lessons from the list with a new list of lessons
     */
    @Override
    public void setElementsWithUniqueList(UniqueList<Lesson> replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code lessons}. {@code lessons} must not contain duplicate lessons.
     */
    @Override
    public void setElementsWithList(List<Lesson> lessons) throws DuplicateException {
        requireAllNonNull(lessons);
        if (!elementsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }
        this.internalList.setAll(lessons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public void sort(Comparator<Lesson> comparator) {
        this.internalList.sort(comparator);
    }


    @Override
    public Iterator<Lesson> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLessonList // instanceof handles nulls
                && this.internalList.equals(((UniqueLessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    /**
     * Returns true if {@code lessons} contains only unique lessons.
     */
    @Override
    public boolean elementsAreUnique(List<Lesson> lessons) {
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
