package team.serenity.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Group;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;


/**
 * API of the Logic component.
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();

    /**
     * Returns an unmodifiable view of the filtered list of groups.
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns an unmodifiable view of the filtered list of students from a group.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the filtered list of lesson from a group.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the list of students info from a group-lesson.
     */
    ObservableList<StudentInfo> getStudentInfoList();

    /**
     * Returns all student info across all tutorial groups.
     */
    ObservableList<StudentInfo> getAllStudentInfo();

    /**
     * Returns an unmodifiable view of the list of questions from a group-lesson.
     */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Returns true if there is at least 1 existing group. Else returns false.
     *
     * @return true if there is at least 1 existing group.
     */
    boolean hasGroup();

    /**
     * Returns a list of existing groups.
     *
     * @return a list of existing groups.
     */
    ObservableList<Group> getGroups();

}
