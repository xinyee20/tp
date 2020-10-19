package seedu.address.model.group;

/**
 * Wraps a Group and a Lesson to form a key for a Hashmap
 */
public class GroupLessonKey {
    private final Group group;
    private final Lesson lesson;

    /**
     * Instantiates a GroupLessonKey object
     * @param group
     * @param lesson
     */
    public GroupLessonKey(Group group, Lesson lesson) {
        this.group = group;
        this.lesson = lesson;
    }

    /**
     * Generates a hashcode for the object.
     * Since Groups and Lessons are unique,
     * concatenating the group and lesson, then hashing it would give a good hash function.
     * @return hash code
     */
    @Override
    public int hashCode() {
        String groupName = group.getName();
        String lessonName = lesson.getName();
        String cocatenated = groupName + lessonName;
        return cocatenated.hashCode();
    }
}
