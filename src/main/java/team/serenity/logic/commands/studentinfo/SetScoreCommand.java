package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CliSyntax.PREFIX_SET_SCORE;

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

public class SetScoreCommand extends Command {

    public static final String COMMAND_WORD = "setscore";
    public static final String MESSAGE_SUCCESS = "%s: \nUpdated Participation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_PRESENT =
            "%s is not present. \nPlease ensure student is present before giving a score!";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Updated score should be within range of 0 to 5";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Awards a specific student a participation score for a lesson.\n"
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_MATRIC + "STUDENT_NUMBER "
            + PREFIX_SET_SCORE + "SCORE "
            + "or INDEX(starting from 1) " + PREFIX_SET_SCORE + " SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Aaron Tan "
            + PREFIX_MATRIC + "A0123456U "
            + PREFIX_SET_SCORE + " 2\n"
            + "or " + COMMAND_WORD + " 2 "
            + PREFIX_SET_SCORE + "2\n";

    private Optional<Student> toSetScore;
    private Optional<Index> index;
    private boolean isByIndex;
    private int scoreToSet;
    private boolean isCorrectStudent;

    /**
     * Creates an SetScoreCommand to set the specified {@code Student}'s participation score.
     */
    public SetScoreCommand(Student student, int scoreToSet) {
        requireNonNull(student);
        requireNonNull(scoreToSet);
        // Specified student to set participation score
        this.toSetScore = Optional.ofNullable(student);
        this.scoreToSet = scoreToSet;
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an SetScoreCommand to set the specified {@code Student}'s participation score by index.
     */
    public SetScoreCommand(Index index, int scoreToSet) {
        requireNonNull(index);
        requireNonNull(scoreToSet);
        // Specified index of student to set participation score
        this.index = Optional.ofNullable(index);
        this.toSetScore = Optional.empty();
        this.scoreToSet = scoreToSet;
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

        if (!isByIndex) {

            // Update single student participation score
            for (int i = 0; i < studentsInfo.size(); i++) {
                StudentInfo studentInfo = studentsInfo.get(i);
                this.isCorrectStudent = studentInfo.containsStudent(this.toSetScore.get());
                if (this.isCorrectStudent) {
                    Attendance currentAttendance = studentInfo.getAttendance();
                    if (!currentAttendance.isPresent()) {
                        throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSetScore.get()));
                    }
                    if (scoreToSet > 5 || scoreToSet < 0) {
                        throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
                    }
                    Participation update = studentInfo.getParticipation().setNewScore(scoreToSet);
                    StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
                    uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
                    model.updateLessonList();
                    model.updateStudentsInfoList();
                    break;
                }
            }

            if (!this.isCorrectStudent) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, this.toSetScore.get()));
            }
        } else {
            if (index.get().getZeroBased() >= studentsInfo.size()) {
                throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        index.get().getOneBased()));
            }

            StudentInfo studentInfo = studentsInfo.get(index.get().getZeroBased());
            toSetScore = Optional.ofNullable(studentInfo.getStudent());
            Attendance currentAttendance = studentInfo.getAttendance();
            if (!currentAttendance.isPresent()) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSetScore.get()));
            }
            if (scoreToSet > 5 || scoreToSet < 0) {
                throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
            }
            Participation update = studentInfo.getParticipation().setNewScore(scoreToSet);
            StudentInfo updatedStudentInfo = studentInfo.updateParticipation(update);
            uniqueStudentInfoList.setElement(studentInfo, updatedStudentInfo);
            model.updateLessonList();
            model.updateStudentsInfoList();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toSetScore.get(), scoreToSet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetScoreCommand // instanceof handles nulls
                && this.scoreToSet == ((SetScoreCommand) other).scoreToSet)
                && this.toSetScore.equals(((SetScoreCommand) other).toSetScore)
                && this.index.equals(((SetScoreCommand) other).index)
                && this.isByIndex == (((SetScoreCommand) other).isByIndex);
    }

}
