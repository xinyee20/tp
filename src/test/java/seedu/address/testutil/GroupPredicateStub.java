package seedu.address.testutil;

import java.util.function.Predicate;

import seedu.address.model.group.Group;

public class GroupPredicateStub implements Predicate<Group> {

    @Override
    public boolean test(Group group) {
        return true;
    }

}
