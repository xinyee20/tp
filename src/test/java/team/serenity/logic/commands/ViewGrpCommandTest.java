package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

class ViewGrpCommandTest {

    @Test
    void execute_noGroup() {
        ViewGrpCommand test = new ViewGrpCommand(new GroupContainsKeywordPredicate("G01"));
        CommandResult actual = test.execute(new ModelStubWithNoGroup());
        assertEquals(MESSAGE_GROUP_EMPTY, actual.getFeedbackToUser());
    }

    @Test
    void execute_containsGroup() {
        ModelStubWithGroup modelStub = new ModelStubWithGroup();
        ViewGrpCommand test = new ViewGrpCommand(new GroupContainsKeywordPredicate("G01"));
        CommandResult actual = test.execute(modelStub);
        assertEquals(
                String.format(MESSAGE_GROUP_LISTED_OVERVIEW, modelStub.getFilteredGroupList().get(0).getGroupName()),
                actual.getFeedbackToUser()
        );
    }

    private class ModelStubWithGroup extends ModelStub {
        private ObservableList<Group> groupList =
                FXCollections.observableList(Arrays.asList(new GroupBuilder().build()));
        private FilteredList<Group> filteredGroupList = new FilteredList<Group>(groupList);

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            return;
        }
    }

    private class ModelStubWithNoGroup extends ModelStub {
        private ObservableList<Group> groupList = FXCollections.observableList(Arrays.asList());
        private FilteredList<Group> filteredGroupList = new FilteredList<Group>(groupList);

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            return;
        }
    }
}
