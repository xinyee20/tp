package team.serenity.model.group.question;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

/**
 * Represents a Question in Serenity.
 * Guarantees: immutable
 */
public class Question {

    private final GroupName groupName;
    private final LessonName lessonName;
    private final Description description;

    /**
     * Constructs an {@code Question}.
     *  @param groupName A valid group name.
     * @param lessonName A valid lesson name.
     * @param description A valid question.
     */
    public Question(GroupName groupName, LessonName lessonName, Description description) {
        requireAllNonNull(groupName, lessonName, description);
        this.groupName = groupName;
        this.lessonName = lessonName;
        this.description = description;
    }

    public Question setGroupAndLesson(GroupName groupName, LessonName lessonName) {
        return new Question(groupName, lessonName, this.description);
    }

    public GroupName getGroupName() {
        return this.groupName;
    }

    public LessonName getLessonName() {
        return this.lessonName;
    }

    public Description getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("[Group %s Lesson %s] %s",
            this.groupName.toString(),
            this.lessonName.toString(),
            this.description.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Question // instanceof handles nulls
                && this.groupName.equals(((Question) other).groupName) // state check
                && this.lessonName.equals(((Question) other).lessonName)
                && this.description.equals(((Question) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.groupName, this.lessonName, this.description);
    }

}
