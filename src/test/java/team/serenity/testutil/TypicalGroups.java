package team.serenity.testutil;

import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_A;
import static team.serenity.testutil.TypicalStudent.FREDDIE;
import static team.serenity.testutil.TypicalStudent.JEFFERY;
import static team.serenity.testutil.TypicalStudent.JUNE;
import static team.serenity.testutil.TypicalStudent.LUNA;
import static team.serenity.testutil.TypicalStudent.QUEENIE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.serenity.logic.commands.CommandTestUtil;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.managers.GroupManager;
import team.serenity.model.managers.Serenity;
import team.serenity.model.util.UniqueList;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group GROUP_C = new GroupBuilder().withName("G01")
        .withStudents(JEFFERY, LUNA, QUEENIE)
        .withClasses("4-2", "5-1")
        .build();

    public static final Group GROUP_D = new GroupBuilder().withName("G02")
        .withStudents(FREDDIE, JUNE)
        .withClasses("4-2", "5-1", "5-2", "6-1")
        .build();

    // Manually added - Group's details found in {@code CommandTestUtil}
    public static final Group GROUP_A = new GroupBuilder().withName(CommandTestUtil.VALID_GROUP_NAME_A)
        .withFilePath(VALID_PATH_A).build();
    //    public static final Group GROUP_B = new GroupBuilder().withName(CommandTestUtil.VALID_GRP_GROUP_B)
    //        .withFilePath(CommandTestUtil.VALID_PATH_GROUP_B).build();

    private TypicalGroups() {
    } // prevents instantiation

    /**
     * Returns an {@code Serenity} with all the typical groups.
     */
    public static Serenity getTypicalSerenity() {
        UniqueList<Group> groups = new UniqueGroupList();
        for (Group group : getTypicalGroups()) {
            groups.add(group);
        }
        Serenity serenity = new Serenity(groups.asUnmodifiableObservableList());
        return serenity;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP_A, GROUP_C, GROUP_D));
    }

    public static GroupManager getTypicalGroupManager() {
        GroupManager groupManager = new GroupManager();
        groupManager.addGroup(GROUP_C);
        groupManager.addGroup(GROUP_D);
        return groupManager;
    }
}
