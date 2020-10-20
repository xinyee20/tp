package team.serenity.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

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

    // ========== GroupManager ==========

    /**
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();

    /**
     * Returns an unmodifiable view of the filtered group list.
     */
    ObservableList<Group> getFilteredGroupList();

    ObservableList<Group> getListOfGroups();

    /**
     * Returns true if a group with the same identity as {@code group} exists in serenity.
     */
    boolean hasGroup(Group group);

    /**
     * Deletes the given group. The group must exist in serenity.
     */
    void deleteGroup(Group target);

    /**
     * Adds the given group. {@code group} must not already exist in serenity.
     */
    void addGroup(Group group);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    // ========== LessonManager ==========

    /**
     * Returns an unmodifiable view of the lesson list.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the filtered lesson list.
     */
    ObservableList<Lesson> getFilteredLessonList();

    Optional<UniqueList<Lesson>> getListOfLessonsFromGroup(Group group);

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

    Optional<UniqueList<Student>> getListOfStudentsFromGroup(Group group);

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

    Optional<UniqueList<StudentInfo>> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson);

    /**
     * Updates the student info list to filter when changing to another lesson of interest.
     */
    void updateStudentsInfoList();

    // ========== QuestionManager ==========

    /**
     * Returns an unmodifiable view of the question list.
     */
    ObservableList<Question> getQuestionList();

    /**
     * Updates the question list to filter when changing to another lesson of interest.
     */
    void updateQuestionList();

}
