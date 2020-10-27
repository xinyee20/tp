package team.serenity.storage.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;

/**
 * Jackson-friendly version of {@link Question}.
 */
public class JsonAdaptedQuestion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Question's %s field is missing!";

    private final String groupName;
    private final String lessonName;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given question details.
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("group") String groupName,
                               @JsonProperty("lesson") String lessonName,
                               @JsonProperty("description") String description) {
        this.groupName = groupName;
        this.lessonName = lessonName;
        this.description = description;
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     */
    public JsonAdaptedQuestion(Question source) {
        this.groupName = source.getGroupName().groupName;
        this.lessonName = source.getLessonName().lessonName;
        this.description = source.getDescription().description;
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted question.
     */
    public Question toModelType() throws IllegalValueException {
        if (this.groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GroupName.class.getSimpleName()));
        }
        final GroupName modelGroupName = new GroupName(this.groupName);

        if (this.lessonName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                LessonName.class.getSimpleName()));
        }
        final LessonName modelLessonName = new LessonName(this.lessonName);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(this.description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(this.description);

        return new Question(modelGroupName, modelLessonName, modelDescription);
    }

}

