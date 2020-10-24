package team.serenity.model.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.question.Question;

class QuestionManagerTest {

    private final QuestionManager questionManager = new QuestionManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), this.questionManager.getListOfQuestions());
    }

    @Test
    public void resetDataNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyActivityManagerReplacesData() {
        QuestionManager newData = getTypicalQuestionManager();
        this.questionManager.resetData(newData);
        assertEquals(newData, this.questionManager);
    }

    @Test
    public void resetDataWithDuplicateQuestionThrowsDuplicateQuestionException() {
        List<Question> newQuestions = Arrays.asList(QUESTION_A, QUESTION_A);
        QuestionManagerStub newData = new QuestionManagerStub(newQuestions);
        assertThrows(DuplicateQuestionException.class, () -> this.questionManager.resetData(newData));
    }

    @Test
    public void hasQuestionNullQuestionThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.hasQuestion(null));
    }

    @Test
    public void hasQuestionNotInQuestionManagerReturnsFalse() {
        assertFalse(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void hasQuestionManagerInQuestionManagerReturnsTrue() {
        this.questionManager.addQuestion(QUESTION_A);
        assertTrue(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void getQuestionModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> this.questionManager
                .getListOfQuestions().remove(0));
    }

    /**
     * A stub ReadOnlyQuestionManager whose question list can violate interface constraints.
     */
    private static class QuestionManagerStub implements ReadOnlyQuestionManager {
        private final ObservableList<Question> questionList = FXCollections.observableArrayList();

        QuestionManagerStub(Collection<Question> questions) {
            this.questionList.setAll(questions);
        }

        @Override
        public ObservableList<Question> getListOfQuestions() {
            return this.questionList;
        }
    }

}
