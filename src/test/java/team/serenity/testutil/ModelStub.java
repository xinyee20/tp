package team.serenity.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.model.Model;
import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.ReadOnlyUserPrefs;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;

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
    public void setSerenityFilePath(Path serenityFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSerenity(ReadOnlySerenity serenity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudentToGroup(Student student, Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeStudentFromGroup(Student student, Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlySerenity getSerenity() {
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
    public void updateStudentList() {
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
    public void updateStudentInfoList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateQuestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getStudentList() {
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
    public ObservableList<StudentInfo> getStudentInfoList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        throw new AssertionError("This method should not be called.");
    }
}
