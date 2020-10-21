package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.util.UniqueList;

public class MarkAbsentCommand extends Command {

    public static final String COMMAND_WORD = "markabsent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance:  absent";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of a student in a class. \n"
            + "Parameters: "
            + PREFIX_STUDENT + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + " Aaron Tan" + " " + PREFIX_ID + " e0123456";

    private Student markAbsent;
    private boolean isCorrectStudent;

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent
     */
    public MarkAbsentCommand(Student student) {
        requireNonNull(student);
        // Specified student to mark present
        markAbsent = student;
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
                isCorrectStudent = studentInfo.containsStudent(markAbsent);
                if (isCorrectStudent) {
                    Attendance update = studentInfo.getAttendance().setNewAttendance(false);
                    StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                    uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                    model.updateLessonList();
                    model.updateStudentInfoList();
                    break;
                }
            }

            if (!isCorrectStudent) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, markAbsent));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, markAbsent));

        } catch (Exception e) {
            if (e instanceof CommandException) {
                throw e;
            } else {
                throw new CommandException(MESSAGE_NOT_IN_LESSON);
            }
        }
    }
}
