package team.serenity.model.managers;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
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

    /**
     * Creates a {@code Serenity}.
     */
    public Serenity() {
        this.groups = new UniqueGroupList();
        this.groupManager = new GroupManager();
        this.lessonManager = new LessonManager();
        this.studentManager = new StudentManager();
        this.studentInfoManager = new StudentInfoManager();
    }

    /**
     * Creates a {@code Serenity} using a List of Groups.
     * @param groups
     */
    public Serenity(List<Group> groups) {

        //instantiate groups
        this.groups = new UniqueGroupList();
        this.groups.setElementsWithList(groups);

        //instantiate GroupManager
        this.groupManager = new GroupManager(this.groups);

        //instantiate other managers
        LessonManager lessonManager = new LessonManager();
        StudentManager studentManager = new StudentManager();
        StudentInfoManager studentInfoManager = new StudentInfoManager();

        //Create Maps for all the relevant data
        Map<GroupName, UniqueList<Lesson>> lessonMap = new HashMap<>();
        Map<GroupName, UniqueList<Student>> studentMap = new HashMap<>();
        Map<GroupLessonKey, UniqueList<StudentInfo>> studentInfoMap = new HashMap<>();
        for (Group group : this.groups) {
            GroupName name = group.getGroupName();
            UniqueList<Student> students = group.getStudents();
            UniqueList<Lesson> lessons = group.getLessons();
            lessonMap.put(name, lessons);
            studentMap.put(name, students);

            for (Lesson lesson : lessons) {
                GroupLessonKey key = new GroupLessonKey(name, lesson.getLessonName());
                studentInfoMap.put(key, lesson.getStudentsInfo());
            }
        }

        //set maps to our managers
        studentManager.setStudents(studentMap);
        lessonManager.setLessons(lessonMap);
        studentInfoManager.setStudentInfo(studentInfoMap);


        //instantiate ReadOnlyStudentManager, ReadOnlyLessonManager and ReadOnlyStudentInfoManager
        this.lessonManager = lessonManager;
        this.studentManager = studentManager;
        this.studentInfoManager = studentInfoManager;
    }

    /**
     * Creates a Serenity object using the Groups in the {@code toBeCopied}.
     */
    public Serenity(ReadOnlySerenity toBeCopied) {

        requireNonNull(toBeCopied);

        //instantiate groups
        this.groups = new UniqueGroupList();
        this.groups.setElementsWithList(toBeCopied.getGroupList());

        //instantiate GroupManager
        this.groupManager = new GroupManager(this.groups);

        //instantiate other managers
        LessonManager lessonManager = new LessonManager();
        StudentManager studentManager = new StudentManager();
        StudentInfoManager studentInfoManager = new StudentInfoManager();

        //Create Maps for all the relevant data
        Map<GroupName, UniqueList<Lesson>> lessonMap = new HashMap<>();
        Map<GroupName, UniqueList<Student>> studentMap = new HashMap<>();
        Map<GroupLessonKey, UniqueList<StudentInfo>> studentInfoMap = new HashMap<>();
        for (Group group : this.groups) {
            GroupName name = group.getGroupName();
            UniqueList<Student> students = group.getStudents();
            UniqueList<Lesson> lessons = group.getLessons();
            lessonMap.put(name, lessons);
            studentMap.put(name, students);

            for (Lesson lesson : lessons) {
                GroupLessonKey key = new GroupLessonKey(name, lesson.getLessonName());
                studentInfoMap.put(key, lesson.getStudentsInfo());
            }
        }

        //set maps to our managers
        studentManager.setStudents(studentMap);
        lessonManager.setLessons(lessonMap);
        studentInfoManager.setStudentInfo(studentInfoMap);


        //instantiate ReadOnlyStudentManager, ReadOnlyLessonManager and ReadOnlyStudentInfoManager
        this.lessonManager = lessonManager;
        this.studentManager = studentManager;
        this.studentInfoManager = studentInfoManager;
    }


    @Override
    public ReadOnlyGroupManager getGroupManager() {
        return this.groupManager;
    }

    @Override
    public ReadOnlyLessonManager getLessonManager() {
        return this.lessonManager;
    }

    @Override
    public ReadOnlyStudentManager getStudentManager() {
        return this.studentManager;
    }

    @Override
    public ReadOnlyStudentInfoManager getStudentInfoManager() {
        return this.studentInfoManager;
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
