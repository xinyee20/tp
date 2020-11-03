package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static team.serenity.logic.commands.student.DelStudentCommand.MESSAGE_SUCCESS;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.commands.student.DelStudentCommand;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.GroupPredicateStub;
import team.serenity.testutil.ModelStub;

public class DelStudentCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DelStudentCommand("", "", null));
        assertThrows(NullPointerException.class, () -> new DelStudentCommand(null, "", new GroupPredicateStub()));
        assertThrows(NullPointerException.class, () -> new DelStudentCommand("", null, new GroupPredicateStub()));
        assertThrows(NullPointerException.class, () -> new DelStudentCommand(null, null, null));
    }

    @Test
    public void execute_missingGroup() throws Exception {
        ModelStubWithoutGroup modelStub = new ModelStubWithoutGroup();
        Predicate<Group> pred = new GroupPredicateStub();
        DelStudentCommand command = new DelStudentCommand("Jon", "A1234567U", pred);

        assertThrows(CommandException.class,
            MESSAGE_GROUP_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void execute_missingStudent() {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubGroup modelStub = new ModelStubGroup(filteredList);
        GroupPredicateStub pred = new GroupPredicateStub();
        DelStudentCommand command = new DelStudentCommand("June", "A1234567U", pred);
        assertThrows(CommandException.class,
            MESSAGE_STUDENT_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void execute_refreshesUi() throws CommandException {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubGroup modelStub = new ModelStubGroup(filteredList);
        GroupPredicateStub pred = new GroupPredicateStub();
        DelStudentCommand command = new DelStudentCommand("Freddie", "A0000000U", pred);
        CommandResult result = command.execute(modelStub);
        CommandResult expectedResult = new CommandResult(
            String.format(MESSAGE_SUCCESS, "FREDDIE", "A0000000U", "G07"), CommandResult.UiAction.REFRESH_TABLE
        );
        assertTrue(result.equals(expectedResult));
    }

    @Test
    public void execute_sameStudentInCaps() {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubGroup modelStub = new ModelStubGroup(filteredList);
        GroupPredicateStub pred = new GroupPredicateStub();
        DelStudentCommand command = new DelStudentCommand("FREDDIE", "A0000000U", pred);
        assertDoesNotThrow(() -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        String studentName = "John";
        String studentId = "A1234567U";
        GroupPredicateStub pred = new GroupPredicateStub();
        Index index = Index.fromOneBased(Integer.parseInt("1"));

        DelStudentCommand first = new DelStudentCommand(studentName, studentId, pred);
        DelStudentCommand second = new DelStudentCommand(studentName, studentId, pred);
        DelStudentCommand third = new DelStudentCommand(index, pred);
        DelStudentCommand fourth = new DelStudentCommand(index, pred);
        DelStudentCommand differentName = new DelStudentCommand("James", studentId, pred);
        DelStudentCommand differentId = new DelStudentCommand(studentName, "A7654321U", pred);
        DelStudentCommand differentPred = new DelStudentCommand(studentName, studentId, new GroupPredicateStub());
        DelStudentCommand differentIndex = new DelStudentCommand(Index.fromOneBased(Integer.parseInt("2")), pred);

        // same object -> returns true
        assertTrue(first.equals(first));

        //same values -> return true
        assertTrue(first.equals(second));
        assertTrue(third.equals(fourth));

        //different values -> return false
        assertFalse(first.equals(differentName));
        assertFalse(first.equals(differentId));
        assertFalse(first.equals(differentPred));
        assertFalse(third.equals(differentIndex));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));
    }
}

/**
 * A Model stub with a group
 */
class ModelStubGroup extends ModelStub {

    private final FilteredList<Group> filteredGroups;

    ModelStubGroup(FilteredList<Group> filteredList) {
        this.filteredGroups = filteredList;
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        return;
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public void deleteStudentFromGroup(Student student, Predicate<Group> predicate) {
        return;
    }
}


