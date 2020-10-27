package team.serenity.testutil.question;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {

    public static final String DEFAULT_GROUP_NAME = "G04";
    public static final String DEFAULT_LESSON_NAME = "1-1";
    public static final String DEFAULT_DESCRIPTION = "How is the grading criteria like?";

    private GroupName groupName;
    private LessonName lessonName;
    private Description description;

    /**
     * Initializes the QuestionBuilder with the default data.
     */
    public QuestionBuilder() {
        this.groupName = new GroupName(DEFAULT_GROUP_NAME);
        this.lessonName = new LessonName(DEFAULT_LESSON_NAME);
        this.description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code question}.
     */
    public QuestionBuilder(Question question) {
        this.groupName = question.getGroupName();
        this.lessonName = question.getLessonName();
        this.description = question.getDescription();
    }

    /**
     * Sets the {@code groupName} of the {@code Question} that we are building.
     * @param groupName
     */
    public QuestionBuilder withGroupName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    /**
     * Sets the {@code lessonName} of the {@code Question} that we are building.
     * @param lessonName
     */
    public QuestionBuilder withLessonName(String lessonName) {
        this.lessonName = new LessonName(lessonName);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Question} that we are building.
     * @param description
     */
    public QuestionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Question build() {
        return new Question(this.groupName, this.lessonName, this.description);
    }

}
