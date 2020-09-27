package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.util.SampleDataUtil.getClassSet;
import static seedu.address.model.util.SampleDataUtil.getStudentSet;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySerenity;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Serenity;
import seedu.address.model.group.Class;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class AddGrpCommandTest {

    Group validGroup = new Group("G04",
        getStudentSet(new Student("Abbie", "e0000000"),
            new Student("Baron", "e0111111"),
            new Student("Charlie", "e02222222")),
        getClassSet(new Class("1.2"), new Class("2.1")));

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGrpCommand(null));
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();

        CommandResult commandResult = new AddGrpCommand(validGroup).execute(modelStub);

        assertEquals(String.format(AddGrpCommand.MESSAGE_SUCCESS, validGroup), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        AddGrpCommand addGrpCommand = new AddGrpCommand(validGroup);
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        assertThrows(CommandException.class, AddGrpCommand.MESSAGE_DUPLICATE_PERSON, () -> addGrpCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Group G04 = new Group("G04",
            getStudentSet(new Student("Abbie", "e0000000"),
                new Student("Baron", "e0111111"),
                new Student("Charlie", "e02222222")),
            getClassSet(new Class("1.2"), new Class("2.1")));
        Group G05 = new Group("G05",
            getStudentSet(new Student("Alicia", "e0000000")),
            getClassSet(new Class("2.2"), new Class("3.1")));
        AddGrpCommand addG04Command = new AddGrpCommand(G04);
        AddGrpCommand addG05Command = new AddGrpCommand(G05);

        // same object -> returns true
        assertTrue(addG04Command.equals(addG04Command));

        // same values -> returns true
        AddGrpCommand addG04CommandCopy = new AddGrpCommand(G04);
        assertTrue(addG04Command.equals(addG04CommandCopy));

        // different types -> returns false
        assertFalse(addG04Command.equals(1));

        // null -> returns false
        assertFalse(addG04Command.equals(null));

        // different person -> returns false
        assertFalse(addG04Command.equals(addG05Command));
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
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
     * A Model stub that always accept the person being added.
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
