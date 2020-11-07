package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_LISTED_OVERVIEW;

import java.util.Collections;
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

public class ViewScoreCommandTest {

    @Test
    void execute_noGroup() {
        ModelStub modelStub = new ViewScoreCommandTest.ModelStubWithNoGroup();
        ViewScoreCommand ViewScoreCommand = new ViewScoreCommand(new GroupContainsKeywordPredicate("G01"));

        CommandResult actual = ViewScoreCommand.execute(modelStub);
        assertEquals(MESSAGE_GROUP_EMPTY, actual.getFeedbackToUser());
    }

    @Test
    void execute_containsGroup() {
        ModelStub modelStub = new ViewScoreCommandTest.ModelStubWithGroup();
        ViewScoreCommand ViewScoreCommand = new ViewScoreCommand(new GroupContainsKeywordPredicate("G04"));
        CommandResult actual = ViewScoreCommand.execute(modelStub);
        assertEquals(
            String.format(MESSAGE_SCORE_LISTED_OVERVIEW, modelStub.getFilteredGroupList().get(0).getGroupName()),
            actual.getFeedbackToUser()
        );
    }

    private static class ModelStubWithGroup extends ModelStub {
        private ObservableList<Group> groupList =
            FXCollections.observableList(Collections.singletonList(new GroupBuilder().build()));
        private FilteredList<Group> filteredGroupList = new FilteredList<>(groupList);

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {}
    }

    private static class ModelStubWithNoGroup extends ModelStub {
        private ObservableList<Group> groupList = FXCollections.observableList(Collections.emptyList());
        private FilteredList<Group> filteredGroupList = new FilteredList<>(groupList);

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {}
    }

}