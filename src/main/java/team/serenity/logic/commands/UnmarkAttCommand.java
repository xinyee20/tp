package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Attendance;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueStudentInfoList;

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
