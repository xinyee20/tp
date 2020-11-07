package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_TO_ADD;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ADD_SCORE;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

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

public class AddScoreCommand extends Command {
    public static final String COMMAND_WORD = "addscore";
    public static final String MESSAGE_SUCCESS = "%s: \nUpdated Participation Score: %d";
    public static final String MESSAGE_STUDENT_NOT_PRESENT = "%s is not present.\n"
        + "Please ensure student is present before adding score!";
    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Updated score should be within range of 0 to 5";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Increase the participation score of the specific student for a tutorial lesson.\n"
        + "Parameters (2 methods):\n"
        + "1. " + PREFIX_NAME + "STUDENT_NAME " + PREFIX_MATRIC + "STUDENT_NUMBER "
        + PREFIX_ADD_SCORE + "SCORE_TO_ADD (must be an integer from 0 to 5)\n"
        + "2. INDEX (must be a positive integer) "
        + PREFIX_ADD_SCORE + "SCORE_TO_ADD (must be an integer from 0 to 5)\n"
        + "Examples:\n"
        + "1. " + COMMAND_WORD + " " + PREFIX_NAME + "Aaron Tan " + PREFIX_MATRIC + "A0123456A "
        + PREFIX_ADD_SCORE + "2\n"
        + "2. " + COMMAND_WORD + " 1 " + PREFIX_ADD_SCORE + "2\n";

    private Optional<Student> toAddScore;
    private Optional<Index> index;
    private boolean isByIndex;
    private int score;
    private int scoreToAdd;
    private int newScore = 0;

    /**
     * Creates an AddScoreCommand to increase the specified {@code Student}'s participation score.
     */
    public AddScoreCommand(Student student, int scoreToAdd) throws CommandException {
        requireNonNull(student);
        requireNonNull(scoreToAdd);
        // Specified student to add participation score
        if (scoreToAdd <= 0) {
            throw new CommandException(MESSAGE_SCORE_TO_ADD);
        }
        this.toAddScore = Optional.ofNullable(student);
        this.scoreToAdd = scoreToAdd;
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an AddScoreCommand to increase the specified {@code Student}'s participation score by index.
     */
    public AddScoreCommand(Index index, int scoreToAdd) throws CommandException {
        requireNonNull(index);
        requireNonNull(scoreToAdd);
        // Specified index of student to add participation score
        if (scoreToAdd <= 0) {
            throw new CommandException(MESSAGE_SCORE_TO_ADD);
        }
        this.index = Optional.ofNullable(index);
        this.toAddScore = Optional.empty();
        this.scoreToAdd = scoreToAdd;
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

        return executeAddScoreOneStudent(model, key, uniqueLesson, currentStudentInfoList, targetStudentInfo);
    }

    /**
     * Executes the  add one student's participation score command and returns the result message.
     */
    private CommandResult executeAddScoreOneStudent(Model model, GroupLessonKey key, Lesson lesson,
        ObservableList<StudentInfo> currentStudentInfoList,
        StudentInfo targetStudentInfo) throws CommandException {
        // Gets the updated StudentInfoList with the updated targetStudentInfo
        UniqueList<StudentInfo> updatedListForAddScoreOneStudent =
            getUpdatedListForAddScoreOneStudent(currentStudentInfoList, targetStudentInfo);

        // Updates the modelManager and lesson object with the new StudentInfoList
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForAddScoreOneStudent);
        lesson.setStudentsInfo(updatedListForAddScoreOneStudent);
        model.updateLessonList();
        model.updateStudentsInfoList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudentInfo.getStudent(), newScore));
    }

    /**
     * Returns the {@code targetStudentInfo} object in the {@code currentStudentInfoList}.
     */
    private StudentInfo getTargetStudentInfo(ObservableList<StudentInfo> currentStudentInfoList)
        throws CommandException {
        if (this.isByIndex) {
            // Add Score by index
            assert this.index.isPresent();
            Index targetIndex = this.index.get();

            // Return error message if index is out of range
            if (targetIndex.getZeroBased() >= currentStudentInfoList.size() || index.get().getOneBased() == 0) {
                throw new CommandException(
                    String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, targetIndex.getOneBased()));
            }
            return currentStudentInfoList.get(targetIndex.getZeroBased());
        }

        assert this.toAddScore.isPresent();
        Student student = this.toAddScore.get();

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
     * Add score to the given {@code targetStudentInfo}'s {@code Participation} field
     * in the {@code currentStudentInfoList}.
     * Returns the {@code updatedStudentInfoList}.
     *
     * @param currentStudentInfoList the current student info list.
     * @param targetStudentInfo the target student info to add score.
     */
    private UniqueList<StudentInfo> getUpdatedListForAddScoreOneStudent(
        ObservableList<StudentInfo> currentStudentInfoList, StudentInfo targetStudentInfo) throws CommandException {
        if (!targetStudentInfo.getAttendance().isPresent()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_PRESENT, this.toAddScore.get()));
        }
        this.score = targetStudentInfo.getParticipation().getScore();
        newScore = score + scoreToAdd;
        if (newScore > 5 || newScore < 0) {
            throw new CommandException(MESSAGE_SCORE_NOT_WITHIN_RANGE);
        }
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentStudentInfoList);
        StudentInfo updatedStudentInfo = new StudentInfo(targetStudentInfo.getStudent(),
            new Participation().setNewScore(newScore), targetStudentInfo.getAttendance());
        updatedList.setElement(targetStudentInfo, updatedStudentInfo);
        return updatedList;
    }

    /**
     * Check whether 2 AddScoreCommand objects are identical
     * @param other The AddScoreCommand to be compared
     * @return True if the 2 commands are identical, False otherwise
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddScoreCommand // instanceof handles nulls
            && this.scoreToAdd == ((AddScoreCommand) other).scoreToAdd)
            && this.toAddScore.equals(((AddScoreCommand) other).toAddScore)
            && this.index.equals(((AddScoreCommand) other).index)
            && this.isByIndex == (((AddScoreCommand) other).isByIndex);
    }
}
