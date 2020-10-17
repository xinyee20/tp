package team.serenity.model;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueLessonList;
import team.serenity.model.group.UniqueQuestionList;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.group.UniqueStudentList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final Serenity serenity;
    private final FilteredList<Group> filteredGroups;
    private final ArrayObservableList<Student> students;
    private final ArrayObservableList<Lesson> lessons;
    private final FilteredList<Lesson> filteredLessons;
    private final ArrayObservableList<StudentInfo> studentsInfo;
    private final ArrayObservableList<Question> questions;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and serenity.
     */
    public ModelManager(ReadOnlySerenity serenity, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs, serenity);

        logger.fine("Initializing with user prefs " + userPrefs
            + " and serenity " + serenity);

        this.userPrefs = new UserPrefs(userPrefs);
        this.serenity = new Serenity(serenity);
        filteredGroups = new FilteredList<>(this.serenity.getGroupList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(lessons);
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.serenity = new Serenity();
        filteredGroups = new FilteredList<>(this.serenity.getGroupList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(lessons);
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
    }

    public ModelManager() {
        this(new Serenity(), new UserPrefs());
    }

    // =========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // =========== Serenity ================================================================================

    @Override
    public Path getSerenityFilePath() {
        return userPrefs.getSerenityFilePath();
    }

    @Override
    public void setSerenityFilePath(Path serenityFilePath) {
        requireNonNull(serenityFilePath);
        userPrefs.setSerenityFilePath(serenityFilePath);
    }

    @Override
    public void setSerenity(ReadOnlySerenity serenity) {
        this.serenity.resetData(serenity);
    }

    @Override
    public ReadOnlySerenity getSerenity() {
        return serenity;
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return serenity.hasGroup(group);
    }

    @Override
    public void deleteGroup(Group target) {
        serenity.removeGroup(target);
        filteredGroups.clear();
        students.clear();
        lessons.clear();
        filteredLessons.clear();
        studentsInfo.clear();
    }

    @Override
    public void addGroup(Group group) {
        requireNonNull(group);
        serenity.addGroup(group);
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        if (!filteredGroups.isEmpty()) {
            students.add(student);
            Group currentGroup = filteredGroups.get(0);
            currentGroup.addStudentToGroup(student);
        }
    }

    @Override
    public void removeStudentFromGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        UniqueStudentList students = filteredGroups.get(0).getStudents();
        if (!filteredGroups.isEmpty() && students.contains(student)) {
            students.remove(student);
            Group currentGroup = filteredGroups.get(0);
            currentGroup.removeStudentFromGroup(student);
        }
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireAllNonNull(predicate);
        this.filteredGroups.setPredicate(predicate);
        updateStudentList();
        updateLessonList();
    }

    @Override
    public void updateStudentList() {
        if (!filteredGroups.isEmpty()) {
            this.students.setAll(this.filteredGroups.get(0).getStudentsAsUnmodifiableObservableList());
        }
    }

    @Override
    public void updateLessonList() {
        if (!filteredGroups.isEmpty()) {
            this.lessons.setAll(this.filteredGroups.get(0).getLessonsAsUnmodifiableObservableList());
        }
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireAllNonNull(predicate);
        this.filteredLessons.setPredicate(predicate);
        updateStudentInfoList();
        updateQuestionList();
    }

    @Override
    public void updateStudentInfoList() {
        if (!filteredGroups.isEmpty() || !filteredLessons.isEmpty()) {
            this.studentsInfo.setAll(this.filteredLessons.get(0).getStudentsInfoAsUnmodifiableObservableList());
        }
    }

    @Override
    public void updateQuestionList() {
        if (!filteredGroups.isEmpty() || !filteredLessons.isEmpty()) {
            this.questions.setAll(this.filteredLessons.get(0).getQuestionListAsUnmodifiableObservableList());
        }
    }

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of {@code
     * versionedSerenity}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students;
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return lessons;
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public ObservableList<StudentInfo> getStudentInfoList() {
        return studentsInfo;
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        return questions;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
            && filteredGroups.equals(other.filteredGroups)
            && students.equals(other.students)
            && lessons.equals(other.lessons)
            && filteredLessons.equals(other.filteredLessons)
            && studentsInfo.equals(other.studentsInfo)
            && questions.equals(other.questions);
    }

}
