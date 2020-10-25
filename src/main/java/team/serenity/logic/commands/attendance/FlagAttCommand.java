package team.serenity.logic.commands.attendance;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Attendance;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

/**
 * Flags the attendance of a student in the class.
 */
public class FlagAttCommand extends Command {

    public static final String COMMAND_WORD = "flagatt";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance is flagged!";
    public static final String MESSAGE_FAILURE = "Flagging attendance is for absent students.";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "Index %d is not found, please ensure that it exists";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flags the attendance of a specific student for a lesson. \n"
            + "Parameters: "
            + PREFIX_NAME + " STUDENT_NAME "
            + PREFIX_ID + " STUDENT_NUMBER " + "or INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Aaron Tan "
            + PREFIX_ID + " e0123456\n"
            + "or " + COMMAND_WORD + " 2";



    private Student toFlagAtt;
    private Index index;
    private boolean isByIndex;
    private boolean isCorrectStudent;

    /**
     * Creates an FlagAttCommand to flag a specified {@code Student}'s attendance.
     */
    public FlagAttCommand(Student student) {
        requireNonNull(student);
        // Specified student to flag attendance
        this.toFlagAtt = student;
        this.isByIndex = false;
    }

    /**
     * Creates an FlagAttCommand to flag a specified {@code Student}'s attendance by index.
     */
    public FlagAttCommand(Index index) {
        requireNonNull(index);
        // Specified index of student to flag attendance
        this.index = index;
        this.isByIndex = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Lesson uniqueLesson = model.getFilteredLessonList().get(0);
            UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
            ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

            if (!isByIndex) {

                // Flag a student's attendance
                for (int i = 0; i < studentsInfo.size(); i++) {
                    StudentInfo studentInfo = studentsInfo.get(i);
                    Attendance current = studentInfo.getAttendance();
                    this.isCorrectStudent = studentInfo.containsStudent(this.toFlagAtt);
                    if (this.isCorrectStudent) {
                        Attendance update = new Attendance(current.getAttendance(), true);
                        StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                        uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                        model.updateLessonList();
                        model.updateStudentsInfoList();
                        break;
                    }
                }

                if (!this.isCorrectStudent) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toFlagAtt));
                }
            } else {
                if (index.getZeroBased() > studentsInfo.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.getOneBased()));
                }

                StudentInfo studentInfo = studentsInfo.get(index.getZeroBased());
                Attendance current = studentInfo.getAttendance();
                toFlagAtt = studentInfo.getStudent();
                Attendance update = new Attendance(current.getAttendance(), true);
                StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toFlagAtt));

        } catch (Exception e) {
            if (e instanceof CommandException) {
                throw e;
            } else if (e instanceof IllegalArgumentException) {
                throw new CommandException(MESSAGE_FAILURE);
            } else {
                throw new CommandException(MESSAGE_NOT_IN_LESSON);
            }
        }
    }
}
