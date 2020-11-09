package team.serenity.model.group.question;

import java.util.function.Predicate;

import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

public class QuestionFromGroupLessonPredicate implements Predicate<Question> {

    private final GroupName groupName;
    private final LessonName lessonName;

    /**
     * Instantiates a new QuestionContainsKeywordPredicate contains keywords predicate.
     *
     * @param groupName the groupName
     * @param lessonName the lessonName
     */
    public QuestionFromGroupLessonPredicate(GroupName groupName, LessonName lessonName) {
        this.groupName = groupName;
        this.lessonName = lessonName;
    }

    @Override
    public boolean test(Question question) {
        return question.getGroupName().equals(this.groupName) && question.getLessonName().equals(this.lessonName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionFromGroupLessonPredicate // instanceof handles nulls
                && this.groupName.equals(((QuestionFromGroupLessonPredicate) other).groupName)); // state check
    }
}
