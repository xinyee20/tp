package seedu.address.model.serenity;

import java.util.ArrayList;
import java.util.List;

public class GroupList {

    private List<Group> groups;

    public GroupList() {
        groups = new ArrayList<>();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
