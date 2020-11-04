package team.serenity.logic.commands.studentinfo;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.ModelStub;

/**
 * A Model stub containing no group
 */
class ModelStubWithNoGroup extends ModelStub {

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        List<Group> grpList = new ArrayList<>();
        UniqueList<Group> groupUniqueList = new UniqueGroupList();
        groupUniqueList.setElementsWithList(grpList);
        return groupUniqueList.asUnmodifiableObservableList();
    }
}
