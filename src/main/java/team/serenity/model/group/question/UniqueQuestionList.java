package team.serenity.model.group.question;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.exceptions.DuplicateQuestionException;
import team.serenity.model.group.exceptions.NotFoundException;
import team.serenity.model.group.exceptions.QuestionNotFoundException;
import team.serenity.model.util.UniqueList;

/**
 * A list of Questions that enforces uniqueness between its elements and does not allow nulls.
 * A Question is considered unique by comparing using {@code Question#equal(Object)}.
 */
public class UniqueQuestionList implements UniqueList<Question> {

    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final ObservableList<Question> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    @Override
    public int size() {
        return this.internalList.size();
    }

    @Override
    public Stream<Question> stream() {
        return this.internalList.stream();
    }

    @Override
    public void sort(Comparator<Question> comparator) {
        this.internalList.sort(comparator);
    }

    /**
     * Returns true if the list contains an equivalent question as the given argument.
     */
    @Override
    public boolean contains(Question toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a question to the list. The question must not already exist in the list.
     */
    @Override
    public void add(Question toAdd) throws DuplicateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuestionException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the question {@code target} in the list with {@code question}. {@code target} must exist in the list.
     * The question identity of {@code question} must not be the same as another existing question in the list.
     */
    @Override
    public void setElement(Question target, Question editedQuestion) throws NotFoundException, DuplicateException {
        requireAllNonNull(target, editedQuestion);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new QuestionNotFoundException();
        }

        if (!target.equals(editedQuestion) && contains(editedQuestion)) {
            throw new DuplicateQuestionException();
        }

        this.internalList.set(index, editedQuestion);
    }

    /**
     * Removes the equivalent question from the list. The question must exist in the list.
     */
    @Override
    public void remove(Question toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new QuestionNotFoundException();
        }
    }

    @Override
    public ObservableList<Question> getList() {
        return this.internalList;
    }

    /**
     * Replaces all the lessons from the list with a new list of lessons
     * @param replacement
     */
    @Override
    public void setElementsWithUniqueList(UniqueList<Question> replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.getList());
    }

    /**
     * Replaces the contents of this list with {@code questionList}.
     * {@code questionList} must not contain duplicate questions.
     */
    @Override
    public void setElementsWithList(List<Question> questionList) throws DuplicateException {
        requireAllNonNull(questionList);
        if (!elementsAreUnique(questionList)) {
            throw new DuplicateQuestionException();
        }
        this.internalList.setAll(questionList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Question> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Question> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQuestionList // instanceof handles nulls
                && this.internalList.equals(((UniqueQuestionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    /**
     * Returns true if {@code questionList} contains only unique questions.
     */
    @Override
    public boolean elementsAreUnique(List<Question> questionList) {
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
