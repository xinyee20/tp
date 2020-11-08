package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_ASSERTION_ERROR_METHOD;
import static team.serenity.commons.core.Messages.MESSAGE_ATTENDANCE_LISTED_OVERVIEW;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

public class ViewAttCommandTest {

    @Test
    void execute_noGroup_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithNoGroup();
        ViewAttCommand viewAttCommand = new ViewAttCommand(new GroupContainsKeywordPredicate("G01"));
        assertThrows(CommandException.class, MESSAGE_GROUP_EMPTY, () -> viewAttCommand.execute(modelStub));
    }

    @Test
    void execute_containsGroup() {
        try {
            ModelStub modelStub = new ModelStubWithGroup();
            ViewAttCommand viewAttCommand = new ViewAttCommand(new GroupContainsKeywordPredicate("G01"));
            CommandResult actual = viewAttCommand.execute(modelStub);
            assertEquals(
                String.format(MESSAGE_ATTENDANCE_LISTED_OVERVIEW,
                modelStub.getFilteredGroupList().get(0).getGroupName()), actual.getFeedbackToUser());
        } catch (CommandException e) {
            throw new AssertionError(MESSAGE_ASSERTION_ERROR_METHOD, e);
        }
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
