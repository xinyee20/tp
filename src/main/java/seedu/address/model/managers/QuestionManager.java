package seedu.address.model.managers;

import seedu.address.model.group.UniqueQuestionList;

public class QuestionManager {
    private final UniqueQuestionList questions;
    public QuestionManager() {
        questions = new UniqueQuestionList();
    }
    public UniqueQuestionList getQuestions() {
        return questions;
    }
}
