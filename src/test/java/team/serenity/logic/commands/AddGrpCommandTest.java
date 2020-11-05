package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_GROUP_NAME_FORMAT;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_STUDENT_FORMAT;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalStudent.JAMES;
import static team.serenity.testutil.TypicalStudent.JEFFERY;
import static team.serenity.testutil.TypicalStudent.JOHN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;

class AddGrpCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGrpCommand(null));
    }

    @Test
    public void execute_groupAcceptedByModel_addGrpSuccessful() throws CommandException {
        Group validGroup = new GroupBuilder().build();
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        CommandResult commandResult = new AddGrpCommand(validGroup).execute(modelStub);

        String expectedMessage = String.format(AddGrpCommand.MESSAGE_SUCCESS, validGroup);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validGroup), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicateGroupName_throwsCommandException() {
        Group validGroup = new GroupBuilder().build();
        ModelStub modelStub = new ModelStubWithGroup(validGroup);
        AddGrpCommand addGrpCommand = new AddGrpCommand(validGroup);

        String expectedMessage = String.format(MESSAGE_DUPLICATE_GROUP_NAME_FORMAT,
            validGroup.getGroupName().groupName);

        assertThrows(CommandException.class, expectedMessage, () -> addGrpCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Group validGroup = new GroupBuilder().withName("G01").withStudents(JOHN, JAMES, JEFFERY).build();
        Group invalidGroup = new GroupBuilder(validGroup).withName("G02").build();
        ModelStub modelStub = new ModelStubWithGroup(validGroup);
        AddGrpCommand addGrpCommand = new AddGrpCommand(invalidGroup);

        String expectedMessage = String.format(MESSAGE_DUPLICATE_STUDENT_FORMAT,
            JOHN.getStudentName(), JOHN.getStudentNo());

        assertThrows(CommandException.class, expectedMessage, () -> addGrpCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Group groupA = new GroupBuilder().withName("G04").build();
        Group groupB = new GroupBuilder().withName("G05").build();
        AddGrpCommand addGroupACommand = new AddGrpCommand(groupA);
        AddGrpCommand addGroupBCommand = new AddGrpCommand(groupB);

        // same object -> returns true
        assertEquals(addGroupACommand, addGroupACommand);

        // same values -> returns true
        AddGrpCommand addG04CommandCopy = new AddGrpCommand(groupA);
        assertEquals(addGroupACommand, addG04CommandCopy);

        // different types -> returns false
        assertNotEquals(1, addGroupACommand);

        // null -> returns false
        assertNotEquals(null, addGroupACommand);

        // different group -> returns false
        assertNotEquals(addGroupACommand, addGroupBCommand);
    }

    @Test
    public void test_hashCode() {
        Group groupA = new GroupBuilder().withName("G04").build();
        Group groupB = new GroupBuilder().withName("G05").build();
        AddGrpCommand addGroupACommand = new AddGrpCommand(groupA);
        AddGrpCommand addGroupBCommand = new AddGrpCommand(groupB);

        // Same case
        assertEquals(addGroupACommand.hashCode(), addGroupACommand.hashCode());

        // Different case
        assertNotEquals(addGroupACommand.hashCode(), addGroupBCommand.hashCode());
    }

    /**
     * A Model stub that contains a single group.
     */
    private static class ModelStubWithGroup extends ModelStub {

        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
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
        public boolean hasGroupName(GroupName toCheck) {
            requireNonNull(toCheck);
            return this.group.getGroupName().equals(toCheck);
        }

        @Override
        public boolean hasStudent(Student toCheck) {
            requireNonNull(toCheck);
            return this.group.getStudents().contains(toCheck);
        }

    }

    /**
     * A Model stub that always accept the group being added.
     */
    private static class ModelStubAcceptingGroupAdded extends ModelStub {

        final ArrayList<Group> groupsAdded = new ArrayList<>();

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
        public boolean hasGroupName(GroupName toCheck) {
            requireNonNull(toCheck);
            return false;
        }

        @Override
        public boolean hasStudent(Student toCheck) {
            requireNonNull(toCheck);
            return false;
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            requireNonNull(predicate);
        }
    }
}
