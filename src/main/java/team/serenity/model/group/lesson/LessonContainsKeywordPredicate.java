package team.serenity.model.group.lesson;

import java.util.function.Predicate;

import team.serenity.commons.util.StringUtil;

/**
 * Tests that a {@code Lesson} matches the keyword given.
 */
public class LessonContainsKeywordPredicate implements Predicate<Lesson> {

    private final String keyword;

    public LessonContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    @Override
    public boolean test(Lesson lesson) {
        return StringUtil.containsWordIgnoreCase(lesson.getLessonName().toString(), this.keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((LessonContainsKeywordPredicate) other).keyword)); // state check
    }
}
