package team.serenity.model;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.commons.util.XlsxUtil;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.managers.GroupManager;
import team.serenity.model.managers.LessonManager;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.managers.Serenity;
import team.serenity.model.managers.StudentInfoManager;
import team.serenity.model.managers.StudentManager;
import team.serenity.model.userprefs.ReadOnlyUserPrefs;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.model.util.UniqueList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final Serenity serenity;
    private final GroupManager groupManager;
    private final StudentManager studentManager;
    private final StudentInfoManager studentInfoManager;
    private final LessonManager lessonManager;
    private final QuestionManager questionManager;

    private final FilteredList<Group> filteredGroups;
    private final ArrayObservableList<Student> students;
    private final ArrayObservableList<Lesson> lessons;
    private final FilteredList<Lesson> filteredLessons;
    private final ArrayObservableList<StudentInfo> studentsInfo;
    private final FilteredList<Question> filteredQuestions;

    /**
     * Initializes a ModelManager with the given serenity, userPrefs and Respective Managers.
     */
    public ModelManager(ReadOnlySerenity serenity,
                        ReadOnlyQuestionManager questionManager,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(serenity, questionManager, userPrefs);

        logger.fine("Initializing with serenity " + serenity + " and user prefs " + userPrefs);

        //instantiate individual managers
        this.userPrefs = new UserPrefs(userPrefs);
        this.serenity = new Serenity(serenity);
        this.groupManager = new GroupManager(serenity.getGroupManager());
        this.studentManager = new StudentManager(serenity.getStudentManager());
        this.studentInfoManager = new StudentInfoManager(serenity.getStudentInfoManager());
        this.lessonManager = new LessonManager(serenity.getLessonManager());
        this.questionManager = new QuestionManager(questionManager);

        this.filteredGroups = new FilteredList<>(this.groupManager.getListOfGroups());
        this.students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        this.lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        this.filteredLessons = new FilteredList<>(this.lessons);
        this.studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        this.filteredQuestions = new FilteredList<>(this.questionManager.getListOfQuestions());
    }

    /**
     * Initializes a ModelManager with the given userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        //instantiate individual managers
        this.userPrefs = new UserPrefs(userPrefs);
        this.serenity = new Serenity();
        this.groupManager = new GroupManager();
        this.studentInfoManager = new StudentInfoManager();
        this.studentManager = new StudentManager();
        this.lessonManager = new LessonManager();
        this.questionManager = new QuestionManager();

        this.filteredGroups = new FilteredList<>(this.groupManager.getListOfGroups());
        this.students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        this.lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        this.filteredLessons = new FilteredList<>(this.lessons);
        this.studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        this.filteredQuestions = new FilteredList<>(this.questionManager.getListOfQuestions());
    }

    public ModelManager() {
        this(new Serenity(), new QuestionManager(), new UserPrefs());
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

    @Override
    public ReadOnlySerenity getSerenity() {
        return this.serenity;
    }

    @Override
    public GroupManager getGroupManager() {
        return groupManager;
    }

    @Override
    public Path getSerenityFilePath() {
        return this.userPrefs.getSerenityFilePath();
    }

    // ========== GroupManager ==========

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
    public Stream<Group> getGroupStream() {
        return this.groupManager.getStream();
    }

    @Override
    public boolean hasGroupName(GroupName toCheck) {
        return this.groupManager.hasGroupName(toCheck);
    }

    @Override
    public boolean isEmpty() {
        return groupManager.isEmpty();
    }

    @Override
    public void deleteGroup(Group group) {
        this.groupManager.deleteGroup(group);
        this.studentManager.deleteAllStudentsFromGroup(group);
        this.studentInfoManager.deleteAllStudentInfosFromGroup(group);
        this.lessonManager.deleteAllLessonsFromGroup(group);
        this.questionManager.deleteAllQuestionsFromGroup(group);
        this.students.clear();
        this.lessons.clear();
        this.studentsInfo.clear();
    }

    @Override
    public void addGroup(Group newGroup) {
        requireNonNull(newGroup);
        UniqueList<Student> newStudentList = newGroup.getStudents();
        UniqueList<Lesson> newLessonList = newGroup.getLessons();
        this.groupManager.addGroup(newGroup);
        this.studentManager.addListOfStudentsToGroup(newGroup.getGroupName(), newStudentList);
        this.lessonManager.addListOfLessonsToGroup(newGroup, newLessonList);
        UniqueList<StudentInfo> studentInfoList = new UniqueStudentInfoList();
        ObservableList<Student> uniqueStudentList = newStudentList.getList();
        for (int s = 0; s < newStudentList.size(); s++) {
            Student uniqueStudent = uniqueStudentList.get(s);
            StudentInfo uniqueStudentInfo = new StudentInfo(uniqueStudent);
            studentInfoList.add(uniqueStudentInfo);
        }
        for (int i = 0; i < newLessonList.size(); i++) {
            ObservableList<Lesson> uniqueLessonList = newLessonList.getList();
            Lesson uniqueLesson = uniqueLessonList.get(i);
            GroupLessonKey groupLessonKey = new GroupLessonKey(newGroup.getGroupName(), uniqueLesson.getLessonName());
            this.studentInfoManager.setListOfStudentsInfoToGroupLessonKey(groupLessonKey, studentInfoList);
        }
    }

    @Override
    public void exportAttendance(Group group) {
        requireNonNull(group);
        XlsxUtil util = new XlsxUtil();
        util.writeAttendanceToXlsx(group, this.studentInfoManager.getStudentInfoMap());
    }

    @Override
    public void exportParticipation(Group group) {
        requireNonNull(group);
        XlsxUtil util = new XlsxUtil();
        util.writeScoreToXlsx(group, this.studentInfoManager.getStudentInfoMap());
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
    public UniqueList<Lesson> getListOfLessonsFromGroup(Group group) {
        return this.lessonManager.getListOfLessonsFromGroup(group.getGroupName());
    }

    @Override
    public boolean ifTargetGroupHasLessonName(GroupName groupName, LessonName lessonName) {
        return this.lessonManager.ifTargetGroupHasLessonName(groupName, lessonName);
    }

    @Override
    public void deleteLesson(Group group, Lesson lesson) {
        this.studentInfoManager.deleteAllStudentsInfoFromGroupLesson(group, lesson);
        this.lessonManager.deleteLessonFromGroup(group.getGroupName(), lesson);
        this.questionManager.deleteAllQuestionsFromGroupLesson(group, lesson);
        this.lessons.removeAll(lesson);
        this.studentsInfo.clear();
    }

    @Override
    public void updateLessonList() {
        if (this.filteredGroups.size() == 1) {
            Group currentGroup = this.filteredGroups.get(0);
            ObservableList<Lesson> lessons = currentGroup.getLessonsAsUnmodifiableObservableList();
            UniqueList<Lesson> lessonList = currentGroup.getLessons();
            this.lessons.setAll(lessons);
            this.lessonManager.setListOfLessonsToGroup(currentGroup.getGroupName(), lessonList);
        }
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireAllNonNull(predicate);
        this.filteredLessons.setPredicate(predicate);
        updateStudentsInfoList();
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    // ========== StudentManager ==========

    @Override
    public ObservableList<Student> getStudentList() {
        return this.students;
    }

    @Override
    public UniqueList<Student> getListOfStudentsFromGroup(Group group) {
        return this.studentManager.getListOfStudentsFromGroup(group.getGroupName());
    }

    @Override
    public boolean hasStudent(Student toCheck) {
        return this.studentManager.hasStudent(toCheck);
    }

    @Override
    public void deleteStudentFromGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        if (!this.filteredGroups.isEmpty()) {
            Group currentGroup = this.filteredGroups.get(0);
            this.groupManager.deleteStudentFromGroup(currentGroup, student);
        }
        updateStudentsInfoList();
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        if (!this.filteredGroups.isEmpty() && !this.students.contains(student)) {
            this.students.add(student);
            this.students.sort(Comparator.comparing(x -> x.getStudentName().toString()));
            Group currentGroup = this.filteredGroups.get(0);
            currentGroup.addStudentToGroup(student);
        }
        updateStudentsInfoList();
    }

    @Override
    public void updateStudentsList() {
        if (this.filteredGroups.size() == 1) {
            ObservableList<Student> studentList = this.filteredGroups.get(0).getStudentsAsUnmodifiableObservableList();
            this.students.setAll(studentList);
            this.studentManager.setListOfStudentsToGroup(this.filteredGroups.get(0).getGroupName(),
                this.filteredGroups.get(0).getStudents());
        }
    }

    @Override
    public boolean checkIfStudentExistsInGroup(Group group, Student student) {
        return this.studentManager.checkIfStudentExistsInGroup(group.getGroupName(), student);
    }

    // ========== StudentInfoManager ==========

    @Override
    public ObservableList<StudentInfo> getStudentsInfoList() {
        return this.studentsInfo;
    }

    @Override
    public UniqueList<StudentInfo> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
        GroupLessonKey key = new GroupLessonKey(group.getGroupName(), lesson.getLessonName());
        return this.studentInfoManager.getListOfStudentsInfoFromGroupLessonKey(key);
    }

    @Override
    public ObservableList<StudentInfo> getObservableListOfStudentsInfoFromKey(GroupLessonKey key) {
        return this.studentInfoManager.getObservableListOfStudentsInfoFromKey(key);
    }

    @Override
    public void setListOfStudentsInfoToGroupLessonKey(GroupLessonKey key,
                                                         UniqueList<StudentInfo> newListOfStudentsInfo) {
        requireAllNonNull(key, newListOfStudentsInfo);
        this.studentInfoManager.setListOfStudentsInfoToGroupLessonKey(key, newListOfStudentsInfo);
    }

    @Override
    public void updateStudentsInfoList() {
        if (!this.filteredGroups.isEmpty() && !this.filteredLessons.isEmpty()) {
            Group currentGroup = this.filteredGroups.get(0);
            Lesson currentLesson = this.filteredLessons.get(0);
            GroupLessonKey key = new GroupLessonKey(currentGroup.getGroupName(), currentLesson.getLessonName());
            ObservableList<StudentInfo> studentsInfoList = currentLesson.getStudentsInfoAsUnmodifiableObservableList();
            UniqueList<StudentInfo> uniqueStudentInfoList = currentLesson.getStudentsInfo();
            this.studentsInfo.setAll(studentsInfoList);
            this.studentInfoManager.setListOfStudentsInfoToGroupLessonKey(key, uniqueStudentInfoList);
        }
    }

    @Override
    public ObservableList<StudentInfo> getAllStudentInfo() {
        ObservableList<StudentInfo> studentInfoList =
                new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        for (Group group : getListOfGroups()) {
            for (Lesson lesson : group.getLessons()) {
                studentInfoList.addAll(lesson.getStudentsInfo().getList());
            }
        }
        return studentInfoList;
    }

    // ========== QuestionManager ==========

    @Override
    public ReadOnlyQuestionManager getQuestionManager() {
        return this.questionManager;
    }

    @Override
    public void setQuestionManager(ReadOnlyQuestionManager questionManager) {
        requireNonNull(questionManager);
        this.questionManager.resetData(questionManager);
    }

    @Override
    public boolean hasQuestion(Question toCheck) {
        requireNonNull(toCheck);
        return this.questionManager.hasQuestion(toCheck);
    }

    @Override
    public void deleteQuestion(Question toDelete) {
        requireNonNull(toDelete);
        this.questionManager.deleteQuestion(toDelete);
    }

    @Override
    public void addQuestion(Question toAdd) {
        requireNonNull(toAdd);
        this.questionManager.addQuestion(toAdd);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question edited) {
        requireAllNonNull(target, edited);
        this.questionManager.setQuestion(target, edited);
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return this.filteredQuestions;
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        requireNonNull(predicate);
        this.filteredQuestions.setPredicate(predicate);
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
            && this.questionManager.equals(other.questionManager)
            && this.filteredGroups.equals(other.filteredGroups)
            && this.students.equals(other.students)
            && this.lessons.equals(other.lessons)
            && this.filteredLessons.equals(other.filteredLessons)
            && this.studentsInfo.equals(other.studentsInfo)
            && this.filteredQuestions.equals(other.filteredQuestions);
    }

}
