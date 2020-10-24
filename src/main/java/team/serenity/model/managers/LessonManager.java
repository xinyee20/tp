package team.serenity.model.managers;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Optional;

import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.util.UniqueList;

public class LessonManager {
    private final HashMap<Group, UniqueList<Lesson>> mapToListOfLessons;

    public LessonManager() {
        this.mapToListOfLessons = new HashMap<>();
    }

    /**
     * Adds a specified {@code Lesson} to a {@code Group}.
     * @param group
     * @param lesson
     */
    public void addLessonToGroup(Group group, Lesson lesson) {
        requireAllNonNull(group, lesson);
        UniqueList<Lesson> lessonList = this.mapToListOfLessons.get(group);
        if (lessonList != null) {
            if (!lessonList.contains(lesson)) {
                lessonList.add(lesson);
            }
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

    public Optional<UniqueList<Lesson>> getListOfLessonsFromGroup(Group group) {
        return Optional.ofNullable(this.mapToListOfLessons.get(group));
    }
}
