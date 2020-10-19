package seedu.address.model.managers;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.group.GroupLessonKey;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.util.UniqueList;

public class StudentInfoManager {
    private final HashMap<GroupLessonKey, UniqueList<StudentInfo>> studentInfos;
    public StudentInfoManager() {
        this.studentInfos = new HashMap<>();
    }

    public Optional<UniqueList<StudentInfo>> getStudentInfos(GroupLessonKey key) {
        return Optional.ofNullable(studentInfos.get(key));
    }

    /**
     * Replaces studentInfos stored at {@code key} with {@code newStudentInfos}
     * @param key
     * @param newStudentInfos
     */
    public void setStudentInfos(GroupLessonKey key, UniqueList<StudentInfo> newStudentInfos) {
        studentInfos.put(key, newStudentInfos);
    }
}
