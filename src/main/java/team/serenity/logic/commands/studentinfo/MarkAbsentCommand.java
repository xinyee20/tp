package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;

public class MarkAbsentCommand extends Command {

    public static final String COMMAND_WORD = "markabsent";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Marks a specific student absent from a lesson.\n"
        + "Parameters: "
        + PREFIX_NAME + " STUDENT_NAME "
        + PREFIX_ID + " STUDENT_NUMBER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + " Aaron Tan "
        + PREFIX_ID + " e0123456\n";

    public static final String MESSAGE_SUCCESS = "%s: \nAttendance:  absent";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";

    private Student markAbsent;
    private boolean isCorrectStudent;

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent.
     */
    public MarkAbsentCommand(Student student) {
        requireNonNull(student);
        // Specified student to mark present
        this.markAbsent = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Lesson uniqueLesson = model.getFilteredLessonList().get(0);
            UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
            ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

            // Mark single student attendance
            for (int i = 0; i < studentsInfo.size(); i++) {
                StudentInfo studentInfo = studentsInfo.get(i);
                this.isCorrectStudent = studentInfo.containsStudent(this.markAbsent);
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
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.markAbsent));
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, this.markAbsent));

        } catch (Exception e) {
            if (e instanceof CommandException) {
                throw e;
            } else {
                throw new CommandException(MESSAGE_NOT_IN_LESSON);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkAbsentCommand // instanceof handles nulls
                && this.markAbsent.equals(((MarkAbsentCommand) other).markAbsent)
                && this.isCorrectStudent == ((MarkAbsentCommand) other).isCorrectStudent);
    }

}
