package team.serenity.model.managers;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.exceptions.GroupNotFoundException;
import team.serenity.model.util.UniqueList;

public class LessonManager implements ReadOnlyLessonManager {

    private final Map<Group, UniqueList<Lesson>> mapToListOfLessons;

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
    public void setLessons(Map<Group, UniqueList<Lesson>> newLessonMap) {
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
    public Map<Group, UniqueList<Lesson>> getLessonMap() {
        return Collections.unmodifiableMap(this.mapToListOfLessons);
    }

    // Lesson-level operations

    /**
     * Checks if a Lesson exists for a specified group.
     *
     * @param targetGroup Group to search for the lesson in
     * @param targetLesson Lesson to check for
     * @return whether the given lesson for the specified group exist.
     */
    public boolean targetGroupHasLesson(Group targetGroup, Lesson targetLesson) throws GroupNotFoundException {
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
    public void addLessonToGroup(Group group, Lesson lesson) throws GroupNotFoundException {
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
    public void addNewMapping(Group group, UniqueList<Lesson> lessons) {
        requireAllNonNull(group, lessons);
        this.mapToListOfLessons.put(group, lessons);
    }

    /**
     * Deletes a specified {@code Lesson} from a {@code Group}
     * @param targetGroup Group to delete the lesson from
     * @param targetLesson Lesson to check delete
     * @throws GroupNotFoundException
     */
    public void deleteLessonFromGroup(Group targetGroup, Lesson targetLesson) throws GroupNotFoundException {
        requireAllNonNull(targetGroup, targetLesson);
        Optional<UniqueList<Lesson>> lessonList = Optional.ofNullable(this.mapToListOfLessons.get(targetGroup));
        if (!lessonList.isEmpty()) {
            lessonList.get().remove(targetLesson);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Replaces listOfLessons stored in {@code Group} with {@code newListOfLessons}
     * @param group
     * @param newListOfLessons
     */
    public void setListOfLessonsToGroup(Group group, UniqueList<Lesson> newListOfLessons) {
        requireAllNonNull(group, newListOfLessons);
        this.mapToListOfLessons.put(group, newListOfLessons);
    }

    public UniqueList<Lesson> getListOfLessonsFromGroup(Group group) throws GroupNotFoundException {
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
