package team.serenity.model.group;

import java.util.function.Predicate;

import team.serenity.commons.util.StringUtil;

/**
 * Tests that a {@code Group} matches the keyword given.
 */
public class GroupContainsKeywordPredicate implements Predicate<Group> {

    private final String keyword;

    public GroupContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    public String getKeyword() {
        return this.keyword;
    }

    @Override
    public boolean test(Group group) {
        return StringUtil.containsWordIgnoreCase(group.getGroupName().toString(), this.keyword);
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((GroupContainsKeywordPredicate) other).keyword)); // state check
    }
}
