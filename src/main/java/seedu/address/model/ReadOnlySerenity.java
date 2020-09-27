package seedu.address.model;

import java.util.List;

import seedu.address.model.group.Group;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlySerenity {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    List<Group> getGroupList();

}
