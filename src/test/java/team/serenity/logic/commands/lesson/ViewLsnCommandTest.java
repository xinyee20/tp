package team.serenity.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_ASSERTION_ERROR_METHOD;
import static team.serenity.commons.core.Messages.MESSAGE_LESSON_LISTED_OVERVIEW;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G01;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G02;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_1;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_2;
import static team.serenity.logic.commands.lesson.ViewLsnCommand.GROUP_DOES_NOT_EXIST_MESSAGE;
import static team.serenity.logic.commands.lesson.ViewLsnCommand.LESSON_DOES_NOT_EXIST_MESSAGE;
import static team.serenity.testutil.Assert.assertThrows;

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
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;
import team.serenity.testutil.question.QuestionBuilder;

class ViewLsnCommandTest {

    @Test
    void execute_noGroup_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithNoGroupAndLesson();
        ViewLsnCommand viewLsnCommand = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G01),
                        new LessonName(VALID_LESSON_NAME_1_1)
                ));
        assertThrows(CommandException.class, GROUP_DOES_NOT_EXIST_MESSAGE, () -> viewLsnCommand.execute(modelStub));
    }

    @Test
    void execute_noLesson_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithNoLesson();
        ViewLsnCommand viewLsnCommand = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G01),
                        new LessonName(VALID_LESSON_NAME_1_1)
                ));
        assertThrows(CommandException.class, LESSON_DOES_NOT_EXIST_MESSAGE, () -> viewLsnCommand.execute(modelStub));
    }

    @Test
    void execute_containsGroupAndLesson_success() {
        try {
            ModelStubWithGroupAndLesson modelStub = new ModelStubWithGroupAndLesson();
            ViewLsnCommand viewLsnCommand = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01),
                    new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1),
                    new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G01),
                            new LessonName(VALID_LESSON_NAME_1_1)
                    ));
            CommandResult actual = viewLsnCommand.execute(modelStub);
            assertEquals(
                    String.format(MESSAGE_LESSON_LISTED_OVERVIEW,
                            modelStub.getFilteredGroupList().get(0).getGroupName(),
                            modelStub.getFilteredLessonList().get(0).getLessonName()),
                    actual.getFeedbackToUser()
            );
        } catch (CommandException e) {
            throw new AssertionError(MESSAGE_ASSERTION_ERROR_METHOD, e);

        }
    }

    @Test
    void equals() {
        GroupContainsKeywordPredicate grpPredicateA = new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01);
        GroupContainsKeywordPredicate grpPredicateB = new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G02);
        LessonContainsKeywordPredicate lsnPredicateA = new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1);
        LessonContainsKeywordPredicate lsnPredicateB = new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_2);
        QuestionFromGroupLessonPredicate qnPredicateA = new QuestionFromGroupLessonPredicate(
                new GroupName(VALID_GROUP_NAME_G01), new LessonName(VALID_LESSON_NAME_1_1));
        QuestionFromGroupLessonPredicate qnPredicateB = new QuestionFromGroupLessonPredicate(
                new GroupName(VALID_GROUP_NAME_G02), new LessonName(VALID_LESSON_NAME_1_2));
        ViewLsnCommand viewLsnCommandA = new ViewLsnCommand(grpPredicateA, lsnPredicateA, qnPredicateA);
        ViewLsnCommand viewLsnCommandG = new ViewLsnCommand(grpPredicateB, lsnPredicateB, qnPredicateB);

        // same object -> returns true
        assertTrue(viewLsnCommandA.equals(viewLsnCommandA));

        // same values -> returns true
        ViewLsnCommand viewLsnCommandACopy = new ViewLsnCommand(grpPredicateA, lsnPredicateA, qnPredicateA);
        assertTrue(viewLsnCommandACopy.equals(viewLsnCommandA));

        // different types -> returns false
        assertFalse(viewLsnCommandA.equals(1));

        // null -> returns false
        assertFalse(viewLsnCommandA.equals(null));

        // different group -> returns false
        assertFalse(viewLsnCommandA.equals(viewLsnCommandG));
    }

    private static class ModelStubWithGroupAndLesson extends ModelStub {
        private ObservableList<Group> groupList =
                FXCollections.observableList(Collections.singletonList(new GroupBuilder().build()));
        private FilteredList<Group> filteredGroupList = new FilteredList<>(groupList);
        private ObservableList<Lesson> lessonList =
                FXCollections.observableList(Collections.singletonList(new LessonBuilder().build()));
        private FilteredList<Lesson> filteredLessonList = new FilteredList<>(lessonList);
        private ObservableList<Question> questionList =
                FXCollections.observableList(Collections.singletonList(new QuestionBuilder().build()));
        private FilteredList<Question> filteredQuestionList = new FilteredList<>(questionList);


        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            return this.filteredLessonList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            this.filteredLessonList.setPredicate(predicate);
        }

        @Override
        public void updateFilteredQuestionList(Predicate<Question> predicate) {
            this.filteredQuestionList.setPredicate(predicate);
        }
    }

    private static class ModelStubWithNoGroupAndLesson extends ModelStub {
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
        public void updateFilteredQuestionList(Predicate<Question> predicate) {}
    }

    private static class ModelStubWithNoLesson extends ModelStub {
        private ObservableList<Group> groupList =
                FXCollections.observableList(Collections.singletonList(new GroupBuilder().build()));;
        private FilteredList<Group> filteredGroupList = new FilteredList<>(groupList);
        private ObservableList<Lesson> lessonList = FXCollections.observableList(Collections.EMPTY_LIST);
        private FilteredList<Lesson> filteredLessonList = new FilteredList<>(lessonList);
        private ObservableList<Question> questionList =
                FXCollections.observableList(Collections.singletonList(new QuestionBuilder().build()));
        private FilteredList<Question> filteredQuestionList = new FilteredList<>(questionList);

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return this.filteredGroupList;
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            this.filteredGroupList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            return this.filteredLessonList;
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            this.filteredLessonList.setPredicate(predicate);
        }

        @Override
        public void updateFilteredQuestionList(Predicate<Question> predicate) {
            this.filteredQuestionList.setPredicate(predicate);
        }
    }

}
