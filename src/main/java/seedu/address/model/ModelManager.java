package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLessonKey;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Question;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.UniqueLessonList;
import seedu.address.model.group.UniqueQuestionList;
import seedu.address.model.group.UniqueStudentInfoList;
import seedu.address.model.group.UniqueStudentList;
import seedu.address.model.managers.GroupManager;
import seedu.address.model.managers.LessonManager;
import seedu.address.model.managers.StudentInfoManager;
import seedu.address.model.managers.StudentManager;
import seedu.address.model.person.Person;
import seedu.address.model.util.UniqueList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
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
     * Initializes a ModelManager with the given addressBook, userPrefs and serenity.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
        ReadOnlySerenity serenity) {
        super();
        requireAllNonNull(addressBook, userPrefs, serenity);

        logger.fine("Initializing with address book: " + addressBook
            + " and user prefs " + userPrefs
            + " and serenity " + serenity);

        //instantiate individual managers
        groupManager = new GroupManager(new UniqueGroupList());
        studentManager = new StudentManager();
        studentInfoManager = new StudentInfoManager();
        lessonManager = new LessonManager();

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        filteredGroups = new FilteredList<>(groupManager.getGroupList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(lessons);
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        //instantiate managers
        groupManager = new GroupManager(new UniqueGroupList());
        studentInfoManager = new StudentInfoManager();
        studentManager = new StudentManager();
        lessonManager = new LessonManager();


        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredGroups = new FilteredList<>(groupManager.getGroupList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());

        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(lessons);
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
        questions = new ArrayObservableList<>(new UniqueQuestionList().asUnmodifiableObservableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Serenity());
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

    // =========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    // =========== Serenity ================================================================================

    //==== GroupManager ====


    @Override
    public ObservableList<Group> getGroupList() {
        return groupManager.getGroupList();
    }

    @Override
    public Path getSerenityFilePath() {
        return userPrefs.getSerenityFilePath();
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groupManager.hasGroup(group);
    }

    //===== LessonManager ====

    @Override
    public Optional<UniqueList<Lesson>> getLessons(Group group) {
        return lessonManager.getLessons(group);
    }

    //==== StudentManager ====

    @Override
    public boolean checkIfStudentExistsInGroup(Group group, Student student) {
        return studentManager.checkIfStudentExists(group, student);
    }

    @Override
    public Optional<UniqueList<Student>> getStudents(Group group) {
        return studentManager.getStudents(group);
    }

    // ==== StudentInfoManager ====

    @Override
    public Optional<UniqueList<StudentInfo>> getStudentInfos(Group group, Lesson lesson) {
        GroupLessonKey key = new GroupLessonKey(group, lesson);
        return studentInfoManager.getStudentInfos(key);
    }

    // =====

    @Override
    public void deleteGroup(Group group) {
        groupManager.deleteGroup(group);
        filteredGroups.clear();
        students.clear();
        lessons.clear();
        filteredLessons.clear();
        studentsInfo.clear();
    }

    @Override
    public void addGroup(Group group) {
        requireNonNull(group);
        UniqueList<Student> studentList = group.getStudents();
        groupManager.addGroup(group);
        studentManager.addGroup(group, studentList);
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        if (!filteredGroups.isEmpty() && !students.contains(student)) {
            students.add(student);
            Group currentGroup = filteredGroups.get(0);
            currentGroup.addStudentToGroup(student);
            studentManager.addStudent(currentGroup, student);
        }
    }

    @Override
    public void removeStudentFromGroup(Student student, Predicate<Group> predicate) {
        requireAllNonNull(student, predicate);
        updateFilteredGroupList(predicate);
        UniqueList<Student> students = filteredGroups.get(0).getStudents();
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
        if (filteredGroups.size() == 1) {
            ObservableList<Student> studentList = this.filteredGroups.get(0).getStudentsAsUnmodifiableObservableList();
            this.students.setAll(studentList);
            studentManager.setGroup(filteredGroups.get(0), filteredGroups.get(0).getStudents());
        }
    }

    @Override
    public void updateLessonList() {
        if (filteredGroups.size() == 1) {
            Group currentGroup = filteredGroups.get(0);
            ObservableList<Lesson> lessons = currentGroup.getLessonsAsUnmodifiableObservableList();
            UniqueList<Lesson> lessonList = currentGroup.getLessons();
            this.lessons.setAll(lessons);
            lessonManager.setLessonLists(currentGroup, lessonList);
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
        if (!filteredGroups.isEmpty() && !filteredLessons.isEmpty()) {
            Group currentGroup = filteredGroups.get(0);
            Lesson currentLesson = filteredLessons.get(0);
            GroupLessonKey key = new GroupLessonKey(currentGroup, currentLesson);
            ObservableList<StudentInfo> studentInfos = currentLesson.getStudentsInfoAsUnmodifiableObservableList();
            UniqueList<StudentInfo> uniqueStudentInfoList = currentLesson.getStudentsInfo();
            this.studentsInfo.setAll(studentInfos);
            this.studentInfoManager.setStudentInfos(key, uniqueStudentInfoList);
        }
    }

    @Override
    public void updateQuestionList() {
        if (!filteredLessons.isEmpty()) {
            this.questions.setAll(this.filteredLessons.get(0).getQuestionListAsUnmodifiableObservableList());
        }
    }

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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons);
    }

}
