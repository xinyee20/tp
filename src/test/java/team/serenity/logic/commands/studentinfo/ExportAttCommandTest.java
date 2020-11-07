package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.logic.commands.studentinfo.ExportAttCommand.MESSAGE_GROUP_DOES_NOT_EXIST;
import static team.serenity.logic.commands.studentinfo.ExportAttCommand.MESSAGE_SUCCESS;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

public class ExportAttCommandTest {

    @Test
    void execute_noGroup() {
        ModelStub modelStub = new ExportAttCommandTest.ModelStubWithNoGroup();
        ExportAttCommand exportAttCommand = new ExportAttCommand(new GroupContainsKeywordPredicate("G01"));
        assertThrows(CommandException.class, MESSAGE_GROUP_DOES_NOT_EXIST, () -> exportAttCommand.execute(modelStub));
    }

    @Test
    void execute_containsGroup() throws CommandException {
        ModelStub modelStub = new ExportAttCommandTest.ModelStubWithGroup();
        ExportAttCommand ExportAttCommand = new ExportAttCommand(new GroupContainsKeywordPredicate("G04"));
        CommandResult actual = ExportAttCommand.execute(modelStub);
        GroupName groupName = modelStub.getFilteredGroupList().get(0).getGroupName();
        assertEquals(
            String.format(MESSAGE_SUCCESS, groupName, groupName),
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

        @Override
        public void exportAttendance(Group group) {}
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
        public void exportAttendance(Group group) {}
    }

}