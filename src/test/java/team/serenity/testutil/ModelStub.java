package team.serenity.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.model.Model;
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
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public boolean isEmpty() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Stream<Group> getGroupStream() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlySerenity getSerenity() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GroupManager getGroupManager() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Path getSerenityFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getListOfGroups() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroupName(GroupName toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroup(Group target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportAttendance(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportParticipation(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudentInfo> getAllStudentInfo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public UniqueList<Lesson> getListOfLessonsFromGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean ifTargetGroupHasLessonName(GroupName groupName, LessonName lessonName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteLesson(Group targetGroup, Lesson targetLesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public UniqueList<Student> getListOfStudentsFromGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudentFromGroup(Student student, Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateStudentsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkIfStudentExistsInGroup(Group group, Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudentInfo> getStudentsInfoList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public UniqueList<StudentInfo> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudentInfo> getObservableListOfStudentsInfoFromKey(GroupLessonKey key) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setListOfStudentsInfoToGroupLessonKey(GroupLessonKey key,
                                                      UniqueList<StudentInfo> newListOfStudentsInfo) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateStudentsInfoList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyQuestionManager getQuestionManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestionManager(ReadOnlyQuestionManager questionManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasQuestion(Question toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteQuestion(Question toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addQuestion(Question toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestion(Question target, Question edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        throw new AssertionError("This method should not be called.");
    }

}
