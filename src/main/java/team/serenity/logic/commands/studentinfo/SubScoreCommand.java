package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SUBTRACT_SCORE;

import java.util.Optional;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;

public class SubScoreCommand extends Command {
    public static final String COMMAND_WORD = "subscore";
    public static final String MESSAGE_SUCCESS = "%s: \nUpdated Participation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_PRESENT =
            "%s is not present. \nPlease ensure student is present before subtracting score!";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Updated score should be within range of 0 to 5";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Decrease the participation score of a specific student for a lesson.\n"
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_MATRIC + "STUDENT_NUMBER "
            + PREFIX_SUBTRACT_SCORE + "SCORE_TO_SUBTRACT "
            + "or INDEX(starting from 1) " + PREFIX_SUBTRACT_SCORE + "SCORE_TO_SUBTRACT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Aaron Tan "
            + PREFIX_MATRIC + "A0123456B "
            + PREFIX_SUBTRACT_SCORE + "2\n"
            + "or " + COMMAND_WORD + " 2"
            + PREFIX_SUBTRACT_SCORE + "2\n";

    private Optional<Student> toSubScore;
    private Optional<Index> index;
    private boolean isByIndex;
    private int score;
    private int scoreToSub;
    private boolean isCorrectStudent;

    /**
     * Creates an SubScoreCommand to decrease the specified {@code Student}'s participation score.
     */
    public SubScoreCommand(Student student, int scoreToSub) {
        requireNonNull(student);
        requireNonNull(scoreToSub);
        // Specified student to add participation score
        this.toSubScore = Optional.ofNullable(student);
        this.scoreToSub = scoreToSub;
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an SubScoreCommand to decrease the specified {@code Student}'s participation score by index.
     */
    public SubScoreCommand(Index index, int scoreToSub) {
        requireNonNull(index);
        requireNonNull(scoreToSub);
        // Specified index of student to decrease participation score
        this.index = Optional.ofNullable(index);
        this.toSubScore = Optional.empty();
        this.scoreToSub = scoreToSub;
        this.isByIndex = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredGroupList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_GROUP);
        }

        if (model.getFilteredLessonList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_LESSON);
        }

        Group uniqueGroup = model.getFilteredGroupList().get(0);
        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueList<StudentInfo> uniqueStudentInfoList =
                model.getListOfStudentsInfoFromGroupAndLesson(uniqueGroup, uniqueLesson);
        ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();
        int newScore = 0;

        if (!isByIndex) {
            // Update single student participation score
            for (int i = 0; i < studentsInfo.size(); i++) {
                StudentInfo studentInfo = studentsInfo.get(i);
                this.score = studentInfo.getParticipation().getScore();
                this.isCorrectStudent = studentInfo.containsStudent(this.toSubScore.get());
                if (this.isCorrectStudent) {
                    Attendance currentAttendance = studentInfo.getAttendance();
                    if (!currentAttendance.isPresent()) {
                        throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSubScore.get()));
                    }
                    newScore = score - scoreToSub;
                    if (newScore > 5 || newScore < 0) {
                        throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
                    }
                    Participation update = studentInfo.getParticipation().setNewScore(newScore);
                    StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                    uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                    model.updateLessonList();
                    model.updateStudentsInfoList();
                    break;
                }
            }
            if (!this.isCorrectStudent) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toSubScore.get()));
            }
        } else {
            if (index.get().getZeroBased() >= studentsInfo.size()) {
                throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        index.get().getOneBased()));
            }

            StudentInfo studentInfo = studentsInfo.get(index.get().getZeroBased());
            toSubScore = Optional.ofNullable(studentInfo.getStudent());
            this.score = studentInfo.getParticipation().getScore();
            Attendance currentAttendance = studentInfo.getAttendance();
            if (!currentAttendance.isPresent()) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSubScore.get()));
            }
            newScore = score - scoreToSub;
            if (newScore > 5 || newScore < 0) {
                throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
            }
            Participation update = studentInfo.getParticipation().setNewScore(newScore);
            StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
            uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
            model.updateLessonList();
            model.updateStudentsInfoList();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toSubScore.get(), newScore));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubScoreCommand // instanceof handles nulls
                && this.toSubScore.equals(((SubScoreCommand) other).toSubScore)
                && this.scoreToSub == ((SubScoreCommand) other).scoreToSub)
                && this.index.equals(((SubScoreCommand) other).index)
                && this.isByIndex == (((SubScoreCommand) other).isByIndex);
    }
}
