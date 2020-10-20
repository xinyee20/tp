package team.serenity.testutil;

import java.util.function.Predicate;

import team.serenity.model.group.Group;

public class GroupPredicateStub implements Predicate<Group> {

    @Override
    public boolean test(Group group) {
        return true;
    }

}
