package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.ReadOnlyUserPrefs;
import team.serenity.model.Serenity;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.testutil.GroupBuilder;

class AddGrpCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGrpCommand(null));
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = new AddGrpCommand(validGroup).execute(modelStub);

        assertEquals(String.format(AddGrpCommand.MESSAGE_SUCCESS, validGroup), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group validGroup = new GroupBuilder().build();
        AddGrpCommand addGrpCommand = new AddGrpCommand(validGroup);
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        assertThrows(CommandException.class,
            AddGrpCommand.MESSAGE_DUPLICATE_GROUP, () -> addGrpCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Group groupA = new GroupBuilder().withName("G04").build();
        Group groupB = new GroupBuilder().withName("G05").build();
        AddGrpCommand addGroupACommand = new AddGrpCommand(groupA);
        AddGrpCommand addGroupBCommand = new AddGrpCommand(groupB);

        // same object -> returns true
        assertTrue(addGroupACommand.equals(addGroupACommand));

        // same values -> returns true
        AddGrpCommand addG04CommandCopy = new AddGrpCommand(groupA);
        assertTrue(addGroupACommand.equals(addG04CommandCopy));

        // different types -> returns false
        assertFalse(addGroupACommand.equals(1));

        // null -> returns false
        assertFalse(addGroupACommand.equals(null));

        // different group -> returns false
        assertFalse(addGroupACommand.equals(addGroupBCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getSerenityFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSerenityFilePath(Path serenityFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSerenity(ReadOnlySerenity serenity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySerenity getSerenity() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStudentInfoList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudentToGroup(Student student, Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeStudentFromGroup(Student student, Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Student> getStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<StudentInfo> getStudentInfoList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Question> getQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends AddGrpCommandTest.ModelStub {

        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return this.group.isSameGroup(group);
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends AddGrpCommandTest.ModelStub {

        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return groupsAdded.stream().anyMatch(group::isSameGroup);
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public ReadOnlySerenity getSerenity() {
            return new Serenity();
        }
    }
}
