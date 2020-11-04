package team.serenity.testutil;

import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_G04;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;
import static team.serenity.testutil.TypicalStudent.CATHERINE;
import static team.serenity.testutil.TypicalStudent.DAVID;
import static team.serenity.testutil.TypicalStudent.ELFIE;
import static team.serenity.testutil.TypicalStudent.FREDDIE;

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

    public static final Group GROUP_G01 = new GroupBuilder().withName("G01")
        .withStudents(AARON, BENJAMIN, CATHERINE)
        .withClasses("1-1", "1-2")
        .build();

    public static final Group GROUP_G02 = new GroupBuilder().withName("G02")
        .withStudents(DAVID, ELFIE, FREDDIE)
        .withClasses("1-1", "1-2", "2-1", "2-2")
        .build();

    // Manually added - Group's details found in {@code CommandTestUtil}
    public static final Group GROUP_G04 = new GroupBuilder().withName(CommandTestUtil.VALID_GROUP_NAME_G04)
        .withFilePath(VALID_PATH_G04).build();
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
        return new ArrayList<>(Arrays.asList(GROUP_G04, GROUP_G01, GROUP_G02));
    }

    public static GroupManager getTypicalGroupManager() {
        GroupManager groupManager = new GroupManager();
        groupManager.addGroup(GROUP_G01);
        groupManager.addGroup(GROUP_G02);
        return groupManager;
    }
}
