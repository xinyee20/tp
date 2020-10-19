package seedu.address.model.managers;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.group.GroupLessonKey;
import seedu.address.model.group.UniqueStudentInfoList;

public class StudentInfoManager {
    private final HashMap<GroupLessonKey, UniqueStudentInfoList> studentInfos;
    public StudentInfoManager() {
        this.studentInfos = new HashMap<>();
    }

    public Optional<UniqueStudentInfoList> getStudentInfos(GroupLessonKey key) {
        return Optional.ofNullable(studentInfos.get(key));
    }

    /**
     * Replaces studentInfos stored at {@code key} with {@code newStudentInfos}
     * @param key
     * @param newStudentInfos
     */
    public void setStudentInfos(GroupLessonKey key, UniqueStudentInfoList newStudentInfos) {
        studentInfos.put(key, newStudentInfos);
    }
}
