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
import team.serenity.logic.commands.lesson.AddLsnCommand;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;

class AddLsnCommandTest {

    @Test
    void execute_targetGroupDoesNotExist_throwsCommandException() throws CommandException {
        AddLsnCommand test = new AddLsnCommand(new LessonName("1-1"), new GroupContainsKeywordPredicate("G05"),
            new QuestionFromGroupLessonPredicate(new GroupName("G05"), new LessonName("1-1")));
        assertThrows(CommandException.class, () -> test.execute(new ModelStubWithGroupList()));
    }

    @Test
    void execute_targetDuplicateLesson_throwsCommandException() {
        AddLsnCommand test = new AddLsnCommand(new LessonName("1-1"), new GroupContainsKeywordPredicate("G02"),
            new QuestionFromGroupLessonPredicate(new GroupName("G02"), new LessonName("1-1")));
        assertThrows(CommandException.class, () -> test.execute(new ModelStubWithGroupList()));
    }

    @Test
    void execute_newLesson() {
        Model actual = new ModelStubWithGroupList();
        Lesson validLesson = new LessonBuilder().withName("1-2").build();
        AddLsnCommand test = new AddLsnCommand(validLesson.getLessonName(), new GroupContainsKeywordPredicate("G02"),
            new QuestionFromGroupLessonPredicate(new GroupName("G02"), validLesson.getLessonName()));
        try {
            CommandResult result = test.execute(actual);
            assertEquals(
                    String.format(AddLsnCommand.MESSAGE_SUCCESS, validLesson, actual.getFilteredGroupList().get(0)),
                    result.getFeedbackToUser()
            );
            assertEquals(validLesson, actual.getFilteredLessonList().get(0));
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }


    }

    private class ModelStubWithGroupList extends ModelStub {

        private ObservableList<Group> list = FXCollections.observableList(Arrays.asList(
                new GroupBuilder().withName("G02").withLessons("1-1").build())
        );
        private FilteredList<Group> filteredList = new FilteredList<>(this.list);
        private FilteredList<Lesson> filteredLessonList =
                new FilteredList<>(this.list.get(0).getLessonsAsUnmodifiableObservableList());

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredList;
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            return this.filteredLessonList;
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredList.setPredicate(predicate);
        }

        @Override
        public void updateLessonList() {

        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            this.filteredLessonList.setPredicate(predicate);
        }

        @Override
        public void updateFilteredQuestionList(Predicate<Question> predicate) {

        }
    }


}
