package seedu.address.model.group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of Lessons that enforces uniqueness between its elements and does not allow nulls.
 * A Lesson is considered unique by comparing using {@code Lesson#equal(Object)}.
 */
public class UniqueStudentInfoList implements Iterable<StudentInfo> {

    private final ObservableList<StudentInfo> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudentInfo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent student info as the given argument.
     */
    public boolean contains(StudentInfo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a student info to the list. The student info must not already exist in the list.
     */
    public void add(StudentInfo toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the student info {@code target} in the list with {@code editedStudentInfo}.
     * {@code target} must exist in the list.
     * The identity of {@code editedStudentInfo} must not be the same as another existing student info in the list.
     */
    public void setStudentInfo(StudentInfo target, StudentInfo editedStudentInfo) {
        requireAllNonNull(target, editedStudentInfo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.equals(editedStudentInfo) && contains(editedStudentInfo)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedStudentInfo);
    }

    /**
     * Removes the equivalent student info from the list. The student info must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    public void setStudentInfo(UniqueStudentInfoList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code studentInfos}.
     * {@code studentInfos} must not contain duplicate student infos.
     */
    public void setStudentInfo(List<StudentInfo> studentInfos) {
        requireAllNonNull(studentInfos);
        if (!studentInfosAreUnique(studentInfos)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(studentInfos);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<StudentInfo> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<StudentInfo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueStudentInfoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code studentInfos} contains only unique student infos.
     */
    private boolean studentInfosAreUnique(List<StudentInfo> studentInfos) {
        for (int i = 0; i < studentInfos.size() - 1; i++) {
            for (int j = i + 1; j < studentInfos.size(); j++) {
                if (studentInfos.get(i).equals(studentInfos.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
