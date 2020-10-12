package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalQuestions.QUESTION_1;
import static seedu.address.testutil.TypicalQuestions.QUESTION_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateQuestionException;
import seedu.address.model.group.exceptions.QuestionNotFoundException;

class UniqueQuestionListTest {

    private final UniqueQuestionList uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        assertFalse(uniqueQuestionList.contains(QUESTION_1));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        uniqueQuestionList.add(QUESTION_1);
        assertTrue(uniqueQuestionList.contains(QUESTION_1));
    }

    @Test
    public void add_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.add(null));
    }

    @Test
    public void add_duplicateQuestion_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(QUESTION_1);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.add(QUESTION_1));
    }

    @Test
    public void setQuestion_nullTargetQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestion(null, QUESTION_1));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestion(QUESTION_1, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInList_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.setQuestion(QUESTION_1, QUESTION_1));
    }

    @Test
    public void setQuestion_editedQuestionIsSameQuestion_success() {
        uniqueQuestionList.add(QUESTION_1);
        uniqueQuestionList.setQuestion(QUESTION_1, QUESTION_1);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_1);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasNonUniqueIdentity_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(QUESTION_1);
        uniqueQuestionList.add(QUESTION_2);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.setQuestion(QUESTION_1, QUESTION_2));
    }

    @Test
    public void remove_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.remove(QUESTION_1));
    }

    @Test
    public void remove_existingQuestion_removesQuestion() {
        uniqueQuestionList.add(QUESTION_1);
        uniqueQuestionList.remove(QUESTION_1);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullUniqueQuestionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((UniqueQuestionList) null));
    }

    @Test
    public void setQuestions_uniqueQuestionList_replacesOwnListWithProvidedUniqueQuestionList() {
        uniqueQuestionList.add(QUESTION_1);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_2);
        uniqueQuestionList.setQuestions(expectedUniqueQuestionList);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((List<Question>) null));
    }

    @Test
    public void setQuestions_list_replacesOwnListWithProvidedList() {
        uniqueQuestionList.add(QUESTION_1);
        List<Question> questionList = Collections.singletonList(QUESTION_2);
        uniqueQuestionList.setQuestions(questionList);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(QUESTION_2);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_listWithDuplicateQuestions_throwsDuplicateQuestionException() {
        List<Question> listWithDuplicateQuestions = Arrays.asList(QUESTION_1, QUESTION_1);
        assertThrows(DuplicateQuestionException.class, () ->
            uniqueQuestionList.setQuestions(listWithDuplicateQuestions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueQuestionList.asUnmodifiableObservableList().remove(0));
    }

}
