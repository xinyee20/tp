package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.exceptions.GroupNotFoundException;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.util.UniqueList;

public class LessonManager implements ReadOnlyLessonManager {

    private final Map<GroupName, UniqueList<Lesson>> mapToListOfLessons;

    /**
     * Instantiates a new LessonManager.
     */
    public LessonManager() {
        this.mapToListOfLessons = new HashMap<>();
    }

    /**
     * Creates a LessonManager using the Lesson in the {@code toBeCopied}
     * @param toBeCopied
     */
    public LessonManager(ReadOnlyLessonManager toBeCopied) {
        this.mapToListOfLessons = new HashMap<>();
        resetData(toBeCopied);
    }

    // Methods that overrides the whole Lesson Map

    /**
     * Replaces the contents of the Lesson map with {@code newLessonMap}.
     * {@code newLessonMap} must not contain duplicate groups.
     */
    public void setLessons(Map<GroupName, UniqueList<Lesson>> newLessonMap) {
        this.mapToListOfLessons.clear();
        this.mapToListOfLessons.putAll(newLessonMap);
    }

    /**
     * Resets the existing data of this {@code LessonManager} with {@code newData}
     */
    public void resetData(ReadOnlyLessonManager newData) {
        requireAllNonNull(newData);
        setLessons(newData.getLessonMap());
    }

    /**
     * Returns the map of lessons as an unmodifiable map.
     */
    @Override
    public Map<GroupName, UniqueList<Lesson>> getLessonMap() {
        return Collections.unmodifiableMap(this.mapToListOfLessons);
    }

    // Lesson-level operations

    /**
     * Checks if {@code targetLesson} exists for a specified (@code targetGroup).
     *
     * @param targetGroup group to search for the lesson name in
     * @param targetLesson lesson name to check for
     * @return true if the given lesson name exists for the specified group.
     */
    public boolean ifTargetGroupHasLessonName(GroupName targetGroup, LessonName targetLesson) {
        requireAllNonNull(targetGroup, targetLesson);
        if (this.mapToListOfLessons.containsKey(targetGroup)) {
            return this.mapToListOfLessons.get(targetGroup).stream()
                .anyMatch(lesson -> lesson.getLessonName().equals(targetLesson));
        }
        return false;
    }

    /**
     * Checks if a Lesson exists for a specified group.
     *
     * @param targetGroup Group to search for the lesson in
     * @param targetLesson Lesson to check for
     * @return whether the given lesson for the specified group exist.
     */
    public boolean targetGroupHasLesson(GroupName targetGroup, Lesson targetLesson) throws GroupNotFoundException {
        requireAllNonNull(targetGroup, targetLesson);
        Optional<UniqueList<Lesson>> uniqueList = Optional.ofNullable(this.mapToListOfLessons.get(targetGroup));
        if (!uniqueList.isEmpty()) {
            return uniqueList.get().contains(targetLesson);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Adds a specified {@code Lesson} to a {@code Group}.
     * @param group Group to add the lesson to
     * @param lesson Lesson to check add
     */
    public void addLessonToGroup(GroupName group, Lesson lesson) throws GroupNotFoundException {
        requireAllNonNull(group, lesson);
        Optional<UniqueList<Lesson>> lessonList = Optional.ofNullable(this.mapToListOfLessons.get(group));
        if (!lessonList.isEmpty()) {
            lessonList.get().add(lesson);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Adds a specified {@code UniqueList<Lesson>} to a {@code Group}.
     * @param group Group to add the lessons to
     * @param lessons Lesson to check add
     */
    public void addNewMapping(GroupName group, UniqueList<Lesson> lessons) {
        requireAllNonNull(group, lessons);
        this.mapToListOfLessons.put(group, lessons);
    }

    /**
     * Deletes a specified {@code Lesson} from a {@code Group}
     * @param targetGroup Group to delete the lesson from
     * @param targetLesson Lesson to check delete
     * @throws GroupNotFoundException
     */
    public void deleteLessonFromGroup(GroupName targetGroup, Lesson targetLesson) throws GroupNotFoundException {
        requireAllNonNull(targetGroup, targetLesson);
        Optional<UniqueList<Lesson>> lessonList = Optional.ofNullable(this.mapToListOfLessons.get(targetGroup));
        if (!lessonList.isEmpty()) {
            lessonList.get().remove(targetLesson);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Delete all lessons in the group.
     */
    public void deleteAllLessonsFromGroup(Group group) {
        requireNonNull(group);
        this.mapToListOfLessons.remove(group.getGroupName());
    }

    /**
     * Adds a specified {@code UniqueList<Lesson>} to a {@code Group}.
     * @param group
     * @param lessons
     */
    public void addListOfLessonsToGroup(Group group, UniqueList<Lesson> lessons) {
        this.mapToListOfLessons.put(group.getGroupName(), lessons);
    }

    /**
     * Replaces listOfLessons stored in {@code Group} with {@code newListOfLessons}
     * @param group
     * @param newListOfLessons
     */
    public void setListOfLessonsToGroup(GroupName group, UniqueList<Lesson> newListOfLessons) {
        requireAllNonNull(group, newListOfLessons);
        this.mapToListOfLessons.put(group, newListOfLessons);
    }

    public UniqueList<Lesson> getListOfLessonsFromGroup(GroupName group) throws GroupNotFoundException {
        requireAllNonNull(group);
        Optional<UniqueList<Lesson>> lessonList = Optional.ofNullable(this.mapToListOfLessons.get(group));
        if (lessonList.isPresent()) {
            return lessonList.get();
        } else {
            throw new GroupNotFoundException();
        }
    }

    //util methods

    @Override
    public int hashCode() {
        return this.mapToListOfLessons.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this //short Circuit if same Object
                || (obj instanceof LessonManager
                && this.mapToListOfLessons.equals(((LessonManager) obj).mapToListOfLessons));

    }

    @Override
    public String toString() {
        return "LessonManager : \n";
    }
}
