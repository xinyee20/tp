package team.serenity.model.group;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.AppUtil.checkArgument;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a Lesson's question in Serenity.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Question {

    public static final String MESSAGE_CONSTRAINTS = "Questions can take any values, and it should not be blank";

    /*
     * The first character of the question must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String group;
    private String lesson;
    private final String description;

    /**
     * Constructs an {@code Question}.
     *
     * @param question A valid question.
     */
    public Question(String question) {
        requireNonNull(question);
        checkArgument(isValidDescription(question), MESSAGE_CONSTRAINTS);
        this.group = "";
        this.lesson = "";
        this.description = question;
    }

    /**
     * Constructs an {@code Question}.
     *
     * @param group
     * @param lesson
     * @param question A valid question.
     */
    public Question(String group, String lesson, String question) {
        requireAllNonNull(group, lesson, question);
        checkArgument(isValidDescription(question), MESSAGE_CONSTRAINTS);
        this.group = group;
        this.lesson = lesson;
        this.description = question;
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void setGroupAndLesson(String group, String lesson) {
        this.group = group;
        this.lesson = lesson;
    }

    public String getGroup() {
        return this.group;
    }

    public String getLesson() {
        return this.lesson;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("[Group %s Lesson %s] %s", this.group, this.lesson, this.description);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Question // instanceof handles nulls
                && this.group.equals(((Question) other).getGroup()) // state check
                && this.lesson.equals(((Question) other).getLesson())
                && this.description.equals(((Question) other).getDescription())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.group, this.lesson, this.description);
    }

}
