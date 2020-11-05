package team.serenity.model;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.managers.GroupManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.userprefs.ReadOnlyUserPrefs;
import team.serenity.model.util.UniqueList;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Question> PREDICATE_SHOW_ALL_QUESTIONS = unused -> true;

    // ========== UserPrefs ==========

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ========== Serenity ==========

    /**
     * Returns serenity.
     */
    ReadOnlySerenity getSerenity();

    /**
     * Returns the GroupManager.
     */
    GroupManager getGroupManager();

    /**
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();

    // ========== GroupManager ==========

    /**
     * Returns an unmodifiable view of the filtered group list.
     */
    ObservableList<Group> getFilteredGroupList();

    ObservableList<Group> getListOfGroups();

    /**
     * Get stream of groups
     * @return Stream of groups
     */
    Stream<Group> getGroupStream();

    /**
     * Returns true if at least one group exists in serenity.
     * @return whether any group exists
     */
    boolean isEmpty();

    /**
     * Deletes the given group. The group must exist in serenity.
     */
    void deleteGroup(Group target);

    /**
     * Adds the given group. {@code group} must not already exist in serenity.
     */
    void addGroup(Group group);

    /**
     * Exports attendance data of the given group as XLSX file.
     */
    void exportAttendance(Group group);

    /**
     * Exports participation data of the given group as XLSX file.
     */
    void exportParticipation(Group group);

    /**
     * Returns true if a group with a GroupName that is the same as {@code toCheck} exists in the
     * GroupManager.
     *
     * @param toCheck the given group name.
     * @return true if the given group name already exist in the GroupManager.
     */
    boolean hasGroupName(GroupName toCheck);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Get all student info objects from all groups.
     */
    ObservableList<StudentInfo> getAllStudentInfo();

    // ========== LessonManager ==========

    /**
     * Returns an unmodifiable view of the lesson list.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the filtered lesson list.
     */
    ObservableList<Lesson> getFilteredLessonList();

    UniqueList<Lesson> getListOfLessonsFromGroup(Group group);

    /**
     * Returns true if a lesson with {@code LessonName} in group {@code GroupName}
     * is the same as {@code lessonName} exists in the LessonManager.
     *
     * @param groupName the given group to check against.
     * @param lessonName the given lesson name to check for.
     * @return true if the given lesson name already exists in the group in the LessonManager.
     */
    boolean ifTargetGroupHasLessonName(GroupName groupName, LessonName lessonName);

    /**
     * Deletes the given group's lesson. The group's lesson must exist in serenity.
     */
    void deleteLesson(Group targetGroup, Lesson targetLesson);

    /**
     * Updates the lesson list to filter when changing to another group of interest.
     */
    void updateLessonList();

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    // ========== StudentManager ==========

    /**
     * Returns an unmodifiable view of the student list.
     */
    ObservableList<Student> getStudentList();

    UniqueList<Student> getListOfStudentsFromGroup(Group group);

    /**
     * Returns true if a question that is the same as {@code toCheck} exists in the
     * StudentManager.
     *
     * @param toCheck the given student.
     * @return true if the given student already exist in the StudentManager.
     */
    boolean hasStudent(Student toCheck);

    /**
     * Removes a Student from a Group.
     */
    void deleteStudentFromGroup(Student student, Predicate<Group> predicate);

    /**
     * Adds a Student to a Group
     */
    void addStudentToGroup(Student student, Predicate<Group> predicate);

    /**
     * Updates the student list when changing to another group of interest.
     */
    void updateStudentsList();

    /**
     * Checks if Student exists.
     * @param group
     * @param student
     * @return Whether student exists.
     */
    boolean checkIfStudentExistsInGroup(Group group, Student student);

    // ========== StudentInfoManager ==========

    /**
     * Returns an unmodifiable view of the student info list
     */
    ObservableList<StudentInfo> getStudentsInfoList();

    UniqueList<StudentInfo> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson);

    /**
     * Returns an unmodifiable view of the student info list from the specified {@code GroupLessonKey}.
     */
    ObservableList<StudentInfo> getObservableListOfStudentsInfoFromKey(GroupLessonKey key);

    /**
     * Replaces listOfStudentsInfo stored at {@code key} with {@code newListOfStudentsInfo}.
     * @param key the given target key.
     * @param newListOfStudentsInfo the given edited list of studentInfo.
     */
    void setListOfStudentsInfoToGroupLessonKey(GroupLessonKey key, UniqueList<StudentInfo> newListOfStudentsInfo);

    /**
     * Updates the student info list to filter when changing to another lesson of interest.
     */
    void updateStudentsInfoList();

    // ========== QuestionManager ==========

    /**
     * Returns the questionManager
     */
    ReadOnlyQuestionManager getQuestionManager();

    /**
     * Replaces questionManager data with the data in {@code questionManager}.
     */
    void setQuestionManager(ReadOnlyQuestionManager questionManager);

    /**
     * Returns true if a question that is the same as {@code toCheck} exists in the
     * QuestionManager.
     *
     * @param toCheck the given question.
     * @return true if the given question already exist in the QuestionManager.
     */
    boolean hasQuestion(Question toCheck);

    /**
     * Deletes the given question.
     * The question must exist in the QuestionManager.
     *
     * @param toDelete the given question.
     */
    void deleteQuestion(Question toDelete);

    /**
     * Adds the given question.
     * {@code toAdd} must not already exist in the QuestionManager.
     *
     * @param toAdd the given question.
     */
    void addQuestion(Question toAdd);

    /**
     * Replaces the given question {@code target} with {@code edited}.
     * {@code target} must exist in the QuestionManager.
     * {@code edited} must not be the same as another existing question in the QuestionManager.
     *
     * @param target the given target question.
     * @param edited the given edited question.
     */
    void setQuestion(Question target, Question edited);

    /**
     * Returns an unmodifiable view of the filtered question list
     */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Updates the filter of the filtered question list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */

    void updateFilteredQuestionList(Predicate<Question> predicate);

}
