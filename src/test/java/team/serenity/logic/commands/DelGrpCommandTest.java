package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_ASSERTION_ERROR_METHOD;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.logic.commands.DelGrpCommand.MESSAGE_DELETE_GROUP_SUCCESS;
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

public class DelGrpCommandTest {

    @Test
    void execute_noGroup() {
        ModelStub modelStub = new DelGrpCommandTest.ModelStubWithNoGroup();
        DelGrpCommand delGrpCommand = new DelGrpCommand(new GroupContainsKeywordPredicate("G01"));
        assertThrows(CommandException.class, MESSAGE_GROUP_EMPTY, () -> delGrpCommand.execute(modelStub));
    }

    @Test
    void execute_containsGroup() {
        try {
            ModelStub modelStub = new DelGrpCommandTest.ModelStubWithGroup();
            DelGrpCommand delGrpCommand = new DelGrpCommand(new GroupContainsKeywordPredicate("G04"));
            CommandResult actual = delGrpCommand.execute(modelStub);
            Group group = modelStub.getFilteredGroupList().get(0);
            assertEquals(
                String.format(MESSAGE_DELETE_GROUP_SUCCESS, group),
                actual.getFeedbackToUser()
            );
        } catch (CommandException e) {
            throw new AssertionError(MESSAGE_ASSERTION_ERROR_METHOD);
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

        @Override
        public void deleteGroup(Group group) {}
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

        @Override
        public void deleteGroup(Group group) {}
    }

}
