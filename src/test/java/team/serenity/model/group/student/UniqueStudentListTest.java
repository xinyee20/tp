package team.serenity.model.group.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.TypicalStudent.JAMES;
import static team.serenity.testutil.TypicalStudent.JOHN;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.exceptions.DuplicateStudentException;
import team.serenity.model.group.exceptions.StudentNotFoundException;


class UniqueStudentListTest {

    private UniqueStudentList uniqueStudentList = new UniqueStudentList();

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
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setElement(JAMES, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.setElement(JAMES, JAMES));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(JOHN);
        uniqueStudentList.setElement(JOHN, JOHN);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(JOHN);
        assertEquals(expected, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(JOHN);
        uniqueStudentList.add(JAMES);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setElement(JAMES, JOHN));
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
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setElementsWithList(null));
    }

    @Test
    void setStudents_testCorrectness() {
        List<Student> listOfStudents = Arrays.asList(JOHN, JAMES);
        uniqueStudentList.setElementsWithList(listOfStudents);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(JOHN);
        expected.add(JAMES);
        assertEquals(expected, uniqueStudentList);

    }

    @Test
    void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(JOHN, JOHN);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
            .setElementsWithList(listWithDuplicateStudents));
    }
}
