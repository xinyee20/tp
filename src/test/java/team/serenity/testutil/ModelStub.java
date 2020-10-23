package team.serenity.testutil;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.userprefs.ReadOnlyUserPrefs;
import team.serenity.model.util.UniqueList;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {


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
    public boolean hasGroup(Group group) {
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
    public void updateFilteredGroupList(Predicate<Group> predicate) {
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
    public Optional<UniqueList<Lesson>> getListOfLessonsFromGroup(Group group) {
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
    public Optional<UniqueList<Student>> getListOfStudentsFromGroup(Group group) {
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
    public Optional<UniqueList<StudentInfo>> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
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
