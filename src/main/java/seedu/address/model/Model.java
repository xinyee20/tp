package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Question;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

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
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();

    /**
     * Sets the user prefs' serenity file path.
     */
    void setSerenityFilePath(Path serenityFilePath);

    /**
     * Replaces serenity data with the data in {@code serenity}.
     */
    void setSerenity(ReadOnlySerenity serenity);

    /**
     * Returns the Serenity
     */
    ReadOnlySerenity getSerenity();

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
     * Adds a Student to a Group
     */
    void addStudentToGroup(Student student, Predicate<Group> predicate);

    /**
     * Removes a Student from a Group.
     */
    void removeStudentFromGroup(Student student, Predicate<Group> predicate);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Updates the student list when changing to another group of interest.
     */
    void updateStudentList();

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

    /**
     * Updates the student info list to filter when changing to another lesson of interest.
     */
    void updateStudentInfoList();

    /**
     * Updates the question list to filter when changing to another lesson of interest.
     */
    void updateQuestionList();

    /**
     * Returns an unmodifiable view of the filtered group list.
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns an unmodifiable view of the student list.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the lesson list.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the filtered lesson list.
     */
    ObservableList<Lesson> getFilteredLessonList();

    /**
     * Returns an unmodifiable view of the student info list
     */
    ObservableList<StudentInfo> getStudentInfoList();
    /**
     * Returns an unmodifiable view of the question list.
     */
    ObservableList<Question> getQuestionList();
}
