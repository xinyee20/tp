package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;

public class UnmarkAttCommand extends Command {

    public static final String COMMAND_WORD = "unmarkatt";
    public static final String MESSAGE_SUCCESS = "Attendance unmarked: \n%1$s - absent";

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

        ObservableList<StudentInfo> studentsInfo = model.getStudentInfoList();

        // Mark single student attendance
        for (int i = 0; i < studentsInfo.size(); i++) {
            StudentInfo student = studentsInfo.get(i);
            boolean isCorrectStudent = studentsInfo.get(i).containsStudent(toUnmarkAtt);
            if (isCorrectStudent) {
                Attendance update = student.getAttendance().setAttendance(false);
                student.updateAttendance(update);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toUnmarkAtt));
    }
}
