package team.serenity.logic.commands.question;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.logic.commands.question.AddQnCommand.MESSAGE_DUPLICATE_QUESTION;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_A_DESC;
import static team.serenity.testutil.question.TypicalQuestion.QUESTION_B_DESC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.question.Question;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;
import team.serenity.testutil.question.QuestionBuilder;

class AddQnCommandTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddQnCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addQnSuccessful() throws CommandException {
        ModelStubAcceptingQuestionAdded modelStub = new ModelStubAcceptingQuestionAdded();
        Question validQuestion = new QuestionBuilder().build();

        CommandResult commandResult = new AddQnCommand(validQuestion.getDescription()).execute(modelStub);

        assertEquals(String.format(AddQnCommand.MESSAGE_SUCCESS, validQuestion), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQuestion), modelStub.questionAdded);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddQnCommand addQnCommand = new AddQnCommand(validQuestion.getDescription());
        ModelStub modelStub = new ModelStubWithQuestion(validQuestion);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_QUESTION, () -> addQnCommand.execute(modelStub));
    }

    @Test
    public void execute_notViewingLesson_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddQnCommand addQnCommand = new AddQnCommand(validQuestion.getDescription());
        ModelStub modelStub = new ModelStubWithMoreThanOneLesson();

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> addQnCommand.execute(modelStub));
    }

    @Test
    public void execute_notViewingGroup_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddQnCommand addQnCommand = new AddQnCommand(validQuestion.getDescription());
        ModelStub modelStub = new ModelStubWithMoreThanOneGroup();

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> addQnCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddQnCommand addQnACommand = new AddQnCommand(QUESTION_A_DESC);
        AddQnCommand addQnBCommand = new AddQnCommand(QUESTION_B_DESC);

        // same object -> returns true
        assertTrue(addQnACommand.equals(addQnACommand));

        // same values -> returns true
        AddQnCommand addQnACommandCopy = new AddQnCommand(QUESTION_A_DESC);
        assertTrue(addQnACommandCopy.equals(addQnACommand));

        // different types -> returns false
        assertFalse(addQnACommand.equals(1));

        // null -> returns false
        assertFalse(addQnACommand.equals(null));

        // different group -> returns false
        assertFalse(addQnACommand.equals(addQnBCommand));
    }

    @Test
    public void test_hashCode() {
        AddQnCommand addQnACommand = new AddQnCommand(QUESTION_A_DESC);
        AddQnCommand addQnBCommand = new AddQnCommand(QUESTION_B_DESC);

        // Same case
        assertEquals(addQnACommand.hashCode(), addQnACommand.hashCode());

        // Different case
        assertNotEquals(addQnACommand.hashCode(), addQnBCommand.hashCode());
    }

    /**
     * A Model stub that contains a single question from default group and default lesson, and has
     * one group and lesson in the filtered group and lesson lists respectively.
     */
    private class ModelStubWithQuestion extends ModelStub {
        private final Question question;

        ModelStubWithQuestion(Question question) {
            requireNonNull(question);
            this.question = question;
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            List<Group> grpList = new ArrayList<>();
            grpList.add(new GroupBuilder().build());
            UniqueList<Group> groupUniqueList = new UniqueGroupList();
            groupUniqueList.setElementsWithList(grpList);
            return groupUniqueList.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            List<Lesson> lsnList = new ArrayList<>();
            lsnList.add(new LessonBuilder().build());
            UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
            lessonUniqueList.setElementsWithList(lsnList);
            return lessonUniqueList.asUnmodifiableObservableList();
        }

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return this.question.equals(question);
        }
    }

    /**
     * A Model stub that always accept the question being added.
     */
    private class ModelStubAcceptingQuestionAdded extends ModelStub {
        final ArrayList<Question> questionAdded = new ArrayList<>();

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            List<Group> grpList = new ArrayList<>();
            grpList.add(new GroupBuilder().build());
            UniqueList<Group> groupUniqueList = new UniqueGroupList();
            groupUniqueList.setElementsWithList(grpList);
            return groupUniqueList.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            List<Lesson> lsnList = new ArrayList<>();
            lsnList.add(new LessonBuilder().build());
            UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
            lessonUniqueList.setElementsWithList(lsnList);
            return lessonUniqueList.asUnmodifiableObservableList();
        }

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return this.questionAdded.stream().anyMatch(question::equals);
        }

        @Override
        public void addQuestion(Question question) {
            requireNonNull(question);
            this.questionAdded.add(question);
        }

        @Override
        public ReadOnlyQuestionManager getQuestionManager() {
            return new QuestionManager();
        }
    }

    /**
     * A Model stub that has one group in the filtered group list but
     * more than one lesson in the filtered lesson list.
     */
    private class ModelStubWithMoreThanOneLesson extends ModelStub {

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            List<Group> grpList = new ArrayList<>();
            grpList.add(new GroupBuilder().build());
            UniqueList<Group> groupUniqueList = new UniqueGroupList();
            groupUniqueList.setElementsWithList(grpList);
            return groupUniqueList.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            List<Lesson> lsnList = new ArrayList<>();
            lsnList.add(new LessonBuilder().build());
            lsnList.add(new LessonBuilder().withName("3-1").build());
            UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
            lessonUniqueList.setElementsWithList(lsnList);
            return lessonUniqueList.asUnmodifiableObservableList();
        }
    }

    /**
     * A Model stub that has more than one group in the filtered group list.
     */
    private class ModelStubWithMoreThanOneGroup extends ModelStub {

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            List<Group> grpList = new ArrayList<>();
            grpList.add(new GroupBuilder().build());
            grpList.add(new GroupBuilder().withName("G05").build());
            UniqueList<Group> groupUniqueList = new UniqueGroupList();
            groupUniqueList.setElementsWithList(grpList);
            return groupUniqueList.asUnmodifiableObservableList();
        }

    }

}
