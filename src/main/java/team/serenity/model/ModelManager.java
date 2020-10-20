package team.serenity.model;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.UniqueLessonList;
import team.serenity.model.group.UniqueQuestionList;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.group.UniqueStudentList;
import team.serenity.model.managers.GroupManager;
import team.serenity.model.managers.LessonManager;
import team.serenity.model.managers.StudentInfoManager;
import team.serenity.model.managers.StudentManager;
import team.serenity.model.util.UniqueList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final FilteredList<Group> filteredGroups;
    private final ArrayObservableList<Student> students;
    private final ArrayObservableList<Lesson> lessons;
    private final FilteredList<Lesson> filteredLessons;
    private final ArrayObservableList<StudentInfo> studentsInfo;
    private final ArrayObservableList<Question> questions;

    private final GroupManager groupManager;
    private final StudentManager studentManager;
    private final StudentInfoManager studentInfoManager;
    private final LessonManager lessonManager;

    /**
     * Initializes a ModelManager with the given serenity and userPrefs.
     */
    public ModelManager(ReadOnlySerenity serenity, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs, serenity);

        logger.fine("Initializing with serenity " + serenity + " and user prefs " + userPrefs);

        //instantiate individual managers
        this.groupManager = new GroupManager(new UniqueGroupList());
        this.studentManager = new StudentManager();
        this.studentInfoManager = new StudentInfoManager();
        this.lessonManager = new LessonManager();
        this.userPrefs = new UserPrefs(userPrefs);

        this.filteredGroups = new FilteredList<>(this.groupManager.getListOfGroups());
        this.students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        this.lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        this.filteredLessons = new FilteredList<>(this.lessons);
        this.studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        this.questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
    }

    /**
     * Initializes a ModelManager with the given userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        //instantiate managers
        this.groupManager = new GroupManager(new UniqueGroupList());
        this.studentInfoManager = new StudentInfoManager();
        this.studentManager = new StudentManager();
        this.lessonManager = new LessonManager();

        this.userPrefs = new UserPrefs(userPrefs);

        this.filteredGroups = new FilteredList<>(this.groupManager.getListOfGroups());
        this.students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        this.lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        this.filteredLessons = new FilteredList<>(this.lessons);
        this.studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        this.questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
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
        return this.userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return this.userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.userPrefs.setGuiSettings(guiSettings);
    }

    // ========== Serenity ================================================================================

    // ========== GroupManager ==========

    @Override
    public Path getSerenityFilePath() {
        return this.userPrefs.getSerenityFilePath();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedSerenity}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return this.filteredGroups;
    }

    @Override
    public ObservableList<Group> getListOfGroups() {
        return this.groupManager.getListOfGroups();
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return this.groupManager.hasGroup(group);
    }

    @Override
    public void deleteGroup(Group group) {
        this.groupManager.deleteGroup(group);
        this.filteredGroups.clear();
        this.students.clear();
        this.lessons.clear();
        this.filteredLessons.clear();
        this.studentsInfo.clear();
    }

    @Override
    public void addGroup(Group group) {
        requireNonNull(group);
        UniqueList<Student> studentList = group.getStudents();
        this.groupManager.addGroup(group);
        this.studentManager.addListOfStudentsToGroup(group, studentList);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireAllNonNull(predicate);
        this.filteredGroups.setPredicate(predicate);
        updateStudentsList();
        updateLessonList();
    }

    // ========== LessonManager ==========

    @Override
    public ObservableList<Lesson> getLessonList() {
        return this.lessons;
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return this.filteredLessons;
    }

    @Override
    public Optional<UniqueList<Lesson>> getListOfLessonsFromGroup(Group group) {
        return this.lessonManager.getListOfLessonsFromGroup(group);
    }

    @Override
    public void updateLessonList() {
        if (this.filteredGroups.size() == 1) {
            Group currentGroup = this.filteredGroups.get(0);
            ObservableList<Lesson> lessons = currentGroup.getLessonsAsUnmodifiableObservableList();
            UniqueList<Lesson> lessonList = currentGroup.getLessons();
            this.lessons.setAll(lessons);
            this.lessonManager.setListOfLessonsToGroup(currentGroup, lessonList);
        }
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireAllNonNull(predicate);
        this.filteredLessons.setPredicate(predicate);
        updateStudentsInfoList();
        updateQuestionList();
    }

    // ========== StudentManager ==========

    @Override
    public ObservableList<Student> getStudentList() {
        return this.students;
    }

    @Override
    public Optional<UniqueList<Student>> getListOfStudentsFromGroup(Group group) {
        return this.studentManager.getListOfStudentsFromGroup(group);
    }

    @Override
    public void deleteStudentFromGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        UniqueList<Student> students = this.filteredGroups.get(0).getStudents();
        if (!this.filteredGroups.isEmpty() && students.contains(student)) {
            students.remove(student);
            Group currentGroup = this.filteredGroups.get(0);
            currentGroup.deleteStudentFromGroup(student);
        }
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        if (!this.filteredGroups.isEmpty() && !this.students.contains(student)) {
            this.students.add(student);
            Group currentGroup = this.filteredGroups.get(0);
            currentGroup.addStudentToGroup(student);
            this.studentManager.addStudentToGroup(currentGroup, student);
        }
    }

    @Override
    public void updateStudentsList() {
        if (this.filteredGroups.size() == 1) {
            ObservableList<Student> studentList = this.filteredGroups.get(0).getStudentsAsUnmodifiableObservableList();
            this.students.setAll(studentList);
            this.studentManager.setListOfStudentsToGroup(this.filteredGroups.get(0),
                this.filteredGroups.get(0).getStudents());
        }
    }

    @Override
    public boolean checkIfStudentExistsInGroup(Group group, Student student) {
        return this.studentManager.checkIfStudentExistsInGroup(group, student);
    }

    // ========== StudentInfoManager ==========

    @Override
    public ObservableList<StudentInfo> getStudentsInfoList() {
        return this.studentsInfo;
    }

    @Override
    public Optional<UniqueList<StudentInfo>> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
        GroupLessonKey key = new GroupLessonKey(group, lesson);
        return this.studentInfoManager.getListOfStudentsInfoFromGroupLessonKey(key);
    }

    @Override
    public void updateStudentsInfoList() {
        if (!this.filteredGroups.isEmpty() && !this.filteredLessons.isEmpty()) {
            Group currentGroup = this.filteredGroups.get(0);
            Lesson currentLesson = this.filteredLessons.get(0);
            GroupLessonKey key = new GroupLessonKey(currentGroup, currentLesson);
            ObservableList<StudentInfo> studentInfos = currentLesson.getStudentsInfoAsUnmodifiableObservableList();
            UniqueList<StudentInfo> uniqueStudentInfoList = currentLesson.getStudentsInfo();
            this.studentsInfo.setAll(studentInfos);
            this.studentInfoManager.setListOfStudentsInfoToGroupLessonKey(key, uniqueStudentInfoList);
        }
    }

    // ========== QuestionManager ==========

    @Override
    public ObservableList<Question> getQuestionList() {
        return this.questions;
    }

    @Override
    public void updateQuestionList() {
        if (!this.filteredLessons.isEmpty()) {
            this.questions.setAll(this.filteredLessons.get(0).getQuestionListAsUnmodifiableObservableList());
        }
    }

    // ========== Utils ==========

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
        return this.userPrefs.equals(other.userPrefs)
            && this.filteredGroups.equals(other.filteredGroups)
            && this.students.equals(other.students)
            && this.lessons.equals(other.lessons)
            && this.filteredLessons.equals(other.filteredLessons)
            && this.studentsInfo.equals(other.studentsInfo)
            && this.questions.equals(other.questions);
    }

}
