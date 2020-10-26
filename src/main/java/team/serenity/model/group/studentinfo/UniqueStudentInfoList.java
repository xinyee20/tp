package team.serenity.model.group.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.DuplicateStudentInfoException;
import team.serenity.model.group.exceptions.NotFoundException;
import team.serenity.model.group.exceptions.StudentInfoNotFoundException;
import team.serenity.model.util.UniqueList;

/**
 * A list of Students Info that enforces uniqueness between its elements and does not allow nulls.
 * A Student Info is considered unique by comparing using {@code StudentInfo#equal(Object)}.
 */
public class UniqueStudentInfoList implements UniqueList<StudentInfo> {

    private final ObservableList<StudentInfo> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudentInfo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    @Override
    public ObservableList<StudentInfo> getList() {
        return this.internalList;
    }

    @Override
    public Stream<StudentInfo> stream() {
        return this.internalList.stream();
    }

    @Override
    public void sort(Comparator<StudentInfo> comparator) {
        this.internalList.sort(comparator);
    }

    /**
     * Returns true if the list contains an equivalent student info as the given argument.
     */
    @Override
    public boolean contains(StudentInfo toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a student info to the list. The student info must not already exist in the list.
     */
    @Override
    public void add(StudentInfo toAdd) throws DuplicateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentInfoException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Removes the equivalent student info from the list. The student info must exist in the list.
     */
    @Override
    public void remove(StudentInfo toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new StudentInfoNotFoundException();
        }
    }

    @Override
    public int size() {
        return this.internalList.size();
    }

    /**
     * Replaces the student info {@code target} in the list with {@code editedStudentInfo}.
     * {@code target} must exist in the list.
     * The identity of {@code editedStudentInfo} must not be the same as another existing student info in the list.
     */
    @Override
    public void setElement(StudentInfo target, StudentInfo editedStudentInfo)
        throws NotFoundException, DuplicateException {

        requireAllNonNull(target, editedStudentInfo);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new StudentInfoNotFoundException();
        }

        if (!target.equals(editedStudentInfo) && contains(editedStudentInfo)) {
            throw new DuplicateStudentInfoException();
        }

        this.internalList.set(index, editedStudentInfo);
    }

    public void setElementsWithUniqueList(UniqueList<StudentInfo> replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code studentsInfo}.
     * {@code studentsInfo} must not contain duplicate students info.
     */
    public void setElementsWithList(List<StudentInfo> studentsInfo) throws DuplicateException {
        requireAllNonNull(studentsInfo);
        if (!elementsAreUnique(studentsInfo)) {
            throw new DuplicateStudentInfoException();
        }
        this.internalList.setAll(studentsInfo);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<StudentInfo> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<StudentInfo> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentInfoList // instanceof handles nulls
                && this.internalList.equals(((UniqueStudentInfoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    /**
     * Returns true if {@code studentsInfo} contains only unique students info.
     */
    @Override
    public boolean elementsAreUnique(List<StudentInfo> studentsInfo) {
        for (int i = 0; i < studentsInfo.size() - 1; i++) {
            for (int j = i + 1; j < studentsInfo.size(); j++) {
                if (studentsInfo.get(i).equals(studentsInfo.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
