package team.serenity.model.managers;

import java.util.Map;

import team.serenity.model.group.Group;
import team.serenity.model.group.Student;
import team.serenity.model.util.UniqueList;

/**
 * Unmodifiable view of a StudentManager
 */
public interface ReadOnlyStudentManager {
    /**
     * Returns an Unmodifiable view of the Student HashMap
     * this Hashmap will not contain any duplicate Students
     */
    public Map<Group, UniqueList<Student>> getStudentMap();

}
