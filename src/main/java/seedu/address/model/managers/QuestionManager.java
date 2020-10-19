package seedu.address.model.managers;

import seedu.address.model.group.Question;
import seedu.address.model.group.UniqueQuestionList;
import seedu.address.model.util.UniqueList;

public class QuestionManager {
    private final UniqueList<Question> questions;
    public QuestionManager() {
        questions = new UniqueQuestionList();
    }
    public UniqueList<Question> getQuestions() {
        return questions;
    }
}
