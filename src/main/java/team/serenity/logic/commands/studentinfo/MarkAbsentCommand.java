package team.serenity.logic.commands.studentinfo;

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
import team.serenity.model.group.Group;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;

public class MarkAbsentCommand extends Command {

    public static final String COMMAND_WORD = "markabsent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance:  absent";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked absent!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific student or all students absent from a lesson.\n"
            + "Parameters: "
            + "all or "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_MATRIC + "STUDENT_NUMBER " + "or INDEX\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_NAME + "Aaron Tan "
            + PREFIX_MATRIC + "A0123456U\n"
            + "or " + COMMAND_WORD + " 2";

    private Optional<Student> toMarkAbsent;
    private Optional<Index> index;
    private boolean isByIndex;
    private boolean isWholeClass;
    private boolean isCorrectStudent;

    /**
     * Creates an MarkAbsentCommand to mark all {@code Student} absent.
     */
    public MarkAbsentCommand() {
        // Mark all students absent
        this.isWholeClass = true;
        this.toMarkAbsent = Optional.empty();
        this.index = Optional.empty();
    }

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent.
     */
    public MarkAbsentCommand(Student student) {
        requireNonNull(student);
        this.isWholeClass = false;
        // Specified student to mark present
        this.toMarkAbsent = Optional.ofNullable(student);
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent by index.
     */
    public MarkAbsentCommand(Index index) {
        requireNonNull(index);
        this.isWholeClass = false;
        // Specified index of student to mark present
        this.index = Optional.ofNullable(index);
        this.toMarkAbsent = Optional.empty();
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
        UniqueList<StudentInfo> uniqueStudentInfoList =
                model.getListOfStudentsInfoFromGroupAndLesson(uniqueGroup, uniqueLesson);
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

        if (!this.isWholeClass) {

            if (!isByIndex) {

                // Mark single student attendance
                for (int i = 0; i < studentsInfo.size(); i++) {
                    StudentInfo studentInfo = studentsInfo.get(i);
                    this.isCorrectStudent = studentInfo.containsStudent(this.toMarkAbsent.get());
                    if (this.isCorrectStudent) {
                        Attendance update = studentInfo.getAttendance().setNewAttendance(false);
                        StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                        uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                        model.updateLessonList();
                        model.updateStudentsInfoList();
                        break;
                    }
                }

                if (!this.isCorrectStudent) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND,
                            this.toMarkAbsent.get()));
                }

            } else {
                if (index.get().getZeroBased() > studentsInfo.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.get().getOneBased()));
                }

                StudentInfo studentInfo = studentsInfo.get(index.get().getZeroBased());
                toMarkAbsent = Optional.ofNullable(studentInfo.getStudent());
                Attendance update = studentInfo.getAttendance().setNewAttendance(false);
                StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toMarkAbsent.get()));
        } else {

            // Mark whole class absent
            for (StudentInfo each : studentsInfo) {
                Attendance update = each.getAttendance().setNewAttendance(false);
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
                || (other instanceof MarkAbsentCommand // instanceof handles nulls
                && this.toMarkAbsent.equals(((MarkAbsentCommand) other).toMarkAbsent)
                && this.index.equals(((MarkAbsentCommand) other).index)
                && this.isCorrectStudent == ((MarkAbsentCommand) other).isCorrectStudent
                && this.isWholeClass == ((MarkAbsentCommand) other).isWholeClass);
    }

}
