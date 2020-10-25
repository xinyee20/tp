package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.util.UniqueList;

/**
 * Wraps all data at the serenity level Duplicates are not allowed (by .isSameGroup comparison).
 */
public class Serenity implements ReadOnlySerenity {

    private final UniqueList<Group> groups;
    private final ReadOnlyGroupManager groupManager;
    private final ReadOnlyLessonManager lessonManager;
    private final ReadOnlyStudentManager studentManager;
    private final ReadOnlyStudentInfoManager studentInfoManager;

    public Serenity() {
        this.groups = new UniqueGroupList();
        this.groupManager = new GroupManager();
        this.lessonManager = new LessonManager();
        this.studentManager = new StudentManager();
        this.studentInfoManager = new StudentInfoManager();
    }

    /**
     * Creates a Serenity object using the Groups in the {@code toBeCopied}.
     */
    public Serenity(ReadOnlySerenity toBeCopied) {
        
        requireNonNull(toBeCopied);

        //instantiate groups
        this.groups = new UniqueGroupList();
        this.groups.setElementsWithList(toBeCopied.getGroupList());

        //instantiate groupmanager
        this.groupManager = new GroupManager(this.groups);


        Map<GroupName, UniqueList<Lesson>> lessonMap = new HashMap<>();
        for (Group group : this.groups) {
            GroupName name = group.getGroupName();
            UniqueList<Lesson> lessons = group.getLessons();
            lessonMap.put(name, lessons);
        }
    }


    public ReadOnlyGroupManager instantiateGroupManager() {
        return new GroupManager(this.groups);
    }


    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in serenity.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return this.groups.contains(group);
    }

    /**
     * Adds a group to the serenity. The group must not already exist in the serenity.
     */
    public void addGroup(Group g) {
        this.groups.add(g);
    }


    /**
     * Replaces the given group {@code group} in the list with {@code editedGroup}. {@code target} must exist in
     * serenity. The group identity of {@code editedGroup} must not be the same as another existing group in serenity.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);
        this.groups.setElement(target, editedGroup);
    }

    /**
     * Removes {@code key} from this {@code Serenity}. {@code key} must exist in serenity.
     */
    public void deleteGroup(Group key) {
        this.groups.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return this.groups.asUnmodifiableObservableList().size() + " groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return this.groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Serenity // instanceof handles nulls
            && this.groups.equals(((Serenity) other).groups));
    }

    @Override
    public int hashCode() {
        return this.groups.hashCode();
    }
}
