package team.serenity.model.managers;

import java.util.HashMap;
import java.util.Optional;

import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

public class StudentInfoManager {
    private final HashMap<GroupLessonKey, UniqueList<StudentInfo>> mapToListOfStudentsInfo;

    public StudentInfoManager() {
        this.mapToListOfStudentsInfo = new HashMap<>();
    }

    public Optional<UniqueList<StudentInfo>> getListOfStudentsInfoFromGroupLessonKey(GroupLessonKey key) {
        return Optional.ofNullable(this.mapToListOfStudentsInfo.get(key));
    }

    /**
     * Replaces listOfStudentsInfo stored at {@code key} with {@code newListOfStudentsInfo}.
     * @param key
     * @param newListOfStudentsInfo
     */
    public void setListOfStudentsInfoToGroupLessonKey(GroupLessonKey key,
                                                      UniqueList<StudentInfo> newListOfStudentsInfo) {
        this.mapToListOfStudentsInfo.put(key, newListOfStudentsInfo);
    }
}
