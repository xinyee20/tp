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
import seedu.address.model.group.UniqueStudentInfoList;

public class UnmarkAttCommand extends Command {

    public static final String COMMAND_WORD = "unmarkatt";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance - absent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of a student in a class. \n"
            + "Parameters: "
            + PREFIX_STUDENT + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + " Aaron Tan" + " " + PREFIX_ID + " e0123456";

    private Student toUnmarkAtt;

    /**
     * Creates an UnmarkAttCommand to mark the specified {@code Student} absent
     */
    public UnmarkAttCommand(Student student) {
        requireNonNull(student);
        // Specified student to mark present
        toUnmarkAtt = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueStudentInfoList uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

        // Mark single student attendance
        for (int i = 0; i < studentsInfo.size(); i++) {
            StudentInfo studentInfo = studentsInfo.get(i);
            boolean isCorrectStudent = studentInfo.containsStudent(toUnmarkAtt);
            if (isCorrectStudent) {
                Attendance update = studentInfo.getAttendance().setAttendance(false);
                StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                uniqueStudentInfoList.setStudentInfo(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentInfoList();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toUnmarkAtt));
    }
}
