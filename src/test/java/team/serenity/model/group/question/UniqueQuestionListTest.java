package team.serenity.model.group.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.util.UniqueList;

class UniqueQuestionListTest {

    private final UniqueList<Question> uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        assertFalse(uniqueQuestionList.contains(QUESTION_A));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        uniqueQuestionList.add(QUESTION_A);
        assertTrue(uniqueQuestionList.contains(QUESTION_A));
    }

    @Test
    public void add_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.add(null));
    }

    @Test
    public void add_duplicateQuestion_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.add(QUESTION_A));
    }

    @Test
    public void setQuestion_nullTargetQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setElement(null, QUESTION_A));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setElement(QUESTION_A, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInList_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.setElement(QUESTION_A, QUESTION_A));
    }

    @Test
    public void setQuestion_editedQuestionIsSameQuestion_success() {
        this.uniqueQuestionList.add(QUESTION_A);
        this.uniqueQuestionList.setElement(QUESTION_A, QUESTION_A);
        UniqueList<Question> expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_A);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasNonUniqueIdentity_throwsDuplicateQuestionException() {
        this.uniqueQuestionList.add(QUESTION_A);
        this.uniqueQuestionList.add(QUESTION_B);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.setElement(QUESTION_A, QUESTION_B));
    }

    @Test
    public void remove_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.remove(QUESTION_A));
    }

    @Test
    public void remove_existingQuestion_removesQuestion() {
        this.uniqueQuestionList.add(QUESTION_A);
        this.uniqueQuestionList.remove(QUESTION_A);
        UniqueList<Question> expectedUniqueQuestionList = new UniqueQuestionList();
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullUniqueQuestionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList
                .setElementsWithUniqueList((UniqueQuestionList) null));
    }

    @Test
    public void setQuestions_uniqueQuestionList_replacesOwnListWithProvidedUniqueQuestionList() {
        this.uniqueQuestionList.add(QUESTION_A);
        UniqueList<Question> expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_B);
        this.uniqueQuestionList.setElementsWithUniqueList(expectedUniqueQuestionList);
        assertEquals(expectedUniqueQuestionList, this.uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.uniqueQuestionList
            .setElementsWithList((List<Question>) null));
    }

    @Test
    public void setQuestions_list_replacesOwnListWithProvidedList() {
        this.uniqueQuestionList.add(QUESTION_A);
        List<Question> questionList = Collections.singletonList(QUESTION_B);
        this.uniqueQuestionList.setElementsWithList(questionList);
        UniqueList<Question> expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_B);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_listWithDuplicateQuestions_throwsDuplicateQuestionException() {
        List<Question> listWithDuplicateQuestions = Arrays.asList(QUESTION_A, QUESTION_A);
        assertThrows(DuplicateQuestionException.class, () ->
            this.uniqueQuestionList.setElementsWithList(listWithDuplicateQuestions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            this.uniqueQuestionList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void test_qquals() {
        UniqueList<Question> expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_A);
        this.uniqueQuestionList.add(QUESTION_A);
        assertEquals(uniqueQuestionList, expectedUniqueQuestionList);

    }

    @Test
    public void test_hashCode() {
        //Same Hash Code
        this.uniqueQuestionList.add(QUESTION_A);
        UniqueList<Question> list = new UniqueQuestionList();
        list.add(QUESTION_A);
        assertEquals(list.hashCode(), this.uniqueQuestionList.hashCode());

        //Different Hash code
        UniqueList<Question> diffPl = new UniqueQuestionList();
        diffPl.add(QUESTION_B);
        assertNotEquals(diffPl.hashCode(), this.uniqueQuestionList.hashCode());
    }

}
