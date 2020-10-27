package team.serenity.testutil.question;

import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;

/**
 * A utility class to help with building QuestionManager objects.
 * Example usage: <br> {@code QuestionManager questionManager = new
 * QuestionManagerBuilder().withQuestion(QUESTION_A, QUESTION_B).build();}
 */
public class QuestionManagerBuilder {

    private QuestionManager questionManager;

    public QuestionManagerBuilder() {
        this.questionManager = new QuestionManager();
    }

    public QuestionManagerBuilder(QuestionManager questionManager) {
        this.questionManager = questionManager;
    }

    /**
     * Adds a new {@code Question} to the {@code QuestionManager} that we are building.
     */
    public QuestionManagerBuilder withQuestion(Question question) {
        this.questionManager.addQuestion(question);
        return this;
    }

    public QuestionManager build() {
        return this.questionManager;
    }

}
