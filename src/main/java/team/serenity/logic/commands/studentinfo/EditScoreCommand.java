package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_NOT_WITHIN_RANGE;
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
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class EditScoreCommand extends Command {

    public static final String COMMAND_WORD = "editscore";
    public static final String MESSAGE_SUCCESS = "%s: \nUpdated Participation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_PRESENT =
            "%s is not present. \nPlease ensure student is present before giving a score!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the score of the specified student for a tutorial lesson. "
            + "Existing score will be overwritten by the input score.\n"
            + "Parameters (2 methods):\n"
            + "1. " + PREFIX_NAME + "STUDENT_NAME " + PREFIX_MATRIC + "STUDENT_NUMBER "
                    + PREFIX_SET_SCORE + "SCORE (must be an integer from 0 to 5)\n"
            + "2. INDEX (must be a positive integer) "
                    + PREFIX_SET_SCORE + "SCORE (must be an integer from 0 to 5)\n"
            + "Examples:\n"
            + "1. " + COMMAND_WORD + " " + PREFIX_NAME + "Aaron Tan " + PREFIX_MATRIC + "A0123456A "
                    + PREFIX_SET_SCORE + "2\n"
            + "2. " + COMMAND_WORD + " 1 " + PREFIX_SET_SCORE + "2\n";

    private Optional<Student> toSetScore;
    private Optional<Index> index;
    private boolean isByIndex;
    private int scoreToSet;

    /**
     * Creates an SetScoreCommand to set the specified {@code Student}'s participation score.
     */
    public EditScoreCommand(Student student, int scoreToSet) {
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
    public EditScoreCommand(Index index, int scoreToSet) {
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
        GroupLessonKey key = new GroupLessonKey(uniqueGroup.getGroupName(), uniqueLesson.getLessonName());
        ObservableList<StudentInfo> currentStudentInfoList = model.getObservableListOfStudentsInfoFromKey(key);
        StudentInfo targetStudentInfo = getTargetStudentInfo(currentStudentInfoList);

        return executeSetScoreOneStudent(model, key, uniqueLesson, currentStudentInfoList, targetStudentInfo);
    }

    /**
     * Executes the set one student's score command and returns the result message.
     */
    private CommandResult executeSetScoreOneStudent(Model model, GroupLessonKey key, Lesson lesson,
                                                ObservableList<StudentInfo> currentStudentInfoList,
                                                StudentInfo targetStudentInfo) throws CommandException {
        // Gets the updated StudentInfoList with the updated targetStudentInfo
        UniqueList<StudentInfo> updatedListForSetScoreOneStudent =
                getUpdatedListForSetScoreOneStudent(currentStudentInfoList, targetStudentInfo);

        // Updates the modelManager and lesson object with the new StudentInfoList
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForSetScoreOneStudent);
        lesson.setStudentsInfo(updatedListForSetScoreOneStudent);
        model.updateLessonList();
        model.updateStudentsInfoList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudentInfo.getStudent(), scoreToSet));
    }

    /**
     * Returns the {@code targetStudentInfo} object in the {@code currentStudentInfoList}.
     */
    private StudentInfo getTargetStudentInfo(ObservableList<StudentInfo> currentStudentInfoList)
            throws CommandException {
        if (this.isByIndex) {
            // Set Score by index
            assert this.index.isPresent();
            Index targetIndex = this.index.get();

            // Return error message if index is out of range
            if (targetIndex.getZeroBased() >= currentStudentInfoList.size() || index.get().getOneBased() == 0) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, targetIndex.getOneBased()));
            }
            return currentStudentInfoList.get(targetIndex.getZeroBased());
        }

        assert this.toSetScore.isPresent();
        Student student = this.toSetScore.get();

        // Filter studentInfoList via Student and get the first object in the filtered stream (if any)
        Optional<StudentInfo> optionalStudentInfo =
                currentStudentInfoList.stream().filter(s -> s.containsStudent(student)).findFirst();

        // Return error message if Student not found in StudentInfoList
        if (optionalStudentInfo.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, student));
        }
        return optionalStudentInfo.get();
    }

    /**
     * Sets the given {@code targetStudentInfo}'s {@code Participation} field
     * in the {@code currentStudentInfoList} with a score.
     * Returns the {@code updatedStudentInfoList}.
     *
     * @param currentStudentInfoList the current student info list.
     * @param targetStudentInfo the target student info to set score.
     */
    private UniqueList<StudentInfo> getUpdatedListForSetScoreOneStudent(
            ObservableList<StudentInfo> currentStudentInfoList, StudentInfo targetStudentInfo) throws CommandException {
        if (!targetStudentInfo.getAttendance().isPresent()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toSetScore.get()));
        }
        if (scoreToSet > 5 || scoreToSet < 0) {
            throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
        }
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentStudentInfoList);
        StudentInfo updatedStudentInfo = new StudentInfo(targetStudentInfo.getStudent(),
                new Participation().setNewScore(scoreToSet), targetStudentInfo.getAttendance());
        updatedList.setElement(targetStudentInfo, updatedStudentInfo);
        return updatedList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditScoreCommand // instanceof handles nulls
                && this.scoreToSet == ((EditScoreCommand) other).scoreToSet)
                && this.toSetScore.equals(((EditScoreCommand) other).toSetScore)
                && this.index.equals(((EditScoreCommand) other).index)
                && this.isByIndex == (((EditScoreCommand) other).isByIndex);
    }

}
