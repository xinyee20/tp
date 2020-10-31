package team.serenity.model.group.student;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.DuplicateStudentException;
import team.serenity.model.group.exceptions.NotFoundException;
import team.serenity.model.group.exceptions.StudentNotFoundException;
import team.serenity.model.util.UniqueList;

/**
 * A list of Students that enforces uniqueness between its elements and does not allow nulls.
 * A Student is considered unique by comparing using {@code Student#equal(Object)}.
 */
public class UniqueStudentList implements UniqueList<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();
    private final ObservableList<Student> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    @Override
    public ObservableList<Student> getList() {
        return this.internalList;
    }

    @Override
    public int size() {
        return this.internalList.size();
    }

    @Override
    public void sort(Comparator<Student> comparator) {
        this.internalList.sort(comparator);
    }

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    @Override
    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSameStudent);
    }

    /**
     * Adds a student to the list. The student must not already exist in the list.
     */
    @Override
    public void add(Student toAdd) throws DuplicateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the student {@code target} in the list with {@code student}. {@code target} must exist in the list.
     * The student identity of {@code student} must not be the same as another existing student in the list.
     */
    @Override
    public void setElement(Student target, Student editedStudent) throws NotFoundException, DuplicateException {
        requireAllNonNull(target, editedStudent);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!target.equals(editedStudent) && contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        this.internalList.set(index, editedStudent);
    }

    /**
     * Removes the equivalent student from the list. The student must exist in the list.
     */
    @Override
    public void remove(Student toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Replaces all the students from the list with a new list of students
     */
    @Override
    public void setElementsWithUniqueList(UniqueList<Student> replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code students}. {@code students} must not contain duplicate students.
     */
    @Override
    public void setElementsWithList(List<Student> students) throws DuplicateException {
        requireNonNull(students);
        if (!elementsAreUnique(students)) {
            throw new DuplicateStudentException();
        }
        this.internalList.setAll(students);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Student> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Student> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentList // instanceof handles nulls
                && this.internalList.equals(((UniqueStudentList) other).internalList));
    }

    @Override
    public Stream<Student> stream() {
        return this.internalList.stream();
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    /**
     * Returns true if {@code students} contains only unique students.
     */
    public boolean elementsAreUnique(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).equals(students.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
