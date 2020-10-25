package team.serenity.storage.question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;

/**
 * An Immutable QuestionManager that is serializable to JSON format.
 */
@JsonRootName(value = "questionManager")
public class JsonSerializableQuestionManager {

    public static final String MESSAGE_DUPLICATE_QUESTION = "Question list contains duplicate question(s).";

    private final List<JsonAdaptedQuestion> globalQuestionList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuestionManager} with the given questions.
     */
    @JsonCreator
    public JsonSerializableQuestionManager(
            @JsonProperty("globalQuestionList") List<JsonAdaptedQuestion> questions) {
        this.globalQuestionList.addAll(questions);
    }

    /**
     * Converts a given {@code ReadOnlyQuestionManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableQuestionManager}.
     */
    public JsonSerializableQuestionManager(ReadOnlyQuestionManager source) {
        globalQuestionList.addAll(
                source.getListOfQuestions()
                        .stream()
                        .map(JsonAdaptedQuestion::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts this JsonSerializableQuestionManager into the model's {@code QuestionManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public QuestionManager toModelType() throws IllegalValueException {
        QuestionManager questionManager = new QuestionManager();
        for (JsonAdaptedQuestion jsonAdaptedQuestion : this.globalQuestionList) {
            Question question = jsonAdaptedQuestion.toModelType();
            if (questionManager.hasQuestion(question)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUESTION);
            }
            questionManager.addQuestion(question);
        }
        return questionManager;
    }

}

