package team.serenity.model.group.question;

import java.util.List;
import java.util.function.Predicate;

import team.serenity.commons.util.StringUtil;

/**
 * The type {@code Question} contains keywords predicate.
 */
public class QuestionContainsKeywordPredicate implements Predicate<Question> {

    private final List<String> keywords;

    /**
     * Instantiates a new QuestionContainsKeywordPredicate contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public QuestionContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsCharSequenceIgnoreCase(question.getDescription().description, keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordPredicate) other).keywords)); // state check
    }

}

