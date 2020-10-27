package team.serenity.model.managers;

import javafx.collections.ObservableList;
import team.serenity.model.group.question.Question;

/**
 * Unmodifiable view of a QuestionManager.
 */
public interface ReadOnlyQuestionManager {
    /**
     * Returns an unmodifiable view of the question list.
     * This list will not contain any duplicate question.
     */
    ObservableList<Question> getListOfQuestions();
}
