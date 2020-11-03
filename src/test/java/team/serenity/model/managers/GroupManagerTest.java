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
    public void constructor_noParams() {
        assertEquals(Collections.emptyList(), this.groupManager.getListOfGroups());
    }

    @Test
    public void constructor_withParams() {
        GroupManager actual = new GroupManager(this.readOnlyGroupManager);
        assertEquals(Collections.emptyList(), actual.getListOfGroups());
    }

    @Test
    public void resetData_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroupManager() {
        GroupManager newData = getTypicalGroupManager();
        this.groupManager.resetData(newData);
        assertEquals(newData, this.groupManager);
    }

    @Test
    public void resetData_withDuplicateGroup_throwsDuplicateQuestionException() {
        List<Group> newGroups = Arrays.asList(GROUP_C, GROUP_C);
        GroupManagerStub newData = new GroupManagerStub(newGroups);
        assertThrows(DuplicateGroupException.class, () -> this.groupManager.resetData(newData));
    }

    @Test
    public void isEmpty_noGroupsInGroupManager_returnFalse() {
        assertTrue(this.groupManager.isEmpty());
    }

    @Test
    public void isEmpty_groupsInGroupManager_returnsTrue() {
        this.groupManager.addGroup(GROUP_D);
        assertFalse(this.groupManager.isEmpty());
    }

    @Test
    public void hasGroupName_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.hasGroupName(null));
    }

    @Test
    public void hasGroupName_notInGroupManager_returnFalse() {
        assertFalse(this.groupManager.hasGroupName(GROUP_D.getGroupName()));
    }

    @Test
    public void hasGroupName_groupInGroupManager_returnsTrue() {
        this.groupManager.addGroup(GROUP_D);
        assertTrue(this.groupManager.hasGroupName(GROUP_D.getGroupName()));
    }

    @Test
    public void isValidGroupToAdd_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.isValidGroupToAdd(null));
    }

    @Test
    public void isValidGroupToAdd_notInGroupManager_returnFalse() {
        assertTrue(this.groupManager.isValidGroupToAdd(GROUP_D));
    }

    @Test
    public void isValidGroupToAdd_groupInGroupManager_returnsTrue() {
        this.groupManager.addGroup(GROUP_D);
        assertFalse(this.groupManager.isValidGroupToAdd(GROUP_D));
    }

    @Test
    public void deleteGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> this.groupManager.deleteGroup(null));
    }

    @Test
    public void deleteGroup_groupNotInGroupManager_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> this.groupManager.deleteGroup(GROUP_D));
    }

    @Test
    public void deleteGroup_groupInGroupManager() {
        this.groupManager.addGroup(GROUP_D);
        assertFalse(this.groupManager.isValidGroupToAdd(GROUP_D));
        this.groupManager.deleteGroup(GROUP_D);
        assertTrue(this.groupManager.isValidGroupToAdd(GROUP_D));
    }

    @Test
    public void getGroup_modifyList_throwsUnsupportedOperationException() {
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
