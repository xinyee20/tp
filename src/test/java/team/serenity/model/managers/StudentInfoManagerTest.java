package team.serenity.model.managers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalStudentInfo.JAMES_INFO;
import static team.serenity.testutil.TypicalStudentInfo.JOHN_INFO;
import static team.serenity.testutil.TypicalStudentInfo.JUNE_INFO;
import static team.serenity.testutil.TypicalStudentInfo.getTypicalStudentInfoManager;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.exceptions.DuplicateStudentInfoException;
import team.serenity.model.group.exceptions.GroupLessonPairNotFoundException;
import team.serenity.model.group.exceptions.StudentInfoNotFoundException;
import team.serenity.testutil.GroupLessonKeyBuilder;

public class StudentInfoManagerTest {

    private final StudentInfoManager studentInfoManager = new StudentInfoManager();

    @Test
    public void constructor_noParams() {
        assertEquals(Collections.emptyMap(), this.studentInfoManager.getStudentInfoMap());
    }

    @Test
    public void constructor_withParams() {
        StudentInfoManager actual = new StudentInfoManager(this.studentInfoManager);
    }

    @Test
    public void resetData_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.studentInfoManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroupManager() {
        StudentInfoManager newData = getTypicalStudentInfoManager();
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertEquals(newData, this.studentInfoManager);
    }

    @Test
    public void checkIfStudentInfoExist_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), null));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.checkIfStudentInfoExist(null, null));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.checkIfStudentInfoExist(null, JUNE_INFO));
    }

    @Test
    public void checkIfStudentInfoExist_groupLessonKeyDoesNotExist_throwGroupLessonPairNotFoundException() {
        assertThrows(GroupLessonPairNotFoundException.class, () ->
                this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JUNE_INFO));

    }

    @Test
    public void checkIfStudentInfoExist_targetStudentInfoExist_returnTrue() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertTrue(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JAMES_INFO));
    }

    @Test
    public void checkIfStudentInfoExist_targetStudentInfoDoesNotExist_returnFalse() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertFalse(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JUNE_INFO));
    }

    @Test
    public void addStudentInfoToKey_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.addStudentInfoToKey(null, null));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.addStudentInfoToKey(null, JOHN_INFO));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.addStudentInfoToKey(new GroupLessonKeyBuilder().build(), null));
    }

    @Test
    public void addStudentInfoToKey_groupLessonKeyDoesNotExist_throwDuplicateStudentInfoException() {
        assertThrows(GroupLessonPairNotFoundException.class, () ->
                this.studentInfoManager.addStudentInfoToKey(new GroupLessonKeyBuilder().build(), JOHN_INFO));
    }

    @Test
    public void addStudentInfoToKey_duplicateStudentInfo_throwDuplicateStudentInfoException() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertThrows(DuplicateStudentInfoException.class, () ->
                this.studentInfoManager.addStudentInfoToKey(new GroupLessonKeyBuilder().build(), JOHN_INFO));
    }

    @Test
    public void addStudentInfoToKey_validStudentInfo() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertFalse(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JUNE_INFO));
        this.studentInfoManager.addStudentInfoToKey(new GroupLessonKeyBuilder().build(), JUNE_INFO);
        assertTrue(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JUNE_INFO));
    }

    @Test
    public void getListOfStudentsInfoFromGroupLessonKey_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.getListOfStudentsInfoFromGroupLessonKey(null));
    }

    @Test
    public void getListOfStudentsInfoFromGroupLessonKey_invalidGroupLessonKey_throwsGroupLessonPairNotFoundException() {
        assertThrows(GroupLessonPairNotFoundException.class, () ->
                this.studentInfoManager.getListOfStudentsInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build()));
    }

    @Test
    public void getListOfStudentsInfoFromGroupLessonKey_validGroupLessonKey_throwsGroupLessonPairNotFoundException() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertDoesNotThrow(() -> this.studentInfoManager
                .getListOfStudentsInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build()));
    }

    @Test
    public void deleteStudentInfoFromGroupLessonKey_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.deleteStudentInfoFromGroupLessonKey(null, null));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.deleteStudentInfoFromGroupLessonKey(null, JOHN_INFO));
        assertThrows(NullPointerException.class, () ->
                this.studentInfoManager.deleteStudentInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build(), null));
    }

    @Test
    public void deleteStudentInfoFromGroupLessonKey_groupLessonKeyDoesNotExist_throwDuplicateStudentInfoException() {
        assertThrows(GroupLessonPairNotFoundException.class, () ->
                this.studentInfoManager
                        .deleteStudentInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build(), JOHN_INFO));
    }

    @Test
    public void deleteStudentInfoFromGroupLessonKey_studentInfoDoesNotExist_throwStudentInfoNotFoundException() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertThrows(StudentInfoNotFoundException.class, () -> this.studentInfoManager
                .deleteStudentInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build(), JUNE_INFO));
    }

    @Test
    public void deleteStudentInfoFromGroupLessonKey_validStudentInfo() {
        this.studentInfoManager.resetData(getTypicalStudentInfoManager());
        assertTrue(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JAMES_INFO));
        this.studentInfoManager.deleteStudentInfoFromGroupLessonKey(new GroupLessonKeyBuilder().build(), JAMES_INFO);
        assertFalse(this.studentInfoManager.checkIfStudentInfoExist(new GroupLessonKeyBuilder().build(), JAMES_INFO));
    }
}
