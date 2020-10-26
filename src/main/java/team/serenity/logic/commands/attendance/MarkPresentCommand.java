package team.serenity.logic.commands.attendance;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Attendance;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

/**
 * Marks the attendance of a class or a student in the class.
 */
public class MarkPresentCommand extends Command {

    public static final String COMMAND_WORD = "markpresent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance: present";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked present!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific student or all students present from a lesson. \n"
            + "Parameters: "
            + "all or "
            + PREFIX_NAME + " STUDENT_NAME "
            + PREFIX_MATRIC + " STUDENT_NUMBER " + "or INDEX\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_NAME + " Aaron Tan "
            + PREFIX_MATRIC + " A0123456U\n"
            + "or " + COMMAND_WORD + " 2";

    private Optional<Student> toMarkPresent;
    private Optional<Index> index;
    private boolean isByIndex;
    private boolean isWholeClass;
    private boolean isCorrectStudent;

    /**
     * Creates an MarkPresentCommand to mark all {@code Student} present.
     */
    public MarkPresentCommand() {
        // Mark all students present
        this.isWholeClass = true;
        this.toMarkPresent = Optional.empty();
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkPresentCommand to mark the specified {@code Student} present.
     */
    public MarkPresentCommand(Student student) {
        requireNonNull(student);
        this.isWholeClass = false;
        // Specified student to mark present
        this.toMarkPresent = Optional.ofNullable(student);
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkPresentCommand to mark the specified {@code Student} present by index.
     */
    public MarkPresentCommand(Index index) {
        requireNonNull(index);
        this.isWholeClass = false;
        // Specified index of student to mark present
        this.index = Optional.ofNullable(index);
        this.toMarkPresent = Optional.empty();
        this.isByIndex = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredGroupList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_GROUP);
        }

        if (model.getFilteredLessonList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_LESSON);
        }

        Group uniqueGroup = model.getFilteredGroupList().get(0);
        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
//        Optional<UniqueList<StudentInfo>> studentInfoList = model.getListOfStudentsInfoFromGroupAndLesson(uniqueGroup, uniqueLesson);
//        UniqueList<StudentInfo> uniqueStudentInfoList = studentInfoList.get();
//        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();
        UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

        if (! this.isWholeClass) {

            if (! isByIndex) {

                // Mark single student present
                for (int i = 0; i < studentsInfo.size(); i++) {
                    StudentInfo studentInfo = studentsInfo.get(i);
                    this.isCorrectStudent = studentInfo.containsStudent(this.toMarkPresent.get());
                    if (this.isCorrectStudent) {
                        Attendance update = studentInfo.getAttendance().setNewAttendance(true);
                        StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                        uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                        model.updateLessonList();
                        model.updateStudentsInfoList();
                        break;
                    }
                }

                if (! this.isCorrectStudent) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toMarkPresent));
                }
            } else {
                if (index.get().getZeroBased() > studentsInfo.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.get().getOneBased()));
                }

                StudentInfo studentInfo = studentsInfo.get(index.get().getZeroBased());
                toMarkPresent = Optional.ofNullable(studentInfo.getStudent());
                Attendance update = studentInfo.getAttendance().setNewAttendance(true);
                StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toMarkPresent));
        } else {

            // Mark whole class present
            for (StudentInfo each : studentsInfo) {
                Attendance update = each.getAttendance().setNewAttendance(true);
                StudentInfo updatedStudentInfo = each.updateAttendance(update);
                uniqueStudentInfoList.setElement(each, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }

            return new CommandResult(String.format(MESSAGE_ALL_SUCCESS));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkPresentCommand // instanceof handles nulls
                && this.toMarkPresent.equals(((MarkPresentCommand) other).toMarkPresent)
                && this.index.equals(((MarkPresentCommand) other).index)
                && this.isByIndex == (((MarkPresentCommand) other).isByIndex)
                && this.isWholeClass == ((MarkPresentCommand) other).isWholeClass);
    }

}
