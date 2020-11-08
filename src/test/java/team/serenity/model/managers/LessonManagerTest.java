package team.serenity.model.managers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalGroups.GROUP_G01;
import static team.serenity.testutil.TypicalGroups.GROUP_G02;
import static team.serenity.testutil.TypicalLesson.LESSON_1_1;
import static team.serenity.testutil.TypicalLesson.LESSON_2_2;
import static team.serenity.testutil.TypicalLesson.LESSON_3_1;
import static team.serenity.testutil.TypicalLesson.getTypicalLessonManager;
import static team.serenity.testutil.TypicalLesson.getTypicalLessons;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.exceptions.DuplicateLessonException;
import team.serenity.model.group.exceptions.GroupNotFoundException;
import team.serenity.model.group.exceptions.LessonNotFoundException;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.LessonBuilder;

class LessonManagerTest {

    private final LessonManager lessonManager = new LessonManager();
    private final ReadOnlyLessonManager readOnlyLessonManager = new LessonManager();

    @Test
    public void constructor_noParams() {
        assertEquals(Collections.emptyMap(), this.lessonManager.getLessonMap());
    }

    @Test
    public void constructor_withParams() {
        LessonManager actual = new LessonManager(this.lessonManager);
        assertEquals(Collections.emptyMap(), actual.getLessonMap());
    }

    @Test
    public void resetData_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.lessonManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroupManager() {
        LessonManager newData = getTypicalLessonManager();
        this.lessonManager.resetData(newData);
        assertEquals(newData, this.lessonManager);
    }

    @Test
    public void targetGroupHasLesson_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.lessonManager.targetGroupHasLesson(null, null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.targetGroupHasLesson(GROUP_G01.getGroupName(), null));
        assertThrows(NullPointerException.class, () -> this.lessonManager.targetGroupHasLesson(null, LESSON_1_1));
    }

    @Test
    public void targetGroupHasLesson_targetGroupDoesNotExist_throwGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () ->
            this.lessonManager.targetGroupHasLesson(GROUP_G01.getGroupName(), LESSON_1_1));
    }

    @Test
    public void targetGroupHasLesson_targetLessonDoesNotExist_returnFalse() {
        LessonManager newData = getTypicalLessonManager();
        assertFalse(newData.targetGroupHasLesson(GROUP_G01.getGroupName(),
            new LessonBuilder().withName("1-3").build()));
    }

    @Test
    public void targetGroupHasLesson_targetLessonExist_returnTrue() {
        LessonManager newData = getTypicalLessonManager();
        assertTrue(newData.targetGroupHasLesson(GROUP_G02.getGroupName(), new LessonBuilder().withName("1-1").build()));
    }

    @Test
    public void addLessonToGroup_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.addLessonToGroup(null, null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.addLessonToGroup(GROUP_G02.getGroupName(), null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.addLessonToGroup(null, LESSON_2_2));
    }

    @Test
    public void addLessonToGroup_targetGroupDoesNotExist_throwGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () ->
            this.lessonManager.addLessonToGroup(GROUP_G02.getGroupName(), LESSON_2_2));
    }

    @Test
    public void addLessonToGroup_duplicateLesson_throwDuplicateLessonException() {
        this.lessonManager.resetData(getTypicalLessonManager());
        assertThrows(DuplicateLessonException.class, () ->
            this.lessonManager.addLessonToGroup(GROUP_G02.getGroupName(), LESSON_2_2));
    }

    @Test
    public void addLessonToGroup_validLesson() {
        this.lessonManager.resetData(getTypicalLessonManager());
        assertFalse(this.lessonManager.targetGroupHasLesson(GROUP_G02.getGroupName(), LESSON_3_1));
        this.lessonManager.addLessonToGroup(GROUP_G02.getGroupName(), LESSON_3_1);
        assertTrue(this.lessonManager.targetGroupHasLesson(GROUP_G02.getGroupName(), LESSON_3_1));
    }

    @Test
    public void deleteLessonFromGroup_nullInput_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.deleteLessonFromGroup(null, null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.deleteLessonFromGroup(GROUP_G01.getGroupName(), null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.deleteLessonFromGroup(null, LESSON_1_1));
    }

    @Test
    public void deleteLessonFromGroup_targetGroupDoesNotExist_throwGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () ->
            this.lessonManager.deleteLessonFromGroup(GROUP_G01.getGroupName(), LESSON_1_1));
    }

    @Test
    public void deleteLessonFromGroup_lessonDoesNotExist_throwLessonNotFoundException() {
        this.lessonManager.resetData(getTypicalLessonManager());
        assertThrows(LessonNotFoundException.class, () ->
            this.lessonManager.deleteLessonFromGroup(GROUP_G01.getGroupName(), LESSON_3_1));
    }

    @Test
    public void deleteLessonFromGroup_validLesson() {
        this.lessonManager.resetData(getTypicalLessonManager());
        assertTrue(this.lessonManager.targetGroupHasLesson(GROUP_G01.getGroupName(), LESSON_1_1));
        this.lessonManager.deleteLessonFromGroup(GROUP_G01.getGroupName(), LESSON_1_1);
        assertFalse(this.lessonManager.targetGroupHasLesson(GROUP_G01.getGroupName(), LESSON_1_1));
    }

    @Test
    public void setListOfLessonsToGroup_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.lessonManager.setListOfLessonsToGroup(null, null));
        assertThrows(NullPointerException.class, () -> this.lessonManager
            .setListOfLessonsToGroup(GROUP_G01.getGroupName(),
            null));
        assertThrows(NullPointerException.class, () ->
            this.lessonManager.setListOfLessonsToGroup(null, getTypicalLessons())
        );
    }

    @Test
    public void getListOfLessonsFromGroup_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.lessonManager.getListOfLessonsFromGroup(null));
    }

    @Test
    public void getListOfLessonsFromGroup_tagertGroupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () ->
            this.lessonManager.getListOfLessonsFromGroup(GROUP_G01.getGroupName()));
    }

    @Test
    public void getListOfLessonsFromGroup_validGroup() {
        this.lessonManager.resetData(getTypicalLessonManager());
        assertDoesNotThrow(() -> this.lessonManager.getListOfLessonsFromGroup(GROUP_G01.getGroupName()));
    }

    /**
     * A stub ReadOnlyLessonManager whose lesson map can violate interface constraints.
     */
    private static class LessonManagerStub implements ReadOnlyLessonManager {
        private final Map<GroupName, UniqueList<Lesson>> map = new HashMap<>();

        LessonManagerStub(Group group) {
            this.map.put(group.getGroupName(), group.getLessons());
        }

        @Override
        public Map<GroupName, UniqueList<Lesson>> getLessonMap() {
            return this.map;
        }
    }
}
