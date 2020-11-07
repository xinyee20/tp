package team.serenity.model.group;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import team.serenity.model.group.lesson.LessonName;

/**
 * Wraps a Group and a Lesson to form a key for a Hashmap.
 */
public class GroupLessonKey {
    private final GroupName groupName;
    private final LessonName lessonName;

    /**
     * Instantiates a GroupLessonKey object.
     * @param groupName
     * @param lessonName
     */
    public GroupLessonKey(GroupName groupName, LessonName lessonName) {
        requireAllNonNull(groupName, lessonName);
        this.groupName = groupName;
        this.lessonName = lessonName;
    }

    /**
     * Retrieves groupName.
     */
    public GroupName getGroupName() {
        return groupName;
    }

    /**
     * Generates a hashcode for the object.
     * Since Groups and Lessons are unique,
     * concatenating the group and lesson, then hashing it would give a good hash function.
     * @return hash code
     */
    @Override
    public int hashCode() {
        String groupName = this.groupName.toString();
        String lessonName = this.lessonName.toString();
        String cocatenated = groupName + lessonName;
        return cocatenated.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof GroupLessonKey
                && this.groupName.equals(((GroupLessonKey) obj).groupName)
                && this.lessonName.equals(((GroupLessonKey) obj).lessonName));
    }
}
