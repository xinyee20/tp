package team.serenity.testutil.question;

import team.serenity.model.group.Question;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {

    public static final String DEFAULT_GROUP = "";
    public static final String DEFAULT_LESSON = "";
    public static final String DEFAULT_DESCRIPTION = "How is the grading criteria like?";

    private String group;
    private String lesson;
    private String description;

    /**
     * Initializes the QuestionBuilder with the default data.
     */
    public QuestionBuilder() {
        this.group = DEFAULT_GROUP;
        this.lesson = DEFAULT_LESSON;
        this.description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code question}.
     */
    public QuestionBuilder(Question question) {
        this.group = question.getGroup();
        this.lesson = question.getLesson();
        this.description = question.getDescription();
    }

    /**
     * Sets the {@code group} of the {@code Question} that we are building.
     */
    public QuestionBuilder withGroup(String group) {
        this.group = group;
        return this;
    }

    /**
     * Sets the {@code lesson} of the {@code Question} that we are building.
     */
    public QuestionBuilder withLesson(String lesson) {
        this.lesson = lesson;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Question} that we are building.
     */
    public QuestionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Question build() {
        return new Question(this.group, this.lesson, this.description);
    }

}
