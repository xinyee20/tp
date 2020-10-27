package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_GROUP_NAME_FORMAT;
import static team.serenity.logic.commands.AddGrpCommand.MESSAGE_DUPLICATE_STUDENT_FORMAT;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalStudent.JAMES;
import static team.serenity.testutil.TypicalStudent.JEFFERY;
import static team.serenity.testutil.TypicalStudent.JOHN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.ModelStub;

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
    public void execute_duplicateGroupName_throwsCommandException() {
        Group validGroup = new GroupBuilder().build();
        ModelStub modelStub = new ModelStubWithGroup(validGroup);
        String expectedMessage = String.format(MESSAGE_DUPLICATE_GROUP_NAME_FORMAT,
                validGroup.getGroupName().groupName);

        AddGrpCommand addGrpCommand = new AddGrpCommand(validGroup);

        assertThrows(CommandException.class, expectedMessage, () -> addGrpCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Group validGroup = new GroupBuilder().withName("G01").withStudents(JOHN, JAMES, JEFFERY).build();
        ModelStub modelStub = new ModelStubWithGroup(validGroup);
        String expectedMessage = String.format(MESSAGE_DUPLICATE_STUDENT_FORMAT,
                JOHN.getStudentName(), JOHN.getStudentNo());

        Group invalidGroup = new GroupBuilder(validGroup).withName("G02").build();
        AddGrpCommand addGrpCommand = new AddGrpCommand(invalidGroup);

        assertThrows(CommandException.class, expectedMessage, () -> addGrpCommand.execute(modelStub));
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
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends ModelStub {

        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
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
    private class ModelStubAcceptingGroupAdded extends ModelStub {

        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public boolean hasGroupName(GroupName toCheck) {
            return false;
        }

        @Override
        public boolean hasStudent(Student toCheck) {
            return false;
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            // update filtered group list
        }
    }
}
