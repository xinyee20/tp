package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_D;

import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.lesson.Lesson;

public class GroupLessonKeyBuilder {

    public static final Group DEFAULT_GROUP = GROUP_D;
    public static final Lesson DEFAULT_LESSON = GROUP_D.getLessonsAsUnmodifiableObservableList().get(0);

    private Group group;
    private Lesson lesson;

    /**
     * initializes a GroupLessonKeyBuilder
     */
    public GroupLessonKeyBuilder() {
        this.group = DEFAULT_GROUP;
        this.lesson = DEFAULT_LESSON;
    }

    /**
     * Parses the {@code group} and set it to the {@code GroupLessonKey} that we are building.
     */
    public GroupLessonKeyBuilder withGroup(Group group) {
        this.group = group;
        return this;
    }

    /**
     * Parses the {@code lesson} and set it to the {@code GroupLessonKey} that we are building.
     */
    public GroupLessonKeyBuilder withGroup(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public GroupLessonKey build() {
        return new GroupLessonKey(this.group.getGroupName(), this.lesson.getLessonName());
    }
}
