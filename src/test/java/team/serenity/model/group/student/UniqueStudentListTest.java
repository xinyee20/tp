package team.serenity.model.group.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.TypicalStudent.GEORGE;
import static team.serenity.testutil.TypicalStudent.HELENE;

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
        assertFalse(uniqueStudentList.contains(HELENE));
    }

    @Test
    public void contains_studentNotInList_returnsFalse2() {
        uniqueStudentList.add(GEORGE);
        assertFalse(uniqueStudentList.contains(HELENE));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(HELENE);
        assertTrue(uniqueStudentList.contains(HELENE));
    }

    @Test
    public void contains_studentInList_returnsTrue2() {
        uniqueStudentList.add(GEORGE);
        assertTrue(uniqueStudentList.contains(GEORGE));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_testCorrectness() {
        uniqueStudentList.add(GEORGE);
        assertTrue(uniqueStudentList.contains(GEORGE));
    }

    @Test
    public void add_testCorrectness2() {
        uniqueStudentList.add(HELENE);
        assertTrue(uniqueStudentList.contains(HELENE));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(HELENE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(HELENE));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException2() {
        uniqueStudentList.add(GEORGE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(GEORGE));
    }

    @Test
    public void setStudent_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setElement(HELENE, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.setElement(HELENE, HELENE));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(GEORGE);
        uniqueStudentList.setElement(GEORGE, GEORGE);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(GEORGE);
        assertEquals(expected, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(GEORGE);
        uniqueStudentList.add(HELENE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setElement(HELENE, GEORGE));
    }

    @Test
    public void remove_nullStudent_throwsStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.remove(GEORGE));
    }

    @Test
    public void remove_existingQuestion() {
        uniqueStudentList.add(GEORGE);
        uniqueStudentList.remove(GEORGE);
        UniqueStudentList expected = new UniqueStudentList();
        assertEquals(expected, uniqueStudentList);
    }

    @Test
    void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setElementsWithList(null));
    }

    @Test
    void setStudents_testCorrectness() {
        List<Student> listOfStudents = Arrays.asList(GEORGE, HELENE);
        uniqueStudentList.setElementsWithList(listOfStudents);
        UniqueStudentList expected = new UniqueStudentList();
        expected.add(GEORGE);
        expected.add(HELENE);
        assertEquals(expected, uniqueStudentList);

    }

    @Test
    void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(GEORGE, GEORGE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
            .setElementsWithList(listWithDuplicateStudents));
    }
}
