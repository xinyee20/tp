package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.logic.commands.student.AddStudentCommand.MESSAGE_SUCCESS;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.commands.student.AddStudentCommand;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.student.Student;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.GroupPredicateStub;
import team.serenity.testutil.ModelStub;

public class AddStudentCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand("", "", null));
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null, "", new GroupPredicateStub()));
        assertThrows(NullPointerException.class, () -> new AddStudentCommand("", null, new GroupPredicateStub()));
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null, null, null));
    }

    @Test
    public void execute_missingGroup() throws Exception {
        ModelStubWithoutGroup modelStub = new ModelStubWithoutGroup();
        Predicate<Group> pred = new GroupPredicateStub();
        AddStudentCommand command = new AddStudentCommand("Jon", "A1234567U", pred);

        assertThrows(CommandException.class,
            MESSAGE_GROUP_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent() {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U"),
                new Student("June", "A0101011U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubWithGroup modelStub = new ModelStubWithGroup(filteredList);
        Predicate<Group> pred = new GroupPredicateStub();
        AddStudentCommand command = new AddStudentCommand("Freddie", "A0000000U", pred);
        assertThrows(CommandException.class,
            MESSAGE_DUPLICATE_STUDENT, () -> command.execute(modelStub));
    }

    @Test
    public void execute_refreshesUi() throws CommandException {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U"),
                new Student("June", "A0101011U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubWithGroup modelStub = new ModelStubWithGroup(filteredList);
        Predicate<Group> pred = new GroupPredicateStub();
        AddStudentCommand command = new AddStudentCommand("John", "A1234567U", pred);
        CommandResult result = command.execute(modelStub);
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_SUCCESS, "John", "A1234567U", "G07"),
                CommandResult.UiAction.REFRESH_TABLE);
        assertTrue(result.equals(expectedResult));
    }

    @Test
    public void execute_sameNameDifferentMatric() {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "A0000000U"),
                new Student("June", "A0101011U")
            ).withClasses("4-2", "5-1", "5-2", "6-1")
            .build();
        UniqueList<Group> groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubWithGroup modelStub = new ModelStubWithGroup(filteredList);
        Predicate<Group> pred = new GroupPredicateStub();
        AddStudentCommand command = new AddStudentCommand("Freddie", "A1234567U", pred);
        assertDoesNotThrow(() -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        String studentName = "John";
        String studentId = "e1234567";
        Predicate<Group> pred = new GroupPredicateStubWithGroupName("G04");
        AddStudentCommand first = new AddStudentCommand(studentName, studentId, pred);
        AddStudentCommand second = new AddStudentCommand(studentName,
            studentId, pred);
        AddStudentCommand differentStudentName = new AddStudentCommand("J", studentId, pred);
        AddStudentCommand differentStudentId = new AddStudentCommand(studentName, "e111", pred);
        AddStudentCommand differentPredicate = new AddStudentCommand(studentName, studentId,
            new GroupPredicateStubWithGroupName("G05"));
        // same object -> returns true
        assertTrue(first.equals(first));

        //same values -> return true
        assertTrue(first.equals(second));

        //different values -> return false
        assertFalse(first.equals(differentStudentName));
        assertFalse(first.equals(differentStudentId));
        assertFalse(first.equals(differentPredicate));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));
    }
}

/**
 * A Model stub with a group
 */
class ModelStubWithGroup extends ModelStub {

    private final FilteredList<Group> filteredGroups;

    ModelStubWithGroup(FilteredList<Group> filteredList) {
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
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        return;
    }
}

/**
 * A Model stub that does not contain any group
 */
class ModelStubWithoutGroup extends ModelStub {

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        return;
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return new UniqueGroupList().asUnmodifiableObservableList();
    }
}

/**
 * A GroupContainsKeywordPredicate stub
 */
class GroupPredicateStubWithGroupName implements Predicate<Group> {

    private final String keyword;

    GroupPredicateStubWithGroupName(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Group group) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || (obj instanceof GroupContainsKeywordPredicate // instanceof handles nulls
            && keyword.equals(((GroupPredicateStubWithGroupName) obj).keyword)); // state check
    }
}
