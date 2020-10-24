package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ADD_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SUBTRACT_SCORE;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Attendance;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Participation;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.util.UniqueList;

public class SetScoreCommand extends Command {

    public static final String COMMAND_WORD = "setscore";
    public static final String MESSAGE_SUCCESS = "%s: \nUpdated Participation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_STUDENT_NOT_PRESENT =
            "%s is not present, please ensure student is present before giving a score";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Score should be within range of 0 to 5";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "Index %d is not found, please ensure that it exists";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Marks a specific student in the class a participation score. \n"
        + "Parameters: "
        + PREFIX_NAME + " STUDENT_NAME "
        + PREFIX_ID + " STUDENT_NUMBER "
        + PREFIX_ADD_SCORE + " SCORE " + "or"
        + PREFIX_SUBTRACT_SCORE + " SCORE\n"
        + "or INDEX " + PREFIX_ADD_SCORE + " SCORE "
        + "or INDEX " + PREFIX_SUBTRACT_SCORE + " SCORE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + " Aaron Tan "
        + PREFIX_ID + " e0123456 "
        + PREFIX_ADD_SCORE + " 2\n"
        + "or " + COMMAND_WORD + " "
        + PREFIX_NAME + " Aaron Tan "
        + PREFIX_ID + " e0123456 "
        + PREFIX_SUBTRACT_SCORE + " 2\n"
        + "or " + COMMAND_WORD + " 2"
        + PREFIX_ADD_SCORE + " 2\n"
        + "or " + COMMAND_WORD + " 2"
        + PREFIX_SUBTRACT_SCORE + " 2\n";

    private Student toSetScore;
    private Index index;
    private boolean isByIndex;
    private int score;
    private int scoreToAdd;
    private int scoreToSubtract;
    private boolean isAddScore;
    private boolean isCorrectStudent;

    /**
     * Creates an SetScoreCommand to adjust the specified {@code Student}'s participation score.
     */
    public SetScoreCommand(Student student, int score, boolean isAddScore) {
        requireNonNull(student);
        requireNonNull(score);
        requireNonNull(isAddScore);
        // Specified student to set participation score
        this.toSetScore = student;
        if (isAddScore) {
            this.isAddScore = true;
            this.scoreToAdd = score;
        } else {
            this.isAddScore = false;
            this.scoreToSubtract = score;
        }
        this.isByIndex = false;
    }

    /**
     * Creates an SetScoreCommand to adjust the specified {@code Student}'s participation score by index.
     */
    public SetScoreCommand(Index index, int score, boolean isAddScore) {
        requireNonNull(index);
        requireNonNull(score);
        requireNonNull(isAddScore);
        // Specified index of student to set participation score
        this.index = index;
        if (isAddScore) {
            this.isAddScore = true;
            this.scoreToAdd = score;
        } else {
            this.isAddScore = false;
            this.scoreToSubtract = score;
        }
        this.isByIndex = true;
    }



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Lesson uniqueLesson = model.getFilteredLessonList().get(0);
            UniqueList<StudentInfo> uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
            ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();
            int newScore = 0;

            if (!isByIndex) {

                // Update single student participation score
                for (int i = 0; i < studentsInfo.size(); i++) {
                    StudentInfo studentInfo = studentsInfo.get(i);
                    this.score = studentInfo.getParticipation().getScore();
                    this.isCorrectStudent = studentInfo.containsStudent(this.toSetScore);
                    if (this.isCorrectStudent) {
                        Attendance currentAttendance = studentInfo.getAttendance();
                        if (!currentAttendance.getAttendance()) {
                            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSetScore));
                        }
                        Participation update;
                        if (isAddScore) {
                            newScore = score + scoreToAdd;
                        } else {
                            newScore = score - scoreToSubtract;
                        }
                        if (newScore > 5 || newScore < 0) {
                            throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
                        }
                        update = studentInfo.getParticipation().setNewScore(newScore);
                        StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                        uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                        model.updateLessonList();
                        model.updateStudentsInfoList();
                        break;
                    }
                }

                if (!this.isCorrectStudent) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toSetScore));
                }
            } else {
                if (index.getZeroBased() > studentsInfo.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.getOneBased()));
                }

                StudentInfo studentInfo = studentsInfo.get(index.getZeroBased());
                toSetScore = studentInfo.getStudent();
                Attendance currentAttendance = studentInfo.getAttendance();
                if (!currentAttendance.getAttendance()) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSetScore));
                }
                Participation update;
                if (isAddScore) {
                    newScore = score + scoreToAdd;
                } else {
                    newScore = score - scoreToSubtract;
                }
                if (newScore > 5 || newScore < 0) {
                    throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
                }
                update = studentInfo.getParticipation().setNewScore(newScore);
                StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentsInfoList();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toSetScore, newScore));

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
                || (other instanceof SetScoreCommand // instanceof handles nulls
                && this.toSetScore.equals(((SetScoreCommand) other).toSetScore)
                && this.score == ((SetScoreCommand) other).score
                && this.isCorrectStudent == (((SetScoreCommand) other).isCorrectStudent));
    }

}
