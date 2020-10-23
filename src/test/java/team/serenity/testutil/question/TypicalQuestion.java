package team.serenity.testutil.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.serenity.model.group.Question;
import team.serenity.model.managers.QuestionManager;

public class TypicalQuestion {

    public static final Question QUESTION_A = new Question("G04", "2-2", "What is the deadline for the report?");

    public static final Question QUESTION_B = new Question("G04", "3-1",
        "What do we need to prepare for tomorrow's lesson?");

    public static final Question QUESTION_C = new Question( "G05", "1-2", "How is the grading criteria like?");

    private TypicalQuestion() {
    } // prevents instantiation

    /**
     * Returns a {@code QuestionManager} with all the typical questions.
     */

    public static QuestionManager getTypicalQuestionManager() {
        QuestionManager questionManager = new QuestionManager();
        for (Question question : getTypicalQuestion()) {
            questionManager.addQuestion(question);
        }
        return questionManager;
    }

    public static List<Question> getTypicalQuestion() {
        return new ArrayList<>(Arrays.asList(QUESTION_A, QUESTION_B, QUESTION_C));
    }

}
