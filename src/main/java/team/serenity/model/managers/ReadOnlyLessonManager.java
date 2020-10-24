package team.serenity.model.managers;

import java.util.HashMap;

import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;

/**
 * Unmodifiable view of a LessonManager
 */
public interface ReadOnlyLessonManager {
    /**
     * Returns an unmodifiable view of the Lesson list.
     * This list will not contain any duplicate lesson.
     */
    public HashMap<Group, Lesson> getLessonMap();


}
