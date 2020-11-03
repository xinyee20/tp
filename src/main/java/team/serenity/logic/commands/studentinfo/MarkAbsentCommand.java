package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

public class MarkAbsentCommand extends Command {

    public static final String COMMAND_WORD = "markabsent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance:  absent";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked absent!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific student or all students absent from a lesson.\n"
            + "Parameters: "
            + "all or "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_MATRIC + "STUDENT_NUMBER " + "or INDEX(starting from 1)\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_NAME + "Aaron Tan "
            + PREFIX_MATRIC + "A0123456U\n"
            + "or " + COMMAND_WORD + " 2";

    private Optional<Student> toMarkAbsent;
    private Optional<Index> index;
    private boolean isByIndex;
    private boolean isWholeClass;
    private boolean isCorrectStudent;

    /**
     * Creates an MarkAbsentCommand to mark all {@code Student} absent.
     */
    public MarkAbsentCommand() {
        // Mark all students absent
        this.isWholeClass = true;
        this.toMarkAbsent = Optional.empty();
        this.index = Optional.empty();
    }

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent.
     */
    public MarkAbsentCommand(Student student) {
        requireNonNull(student);
        this.isWholeClass = false;
        // Specified student to mark present
        this.toMarkAbsent = Optional.ofNullable(student);
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkAbsentCommand to mark the specified {@code Student} absent by index.
     */
    public MarkAbsentCommand(Index index) {
        requireNonNull(index);
        this.isWholeClass = false;
        // Specified index of student to mark present
        this.index = Optional.ofNullable(index);
        this.toMarkAbsent = Optional.empty();
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

        // Mark all students absent
        if (this.isWholeClass) {
            return executeMarkAll(model, key, uniqueLesson, currentStudentInfoList);
        }

        // Mark one student absent
        StudentInfo targetStudentInfo = getTargetStudentInfo(currentStudentInfoList);
        return executeMarkOneStudent(model, key, uniqueLesson, currentStudentInfoList, targetStudentInfo);
    }

    /**
     * Executes the mark all student absent command and returns the result message.
     */
    private CommandResult executeMarkAll(Model model, GroupLessonKey key, Lesson lesson,
                                         ObservableList<StudentInfo> currentStudentInfoList) {
        // Gets the updated StudentInfoList with all the updated StudentsInfo
        UniqueList<StudentInfo> updatedListForMarkAll = getUpdatedListForMarkAll(currentStudentInfoList);

        // Updates the modelManager and lesson object with the new StudentInfoList
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForMarkAll);
        lesson.setStudentsInfo(updatedListForMarkAll);
        return new CommandResult(MESSAGE_ALL_SUCCESS);
    }

    /**
     * Executes the mark one student absent command and returns the result message.
     */
    private CommandResult executeMarkOneStudent(Model model, GroupLessonKey key, Lesson lesson,
                                                ObservableList<StudentInfo> currentStudentInfoList,
                                                StudentInfo targetStudentInfo) {
        // Gets the updated StudentInfoList with the updated targetStudentInfo
        UniqueList<StudentInfo> updatedListForMarkOneStudent =
                getUpdatedListForMarkOneStudent(currentStudentInfoList, targetStudentInfo);

        // Updates the modelManager and lesson object with the new StudentInfoList
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForMarkOneStudent);
        lesson.setStudentsInfo(updatedListForMarkOneStudent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudentInfo.getStudent()));
    }

    /**
     * Returns the {@code targetStudentInfo} object in the {@code currentStudentInfoList}.
     */
    private StudentInfo getTargetStudentInfo(ObservableList<StudentInfo> currentStudentInfoList)
            throws CommandException {
        if (this.isByIndex) {
            // Mark absent StudentInfo by index
            assert this.index.isPresent();
            Index targetIndex = this.index.get();

            // Return error message if index is out of range
            if (targetIndex.getZeroBased() >= currentStudentInfoList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, targetIndex.getOneBased()));
            }
            return currentStudentInfoList.get(targetIndex.getZeroBased());
        }

        // Since it is not mark all or mark by index, there should be a student
        assert this.toMarkAbsent.isPresent();
        Student student = this.toMarkAbsent.get();

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
     * Sets all {@code StudentInfo}'s {@code Attendance}'s {@code isPresent} field
     * in the {@code currentStudentInfoList} to {@code false}.
     * Returns the {@code updatedStudentInfoList}.
     *
     * @param currentStudentInfoList the current student info list.
     */
    private UniqueList<StudentInfo> getUpdatedListForMarkAll(ObservableList<StudentInfo> currentStudentInfoList) {
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentStudentInfoList);
        List<StudentInfo> updatedStudentInfo = updatedList.stream()
                .map(s -> new StudentInfo(s.getStudent(), s.getParticipation(), new Attendance(false)))
                .collect(Collectors.toList());
        updatedList.setElementsWithList(updatedStudentInfo);
        return updatedList;
    }

    /**
     * Sets the given {@code targetStudentInfo}'s {@code Attendance}'s {@code isPresent} field
     * in the {@code currentStudentInfoList} to {@code false}.
     * Returns the {@code updatedStudentInfoList}.
     *
     * @param currentStudentInfoList the current student info list.
     * @param targetStudentInfo the target student info to mark absent.
     */
    private UniqueList<StudentInfo> getUpdatedListForMarkOneStudent(ObservableList<StudentInfo> currentStudentInfoList,
                                                                    StudentInfo targetStudentInfo) {
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentStudentInfoList);
        StudentInfo updatedStudentInfo = new StudentInfo(targetStudentInfo.getStudent(),
                targetStudentInfo.getParticipation(), new Attendance(false));
        updatedList.setElement(targetStudentInfo, updatedStudentInfo);
        return updatedList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkAbsentCommand // instanceof handles nulls
                && this.toMarkAbsent.equals(((MarkAbsentCommand) other).toMarkAbsent)
                && this.index.equals(((MarkAbsentCommand) other).index)
                && this.isCorrectStudent == ((MarkAbsentCommand) other).isCorrectStudent
                && this.isWholeClass == ((MarkAbsentCommand) other).isWholeClass);
    }

}
