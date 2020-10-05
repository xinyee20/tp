package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class LsnContainsKeywordPredicate implements Predicate<Lesson> {

    private final String keyword;

    public LsnContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    @Override
    public boolean test(Lesson lesson) {
        return StringUtil.containsWordIgnoreCase(lesson.getName(), keyword);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
