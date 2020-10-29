package team.serenity.model.managers;


import java.util.Map;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.util.UniqueList;

/**
 * Unmodifiable view of a LessonManager
 */
public interface ReadOnlyLessonManager {
    /**
     * Returns an unmodifiable view of the Lesson list.
     * This list will not contain any duplicate lesson.
     * @return
     */
    public Map<GroupName, UniqueList<Lesson>> getLessonMap();


}
