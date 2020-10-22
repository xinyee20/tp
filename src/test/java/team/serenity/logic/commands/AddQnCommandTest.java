package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalQuestions.QUESTION_1;
import static team.serenity.testutil.TypicalQuestions.QUESTION_2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.Question;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

class AddQnCommandTest {

    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddQnCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addQnSuccessful() throws CommandException {
        // TODO: Complete after LessonBuilder is implemented
        /*
             ModelStubAcceptingQuestionAdded modelStub = new ModelStubAcceptingQuestionAdded();
             Question validQuestion = new QuestionBuilder().build();

             CommandResult commandResult = new AddQnCommand(validQuestion).execute(modelStub);

             assertEquals(String.format(AddQnCommand.MESSAGE_SUCCESS, validQuestion),
                     commandResult.getFeedbackToUser());
             assertEquals(Arrays.asList(validQuestion), modelStub.questionAdded);
         */
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        // TODO: Complete after LessonBuilder is implemented
        /*
            Question validQuestion = new QuestionBuilder().build();
            AddQnCommand addQnCommand = new AddQnCommand(validQuestion);
            ModelStub modelStub = new ModelStubWithQuestion(validQuestion);

            Assertions.assertThrows(CommandException.class, () -> addQnCommand.execute(modelStub));
         */
    }

    @Test
    public void equals() {
        AddQnCommand addQnACommand = new AddQnCommand(QUESTION_1);
        AddQnCommand addQnBCommand = new AddQnCommand(QUESTION_2);

        // same object -> returns true
        assertTrue(addQnACommand.equals(addQnACommand));

        // same values -> returns true
        AddQnCommand addQnACommandCopy = new AddQnCommand(QUESTION_1);
        assertTrue(addQnACommandCopy.equals(addQnACommand));

        // different types -> returns false
        assertFalse(addQnACommand.equals(1));

        // null -> returns false
        assertFalse(addQnACommand.equals(null));

        // different group -> returns false
        assertFalse(addQnACommand.equals(addQnBCommand));
    }

    /**
     * A Model stub that contains a single question from default group and default lesson.
     */
    private class ModelStubWithQuestion extends ModelStub {
        private final Question question;

        ModelStubWithQuestion(Question question) {
            requireNonNull(question);
            this.question = question;
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

        // TODO: Complete after LessonBuilder is implemented
        /*
        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            List<Lesson> lsnList = new ArrayList<>();
            lsnList.add(new LessonBuilder().build());
            UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
            lessonUniqueList.setElementsWithList(lsnList);
            return lessonUniqueList.asUnmodifiableObservableList();
        }
         */

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

}
