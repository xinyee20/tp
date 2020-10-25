package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.serenity.model.group.exceptions.DuplicateException;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.question.UniqueQuestionList;
import team.serenity.model.util.UniqueList;

/**
 * Wraps all data at the QuestionManager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class QuestionManager implements ReadOnlyQuestionManager {

    private final UniqueList<Question> listOfQuestions;

    /**
     * Instantiates a new QuestionManager.
     */
    public QuestionManager() {
        this.listOfQuestions = new UniqueQuestionList();
    }

    /**
     * Creates a QuestionManager using the Questions in the {@code} toBeCopied}
     */
    public QuestionManager(ReadOnlyQuestionManager toBeCopied) {
        this.listOfQuestions = new UniqueQuestionList();
        resetData(toBeCopied);
    }

    // Methods that overrides the whole question list

    /**
     * Replaces the contents of the question list with {@code newListOfQuestions}.
     * {@code newListOfQuestions} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> newListOfQuestions) {
        this.listOfQuestions.setElementsWithList(newListOfQuestions);
    }

    /**
     * Resets the existing data of this {@code QuestionManager} with {@code newData}
     */
    public void resetData(ReadOnlyQuestionManager newData) {
        requireNonNull(newData);
        setQuestions(newData.getListOfQuestions());
    }

    public ObservableList<Question> getListOfQuestions() {
        return this.listOfQuestions.asUnmodifiableObservableList();
    }

    // Question-level operations

    /**
     * Returns true if a question with the same identity as {@code toCheck} exists in the QuestionManager.
     */
    public boolean hasQuestion(Question toCheck) {
        requireNonNull(toCheck);
        return this.listOfQuestions.contains(toCheck);
    }

    /**
     * Adds a question {@code toAdd} to the QuestionManager.
     * The question must not already exist in the QuestionManager.
     */
    public void addQuestion(Question toAdd) {
        requireNonNull(toAdd);
        if (hasQuestion(toAdd)) {
            throw new DuplicateException();
        }
        this.listOfQuestions.add(toAdd);
    }

    /**
     * Replaces the given question {@code target} in the list with {@code editedQuestion}.
     * {@code target} must exist in the QuestionManager.
     * The question identity of {@code editedQuestion} must not be the same as
     * another existing question in the QuestionManager.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireNonNull(editedQuestion);
        this.listOfQuestions.setElement(target, editedQuestion);
    }

    /**
     * Deletes the given question {@code toDelete} from this {@code QuestionManager}.
     * {@code toDelete} must exist in the QuestionManager.
     */
    public void deleteQuestion(Question toDelete) {
        this.listOfQuestions.remove(toDelete);
    }

    // Util Methods

    @Override
    public int hashCode() {
        return this.listOfQuestions.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof QuestionManager // instanceof handles nulls
                && this.listOfQuestions.equals(((QuestionManager) obj).listOfQuestions));
    }

    @Override
    public String toString() {
        return "QuestionManager:\n"
                + this.listOfQuestions.getList().stream().map(Question::toString).collect(Collectors.joining("\n"))
                + "\n Total number of questions: " + this.listOfQuestions.size();
    }

}
