package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
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
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

/**
 * Flags the attendance of a student in the class.
 */
public class FlagAttCommand extends Command {

    public static final String COMMAND_WORD = "flagatt";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance is flagged!";
    public static final String MESSAGE_FAILURE = "Student should be absent to be flagged!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flags the attendance of a specific student for a lesson. \n"
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_MATRIC + "STUDENT_NUMBER " + "or INDEX(starting from 1)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Aaron Tan "
            + PREFIX_MATRIC + "A0123456U\n"
            + "or " + COMMAND_WORD + " 2";


    private Optional<Student> toFlagAtt;
    private Optional<Index> index;
    private boolean isByIndex;
    private boolean isCorrectStudent;

    /**
     * Creates an FlagAttCommand to flag a specified {@code Student}'s attendance.
     */
    public FlagAttCommand(Student student) {
        requireNonNull(student);
        // Specified student to flag attendance
        this.toFlagAtt = Optional.ofNullable(student);
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an FlagAttCommand to flag a specified {@code Student}'s attendance by index.
     */
    public FlagAttCommand(Index index) {
        requireNonNull(index);
        // Specified index of student to flag attendance
        this.index = Optional.ofNullable(index);
        this.toFlagAtt = Optional.empty();
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

        return executeFlagOneStudent(model, key, uniqueLesson, currentStudentInfoList, targetStudentInfo);
    }

    /**
     * Executes the flag one student attendance command and returns the result message.
     */
    private CommandResult executeFlagOneStudent(Model model, GroupLessonKey key, Lesson lesson,
                                                ObservableList<StudentInfo> currentStudentInfoList,
                                                StudentInfo targetStudentInfo) throws CommandException {
        // Gets the updated StudentInfoList with the updated targetStudentInfo
        UniqueList<StudentInfo> updatedListForFlagOneStudent =
                getUpdatedListForFlagOneStudent(currentStudentInfoList, targetStudentInfo);

        // Updates the modelManager and lesson object with the new StudentInfoList
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForFlagOneStudent);
        lesson.setStudentsInfo(updatedListForFlagOneStudent);
        model.updateLessonList();
        model.updateStudentsInfoList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudentInfo.getStudent()));
    }

    /**
     * Returns the {@code targetStudentInfo} object in the {@code currentStudentInfoList}.
     */
    private StudentInfo getTargetStudentInfo(ObservableList<StudentInfo> currentStudentInfoList)
            throws CommandException {
        if (this.isByIndex) {
            // Flag Attendance by index
            assert this.index.isPresent();
            Index targetIndex = this.index.get();

            // Return error message if index is out of range
            if (targetIndex.getZeroBased() >= currentStudentInfoList.size() || index.get().getOneBased() == 0) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, targetIndex.getOneBased()));
            }
            return currentStudentInfoList.get(targetIndex.getZeroBased());
        }

        assert this.toFlagAtt.isPresent();
        Student student = this.toFlagAtt.get();

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
     * Sets the given {@code targetStudentInfo}'s {@code Attendance}'s {@code isFlagged} field
     * in the {@code currentStudentInfoList} to {@code true}.
     * Returns the {@code updatedStudentInfoList}.
     *
     * @param currentStudentInfoList the current student info list.
     * @param targetStudentInfo the target student info to flag attendance.
     */
    private UniqueList<StudentInfo> getUpdatedListForFlagOneStudent(ObservableList<StudentInfo> currentStudentInfoList,
                                                                    StudentInfo targetStudentInfo) throws CommandException {
        if (targetStudentInfo.getAttendance().isPresent()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentStudentInfoList);
        StudentInfo updatedStudentInfo = new StudentInfo(targetStudentInfo.getStudent(),
                targetStudentInfo.getParticipation(), new Attendance(false, true));
        updatedList.setElement(targetStudentInfo, updatedStudentInfo);
        return updatedList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlagAttCommand // instanceof handles nulls
                && this.toFlagAtt.equals(((FlagAttCommand) other).toFlagAtt)
                && this.index.equals(((FlagAttCommand) other).index)
                && this.isCorrectStudent == ((FlagAttCommand) other).isCorrectStudent);
    }
}
