package team.serenity.model.group.question;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_C;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.util.UniqueList;

class UniqueQuestionListTest {

    private final UniqueList<Question> uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void test_stream() {
        Stream<Question> expected = Stream.empty();
        assertArrayEquals(expected.toArray(), this.uniqueQuestionList.stream().toArray());
    }

    @Test
    public void test_sort() {
        Comparator<Question> questionComparator = new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o1.getDescription().description.compareTo(o2.getDescription().description);
            }
        };
        List<Question> questionList = Arrays.asList(QUESTION_A, QUESTION_B, QUESTION_C);
        List<Question> expectedList = Arrays.asList(QUESTION_C, QUESTION_B, QUESTION_A);
        this.uniqueQuestionList.setElementsWithList(questionList);
        this.uniqueQuestionList.sort(questionComparator);
        assertArrayEquals(expectedList.toArray(), this.uniqueQuestionList.getList().toArray());
    }

    public static boolean equalIterators(Iterator i1, Iterator i2) {
        if (i1 == i2) {
            return true;
        }
        while (i1.hasNext()) {
            if (!i2.hasNext()) {
                return false;
            }
            if (!Objects.equals(i1.next(), i2.next())) {
                return false;
            }
        }
        if (i2.hasNext()) {
            return false;
        }
        return true;
    }

    @Test
    public void test_iterator() {
        Iterator<Question> expectedIterator = Arrays.asList(QUESTION_A, QUESTION_B).iterator();
        List<Question> questionList = Arrays.asList(QUESTION_A, QUESTION_B);
        this.uniqueQuestionList.setElementsWithList(questionList);
        assertTrue(equalIterators(expectedIterator, this.uniqueQuestionList.iterator()));
    }

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
