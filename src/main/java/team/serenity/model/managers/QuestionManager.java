package team.serenity.model.managers;

import team.serenity.model.group.Question;
import team.serenity.model.group.UniqueQuestionList;
import team.serenity.model.util.UniqueList;

public class QuestionManager {

    private final UniqueList<Question> questionList;

    public QuestionManager() {
        this.questionList = new UniqueQuestionList();
    }

    public UniqueList<Question> getQuestionList() {
        return this.questionList;
    }
}
