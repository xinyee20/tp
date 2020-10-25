package team.serenity.model.managers;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;

/**
 * Unmodifiable view of a Group manager
 */
public interface ReadOnlyGroupManager {
    /**
     * Returns an unmodifiable view of the group list
     * this list will not contain any duplicate groups
     */
    public ObservableList<Group> getListOfGroups();
}
