package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Participation;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueStudentInfoList;

public class AddScoreCommand extends Command {

    public static final String COMMAND_WORD = "addscore";
    public static final String MESSAGE_SUCCESS = "%s: \nParticipation Score - %d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gives a student in the class a participation score. \n"
            + "Parameters: " + " " + "SCORE "
            + PREFIX_STUDENT + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " " + "2" + " "
            + PREFIX_STUDENT + " Aaron Tan" + " " + PREFIX_ID + " e0123456";

    private Student toAddScore;
    private int score;

    /**
     * Creates an AddScoreCommand to award the specified {@code Student} a participation score
     */
    public AddScoreCommand(Student student, int score) {
        requireNonNull(student);
        // Specified student to add participation score
        toAddScore = student;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueStudentInfoList uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

        // Update single student participation score
        for (int i = 0; i < studentsInfo.size(); i++) {
            StudentInfo studentInfo = studentsInfo.get(i);
            boolean isCorrectStudent = studentInfo.containsStudent(toAddScore);
            if (isCorrectStudent) {
                Participation update = studentInfo.getParticipation().setScore(score);
                StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                uniqueStudentInfoList.setStudentInfo(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentInfoList();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddScore, score));

    }
}
