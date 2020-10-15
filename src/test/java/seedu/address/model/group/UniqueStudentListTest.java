package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStudent.JAMES;
import static seedu.address.testutil.TypicalStudent.JOHN;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateStudentException;
import seedu.address.model.group.exceptions.StudentNotFoundException;

class UniqueStudentListTest {

    UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullSudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(JAMES));
    }

    @Test
    public void contains_studentNotInList_returnsFalse2() {
        uniqueStudentList.add(JOHN);
        assertFalse(uniqueStudentList.contains(JAMES));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(JAMES);
        assertTrue(uniqueStudentList.contains(JAMES));
    }

    @Test
    public void contains_studentInList_returnsTrue2() {
        uniqueStudentList.add(JOHN);
        assertTrue(uniqueStudentList.contains(JOHN));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_testCorrectness() {
        uniqueStudentList.add(JOHN);
        assertTrue(uniqueStudentList.contains(JOHN));
    }

    @Test
    public void add_testCorrectness2() {
        uniqueStudentList.add(JAMES);
        assertTrue(uniqueStudentList.contains(JAMES));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(JAMES);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(JAMES));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException2() {
        uniqueStudentList.add(JOHN);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(JOHN));
    }

    @Test
    public void setStudent_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudent(JAMES,null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.setStudent(JAMES,JAMES));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(JOHN);
        uniqueStudentList.setStudent(JOHN, JOHN);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(JOHN);
        assertEquals(expected, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_ThrowsDuplicateStudentException() {
        uniqueStudentList.add(JOHN);
        uniqueStudentList.add(JAMES);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setStudent(JAMES, JOHN));
    }

    @Test
    public void remove_nullStudent_throwsStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.remove(JOHN));
    }

    @Test
    public void remove_existingQuestion() {
        uniqueStudentList.add(JOHN);
        uniqueStudentList.remove(JOHN);
        UniqueStudentList expected = new UniqueStudentList();
        assertEquals(expected, uniqueStudentList);
    }

    @Test
    void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents(null));
    }

    @Test
    void SetStudents_testCorrectness() {
        List<Student> listOfStudents = Arrays.asList(JOHN,JAMES);
        uniqueStudentList.setStudents(listOfStudents);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(JOHN);
        expected.add(JAMES);
        assertEquals(expected, uniqueStudentList);

    }

    @Test
    void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(JOHN,JOHN);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setStudents(listWithDuplicateStudents));
    }
}
