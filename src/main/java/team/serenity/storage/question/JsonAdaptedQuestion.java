package team.serenity.storage.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Question;

/**
 * Jackson-friendly version of {@link Question}.
 */
public class JsonAdaptedQuestion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Question's %s field is missing!";

    private final String group;
    private final String lesson;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given trip details.
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("group") String group,
                               @JsonProperty("lesson") String lesson,
                               @JsonProperty("description") String description) {
        this.group = group;
        this.lesson = lesson;
        this.description = description;
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     */
    public JsonAdaptedQuestion(Question source) {
        this.group = source.getGroup();
        this.lesson = source.getLesson();
        this.description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted trip object into the model's {@code Question} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted trip.
     */
    public Question toModelType() throws IllegalValueException {
        if (this.group == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Group"));
        }

        if (this.lesson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Lesson"));
        }

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        if (!Question.isValidDescription(this.description)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(this.group, this.lesson, this.description);
    }

}

