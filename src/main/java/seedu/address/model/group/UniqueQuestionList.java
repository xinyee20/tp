package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateQuestionException;
import seedu.address.model.group.exceptions.QuestionNotFoundException;

/**
 * A list of Questions that enforces uniqueness between its elements and does not allow nulls.
 * A Question is considered unique by comparing using {@code Question#equal(Object)}.
 */
public class UniqueQuestionList implements Iterable<Question> {

    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final ObservableList<Question> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent question as the given argument.
     */
    public boolean contains(Question toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a question to the list. The question must not already exist in the list.
     */
    public void add(Question toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuestionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the question {@code target} in the list with {@code question}. {@code target} must exist in the list.
     * The question identity of {@code question} must not be the same as another existing question in the list.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new QuestionNotFoundException();
        }

        if (!target.equals(editedQuestion) && contains(editedQuestion)) {
            throw new DuplicateQuestionException();
        }

        internalList.set(index, editedQuestion);
    }

    /**
     * Removes the equivalent question from the list. The question must exist in the list.
     */
    public void remove(Question toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new QuestionNotFoundException();
        }
    }

    /**
     * Replaces all the questions from the list with a new list of questions
     */
    public void setQuestions(UniqueQuestionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code questionList}.
     * {@code questionList} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> questionList) {
        requireAllNonNull(questionList);
        if (!questionsAreUnique(questionList)) {
            throw new DuplicateQuestionException();
        }
        internalList.setAll(questionList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Question> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Question> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQuestionList // instanceof handles nulls
                && internalList.equals(((UniqueQuestionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code questionList} contains only unique questions.
     */
    private boolean questionsAreUnique(List<Question> questionList) {
        for (int i = 0; i < questionList.size() - 1; i++) {
            for (int j = i + 1; j < questionList.size(); j++) {
                if (questionList.get(i).equals(questionList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
