package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SCORE;

import javafx.collections.ObservableList;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Participation;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

public class AddScoreCommand extends Command {

    public static final String COMMAND_WORD = "addscore";
    public static final String MESSAGE_SUCCESS = "%s: \nParticipation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gives a student in the class a participation score. \n"
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_ID + " STUDENT_NUMBER "
            + PREFIX_SCORE + " SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Aaron Tan "
            + PREFIX_ID + " e0123456 "
            + PREFIX_SCORE + " 2";

    private Student toAddScore;
    private int score;
    private boolean isCorrectStudent;

    /**
     * Creates an AddScoreCommand to award the specified {@code Student} a participation score
     */
    public AddScoreCommand(Student student, int score) {
        requireNonNull(student);
        // Specified student to add participation score
        this.toAddScore = student;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Lesson uniqueLesson = model.getFilteredLessonList().get(0);
            UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
            ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

            // Update single student participation score
            for (int i = 0; i < studentsInfo.size(); i++) {
                StudentInfo studentInfo = studentsInfo.get(i);
                this.isCorrectStudent = studentInfo.containsStudent(this.toAddScore);
                if (this.isCorrectStudent) {
                    Participation update = studentInfo.getParticipation().setNewScore(this.score);
                    StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                    uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                    model.updateLessonList();
                    model.updateStudentsInfoList();
                    break;
                }
            }

            if (!this.isCorrectStudent) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toAddScore));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toAddScore, this.score));

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
                || (other instanceof AddScoreCommand // instanceof handles nulls
                && this.toAddScore.equals(((AddScoreCommand) other).toAddScore))
                && this.score == ((AddScoreCommand) other).score;
    }

}
