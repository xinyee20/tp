package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Group} matches the keyword given.
 */
public class GroupContainsKeywordPredicate implements Predicate<Group> {

    private final String keyword;

    public GroupContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public boolean test(Group group) {
        return StringUtil.containsWordIgnoreCase(group.getName(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((GroupContainsKeywordPredicate) other).keyword)); // state check
    }
}
