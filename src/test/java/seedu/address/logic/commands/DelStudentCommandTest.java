package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.GroupPredicateStub;
import seedu.address.testutil.ModelStub;

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
        DelStudentCommand command = new DelStudentCommand("Jon", "e1234567", pred);

        assertThrows(CommandException.class,
            MESSAGE_GROUP_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void execute_missingStudent() throws Exception {
        Group stubGroup = new GroupBuilder().withName("G07")
            .withStudents(
                new Student("Freddie", "e0000000")
                ).withClasses(
                "4.2",
                "5.1",
                "5.2",
                "6.1"
            ).build();
        UniqueGroupList groupList = new UniqueGroupList();
        groupList.add(stubGroup);
        FilteredList<Group> filteredList = new FilteredList<>(groupList.asUnmodifiableObservableList());
        ModelStubGroup modelStub = new ModelStubGroup(filteredList);
        GroupPredicateStub pred = new GroupPredicateStub();
        DelStudentCommand command = new DelStudentCommand("June", "e1234567", pred);
        assertThrows(CommandException.class,
            MESSAGE_STUDENT_EMPTY, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        String studentName = "John";
        String studentId = "e1234567";
        GroupPredicateStub pred = new GroupPredicateStub();
        DelStudentCommand first = new DelStudentCommand(studentName, studentId, pred);
        DelStudentCommand second = new DelStudentCommand(studentName, studentId, pred);
        DelStudentCommand differentName = new DelStudentCommand("James", studentId, pred);
        DelStudentCommand differentId = new DelStudentCommand(studentName, "e7654321", pred);
        DelStudentCommand differentPred = new DelStudentCommand(studentName, studentId, new GroupPredicateStub());

        // same object -> returns true
        assertTrue(first.equals(first));

        //same values -> return true
        assertTrue(first.equals(second));

        //different values -> return false
        assertFalse(first.equals(differentName));
        assertFalse(first.equals(differentId));
        assertFalse(first.equals(differentPred));

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
}


