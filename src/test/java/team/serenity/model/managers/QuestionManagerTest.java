package team.serenity.model.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_C;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.group.question.Question;

class QuestionManagerTest {

    private final QuestionManager questionManager = new QuestionManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), this.questionManager.getListOfQuestions());
    }

    @Test
    public void setQuestions_nullListOfQuestions_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.setQuestions(null));
    }

    @Test
    public void setQuestions_invalidListOfQuestions_throwsDuplicateQuestionException() {
        List<Question> newQuestions = Arrays.asList(QUESTION_A, QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () -> this.questionManager.setQuestions(newQuestions));;
    }

    @Test
    public void setQuestions_validListOfQuestions_setsQuestions() {
        List<Question> newQuestions = Arrays.asList(QUESTION_A, QUESTION_B);
        this.questionManager.setQuestions(newQuestions);
        assertEquals(newQuestions, this.questionManager.getListOfQuestions());
    }

    @Test
    public void resetData_nullNewData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.resetData(null));
    }

    @Test
    public void resetData_withDuplicateQuestionsInNewData_throwsDuplicateQuestionException() {
        List<Question> newQuestions = Arrays.asList(QUESTION_A, QUESTION_A);
        QuestionManagerStub newData = new QuestionManagerStub(newQuestions);
        assertThrows(DuplicateQuestionException.class, () -> this.questionManager.resetData(newData));
    }

    @Test
    public void resetData_withValidNewData_replacesData() {
        QuestionManager newData = getTypicalQuestionManager();
        this.questionManager.resetData(newData);
        assertEquals(newData, this.questionManager);
    }

    @Test
    public void getListOfQuestions_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> this.questionManager
                .getListOfQuestions().remove(0));
    }

    @Test
    public void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.hasQuestion(null));
    }

    @Test
    public void hasQuestion_questionNotInQuestionManager_returnsFalse() {
        assertFalse(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void hasQuestion_questionInQuestionManager_returnsTrue() {
        this.questionManager.addQuestion(QUESTION_A);
        assertTrue(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void addQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.addQuestion(null));
    }

    @Test
    public void addQuestion_validQuestion_addsQuestion() {
        this.questionManager.addQuestion(QUESTION_A);
        assertTrue(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void addQuestion_duplicateQuestion_throwsDuplicateQuestionException() {
        this.questionManager.addQuestion(QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () -> this.questionManager.addQuestion(QUESTION_A));
    }

    @Test
    public void setQuestion_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.setQuestion(null, null));
        assertThrows(NullPointerException.class, () -> this.questionManager.setQuestion(null, QUESTION_A));
        assertThrows(NullPointerException.class, () -> this.questionManager.setQuestion(QUESTION_A, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInQuestionManager_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> this.questionManager.setQuestion(QUESTION_A, QUESTION_B));
    }

    @Test
    public void setQuestion_editedQuestionInQuestionManager_throwsDuplicateQuestionException() {
        this.questionManager.addQuestion(QUESTION_A);
        this.questionManager.addQuestion(QUESTION_B);
        assertThrows(DuplicateQuestionException.class, () -> this.questionManager.setQuestion(QUESTION_A, QUESTION_B));;
    }

    @Test
    public void setQuestion_validInputs_setsQuestion() {
        this.questionManager.addQuestion(QUESTION_A);
        this.questionManager.setQuestion(QUESTION_A, QUESTION_B);
        assertFalse(this.questionManager.hasQuestion(QUESTION_A));
        assertTrue(this.questionManager.hasQuestion(QUESTION_B));
    }

    @Test
    public void deleteQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.questionManager.deleteQuestion(null));
    }

    @Test
    public void deleteQuestion_questionNotInQuestionManager_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> this.questionManager.deleteQuestion(QUESTION_A));
    }

    @Test
    public void deleteQuestion_validQuestion_deletesQuestion() {
        this.questionManager.addQuestion(QUESTION_A);
        this.questionManager.deleteQuestion(QUESTION_A);
        assertFalse(this.questionManager.hasQuestion(QUESTION_A));
    }

    @Test
    public void test_hashCode() {
        // Same case
        List<Question> questionList = Arrays.asList(QUESTION_A, QUESTION_B, QUESTION_C);
        QuestionManager questionManagerCopy = new QuestionManager(this.questionManager);
        this.questionManager.setQuestions(questionList);
        questionManagerCopy.setQuestions(questionList);
        assertEquals(this.questionManager.hashCode(), questionManagerCopy.hashCode());

        // Different case
        QuestionManager differentQuestionManager = new QuestionManager();
        assertNotEquals(this.questionManager.hashCode(), differentQuestionManager.hashCode());
    }

    @Test
    public void test_equals() {
        // same values -> returns true
        QuestionManager questionManagerCopy = new QuestionManager(this.questionManager);
        assertTrue(this.questionManager.equals(questionManagerCopy));

        // same object -> returns true
        assertTrue(this.questionManager.equals(this.questionManager));

        // null -> returns false
        assertFalse(this.questionManager.equals(null));

        // different types -> returns false
        assertFalse(this.questionManager.equals(5));

        // different listOfQuestions -> returns false
        List<Question> questionList = Arrays.asList(QUESTION_A, QUESTION_B, QUESTION_C);
        List<Question> differentQuestionList = Arrays.asList(QUESTION_A, QUESTION_B);
        this.questionManager.setQuestions(questionList);
        questionManagerCopy.setQuestions(differentQuestionList);
        assertFalse(this.questionManager.equals(questionManagerCopy));
    }

    @Test
    public void test_toString() {
        // Same case
        QuestionManager questionManagerCopy = new QuestionManager(this.questionManager);
        assertEquals(this.questionManager.toString(), questionManagerCopy.toString());

        // Different case
        QuestionManager differentQuestionManager = getTypicalQuestionManager();
        assertNotEquals(this.questionManager.toString(), differentQuestionManager.toString());
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
