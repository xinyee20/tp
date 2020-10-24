package team.serenity.model.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalGroups.GROUP_C;
import static team.serenity.testutil.TypicalGroups.GROUP_D;
import static team.serenity.testutil.TypicalGroups.getTypicalGroupManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.exceptions.DuplicateGroupException;
import team.serenity.model.group.exceptions.GroupNotFoundException;

class GroupManagerTest {

    private final GroupManager groupManager = new GroupManager();
    private final ReadOnlyGroupManager readOnlyGroupManager = new GroupManager();

    @Test
    public void testConstructorNoParams() {
        assertEquals(Collections.emptyList(), this.groupManager.getListOfGroups());
    }

    @Test
    public void testConstructorWithParams() {
        GroupManager actual = new GroupManager(this.readOnlyGroupManager);
        assertEquals(Collections.emptyList(), actual.getListOfGroups());
    }

    @Test
    public void resetDataWithNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyGroupManager() {
        GroupManager newData = getTypicalGroupManager();
        this.groupManager.resetData(newData);
        assertEquals(newData, this.groupManager);
    }

    @Test
    public void resetDataWithDuplicateGroupThrowsDuplicateQuestionException() {
        List<Group> newGroups = Arrays.asList(GROUP_C, GROUP_C);
        GroupManagerStub newData = new GroupManagerStub(newGroups);
        assertThrows(DuplicateGroupException.class, () -> this.groupManager.resetData(newData));
    }

    @Test
    public void hasGroupNullGroupThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.hasGroup(null));
    }

    @Test
    public void hasGroupNotInGroupManagerReturnFalse() {
        assertFalse(this.groupManager.hasGroup(GROUP_D));
    }

    @Test
    public void hasGroupInGroupManagerReturnsTrue() {
        this.groupManager.addGroup(GROUP_D);
        assertTrue(this.groupManager.hasGroup(GROUP_D));
    }

    @Test
    public void deleteGroupNullGroupThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.deleteGroup(null));
    }

    @Test
    public void deleteGroupNotInGroupManagerThrowsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> this.groupManager.deleteGroup(GROUP_D));
    }

    @Test
    public void deleteGroupInGroupManager() {
        this.groupManager.addGroup(GROUP_D);
        assertTrue(this.groupManager.hasGroup(GROUP_D));
        this.groupManager.deleteGroup(GROUP_D);
        assertFalse(this.groupManager.hasGroup(GROUP_D));
    }

    @Test
    public void getGroupModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> this.groupManager
                .getListOfGroups().remove(0));
    }

    /**
     * A stub ReadOnlyGroupManager whose group list can violate interface constraints.
     */
    private static class GroupManagerStub implements ReadOnlyGroupManager {
        private final ObservableList<Group> groupList = FXCollections.observableArrayList();

        GroupManagerStub(Collection<Group> groups) {
            this.groupList.setAll(groups);
        }

        @Override
        public ObservableList<Group> getListOfGroups() {
            return this.groupList;
        }
    }
}
