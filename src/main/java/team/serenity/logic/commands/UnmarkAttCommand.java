package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Attendance;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

public class UnmarkAttCommand extends Command {

    public static final String COMMAND_WORD = "unmarkatt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of a student in a class. \n"
            + "Parameters: "
            + PREFIX_NAME + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Aaron Tan" + " " + PREFIX_ID + " e0123456";

    public static final String MESSAGE_SUCCESS = "%s: \nAttendance - absent";

    private Student toUnmarkAtt;

    /**
     * Creates an UnmarkAttCommand to mark the specified {@code Student} absent
     */
    public UnmarkAttCommand(Student student) {
        requireNonNull(student);
        // Specified student to mark present
        this.toUnmarkAtt = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

        // Mark single student attendance
        for (int i = 0; i < studentsInfo.size(); i++) {
            StudentInfo studentInfo = studentsInfo.get(i);
            boolean isCorrectStudent = studentInfo.containsStudent(this.toUnmarkAtt);
            if (isCorrectStudent) {
                Attendance update = studentInfo.getAttendance().setNewAttendance(false);
                StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toUnmarkAtt));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkAttCommand // instanceof handles nulls
                && this.toUnmarkAtt.equals(((UnmarkAttCommand) other).toUnmarkAtt));
    }

}
