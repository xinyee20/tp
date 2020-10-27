package team.serenity.testutil.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;

public class TypicalQuestion {

    public static final Description QUESTION_A_DESC = new Description("What is the deadline for the report?");
    public static final Description QUESTION_B_DESC = new Description("What do we need to prepare for the lesson?");
    public static final Description QUESTION_C_DESC = new Description("How is the grading criteria like?");

    public static final Question QUESTION_A = new QuestionBuilder().withGroupName("G04")
        .withLessonName("2-2").withDescription("What is the deadline for the report?").build();

    public static final Question QUESTION_B = new QuestionBuilder().withGroupName("G05")
        .withLessonName("3-1").withDescription("What do we need to prepare for tomorrow's lesson?").build();

    public static final Question QUESTION_C = new QuestionBuilder().withGroupName("G05")
        .withLessonName("1-2").withDescription("How is the grading criteria like?").build();

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
