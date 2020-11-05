package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.commands.lesson.DelLsnCommand;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

class DelLsnCommandTest {

    @Test
    void execute_invalidGroup_throwsCommandException() {
        ModelStubWithGroupAndLesson model = new ModelStubWithGroupAndLesson();
        DelLsnCommand test = new DelLsnCommand(new GroupName("G05"), new LessonName("1-1"));
        assertThrows(CommandException.class, () -> test.execute(model));
    }

    @Test
    void testEquals_invalidLesson_throwsCommandException() {
        ModelStubWithGroupAndLesson model = new ModelStubWithGroupAndLesson();
        DelLsnCommand test = new DelLsnCommand(new GroupName("G04"), new LessonName("2-1"));
        assertThrows(CommandException.class, () -> test.execute(model));
    }

    @Test
    void testEquals_validGroupAndLesson() {
        ModelStubWithGroupAndLesson actual = new ModelStubWithGroupAndLesson();
        DelLsnCommand test = new DelLsnCommand(new GroupName("G04"), new LessonName("1-1"));
        Group expectedGroup = new GroupBuilder().withClasses("1-2").build();
        try {
            CommandResult result = test.execute(actual);
            assertEquals(
                    String.format(DelLsnCommand.MESSAGE_SUCCESS,
                            new LessonName("1-1"),
                            actual.getFilteredGroupList().get(0).getGroupName()),
                    result.getFeedbackToUser()
            );
            assertEquals(expectedGroup, actual.getFilteredGroupList().get(0));
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }


    private class ModelStubWithGroupAndLesson extends ModelStub {
        private Group group = new GroupBuilder().withClasses("1-1", "1-2").build();
        private FilteredList<Lesson> list = new FilteredList<>(this.group.getLessonsAsUnmodifiableObservableList());

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            return;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            this.list.setPredicate(predicate);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return new FilteredList<Group>(FXCollections.observableList(Arrays.asList(this.group)));
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            return this.list;
        }

        @Override
        public void deleteLesson(Group targetGroup, Lesson targetLesson) {
            targetGroup.getLessons().remove(targetLesson);
        }

        @Override
        public boolean ifTargetGroupHasLessonName(GroupName groupName, LessonName lessonName) {
            ObservableList<Lesson> list = this.group.getLessons().asUnmodifiableObservableList();
            for (Lesson l : list) {
                if (l.getLessonName().equals(lessonName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean hasGroupName(GroupName toCheck) {
            return this.group.getGroupName().equals(toCheck);
        }
    }
}
