package team.serenity.model.managers;

import java.util.Map;

import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;

/**
 * Unmodifiable view of a StudentInfoManager
 */
public interface ReadOnlyStudentInfoManager {
    /**
     * Returns an unmodifiable view of the StudentInfo list
     * this list will not contain any duplicate StudentInfo
     */
    public Map<GroupLessonKey, UniqueList<StudentInfo>> getStudentInfoMap();
}
