package team.serenity.model.managers;

import java.util.Map;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;

/**
 * Unmodifiable view of a StudentManager
 */
public interface ReadOnlyStudentManager {
    /**
     * Returns an Unmodifiable view of the Student HashMap
     * this Hashmap will not contain any duplicate Students
     */
    public Map<GroupName, UniqueList<Student>> getStudentMap();

}
