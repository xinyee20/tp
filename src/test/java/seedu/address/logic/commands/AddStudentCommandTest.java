package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static seedu.address.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GrpContainsKeywordPredicate;
import seedu.address.model.group.Student;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.GroupPredicateStub;
import seedu.address.testutil.ModelStub;

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
        AddStudentCommand command = new AddStudentCommand("Jon", "e1234567", pred);

        assertThrows(CommandException.class,
            MESSAGE_GROUP_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent() throws Exception {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "e0000000"),
                new Student("June", "e0101011")
            ).withClasses(
                "4.2",
                "5.1",
                "5.2",
                "6.1"
            ).build();
        UniqueGroupList groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubWithGroup modelStub = new ModelStubWithGroup(filteredList);
        Predicate<Group> pred = new GroupPredicateStub();
        AddStudentCommand command = new AddStudentCommand("Freddie", "e0000000", pred);
        assertThrows(CommandException.class,
            MESSAGE_DUPLICATE_STUDENT, () -> command.execute(modelStub));
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
 * A GrpContainsKeywordPredicate stub
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
            || (obj instanceof GrpContainsKeywordPredicate // instanceof handles nulls
            && keyword.equals(((GroupPredicateStubWithGroupName) obj).keyword)); // state check
    }
}
