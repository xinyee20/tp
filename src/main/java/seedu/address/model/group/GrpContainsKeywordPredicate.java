package seedu.address.model.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class GrpContainsKeywordPredicate implements Predicate<Group> {

    private final String keyword;

    public GrpContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    @Override
    public boolean test(Group group) {
        return StringUtil.containsWordIgnoreCase(group.getName(),keyword);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
