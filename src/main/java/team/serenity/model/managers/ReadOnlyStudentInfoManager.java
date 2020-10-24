package team.serenity.model.managers;

import java.util.HashMap;

import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.StudentInfo;

/**
 * Unmodifiable view of a StudentInfoManager
 */
public interface ReadOnlyStudentInfoManager {
    /**
     * Returns an unmodifiable view of the StudentInfo list
     * this list will not contain any duplicate StudentInfo
     */
    public HashMap<GroupLessonKey, StudentInfo> getStudentInfoMap();
}
