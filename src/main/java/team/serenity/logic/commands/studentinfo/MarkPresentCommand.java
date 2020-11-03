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

/**
 * Marks the attendance of a class or a student in the class.
 */
public class MarkPresentCommand extends Command {

    public static final String COMMAND_WORD = "markpresent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance: present";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked present!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific student or all students present from a lesson. \n"
            + "Parameters: "
            + "all or "
            + PREFIX_NAME + " STUDENT_NAME "
            + PREFIX_MATRIC + " STUDENT_NUMBER " + "or INDEX\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_NAME + " Aaron Tan "
            + PREFIX_MATRIC + " A0123456U\n"
            + "or " + COMMAND_WORD + " 2";

    private Optional<Student> toMarkPresent;
    private Optional<Index> index;
    private boolean isByIndex;
    private boolean isWholeClass;

    /**
     * Creates an MarkPresentCommand to mark all {@code Student}s present.
     */
    public MarkPresentCommand() {
        // Mark all students present
        this.isWholeClass = true;
        this.toMarkPresent = Optional.empty();
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkPresentCommand to mark the specified {@code Student} present.
     */
    public MarkPresentCommand(Student student) {
        requireNonNull(student);
        this.isWholeClass = false;
        // Specified student to mark present
        this.toMarkPresent = Optional.ofNullable(student);
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates an MarkPresentCommand to mark the specified {@code Student} present by index.
     */
    public MarkPresentCommand(Index index) {
        requireNonNull(index);
        this.isWholeClass = false;
        // Specified index of student to mark present
        this.index = Optional.ofNullable(index);
        this.toMarkPresent = Optional.empty();
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

        // Mark all students present
        if (this.isWholeClass) {
            return executeMarkAll(model, key, uniqueLesson, currentStudentInfoList);
        }

        // Mark one student present
        StudentInfo targetStudentInfo = getTargetStudentInfo(currentStudentInfoList);
        return executeMarkOneStudent(model, key, uniqueLesson, currentStudentInfoList, targetStudentInfo);
    }

    private CommandResult executeMarkAll(Model model, GroupLessonKey key, Lesson lesson,
                                         ObservableList<StudentInfo> currentStudentInfoList) {
        UniqueList<StudentInfo> updatedListForMarkAll = getUpdatedListForMarkAll(currentStudentInfoList);
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForMarkAll);
        lesson.setStudentsInfo(updatedListForMarkAll);
        model.updateStudentsInfoList();
        return new CommandResult(MESSAGE_ALL_SUCCESS);
    }

    private CommandResult executeMarkOneStudent(Model model, GroupLessonKey key, Lesson lesson,
                                                ObservableList<StudentInfo> currentStudentInfoList,
                                                StudentInfo targetStudentInfo) {
        UniqueList<StudentInfo> updatedListForMarkOneStudent =
                getUpdatedListForMarkOneStudent(currentStudentInfoList, targetStudentInfo);
        model.setListOfStudentsInfoToGroupLessonKey(key, updatedListForMarkOneStudent);
        lesson.setStudentsInfo(updatedListForMarkOneStudent);
        model.updateStudentsInfoList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudentInfo.getStudent()));
    }

    private StudentInfo getTargetStudentInfo(ObservableList<StudentInfo> uniqueStudentInfoList)
            throws CommandException {
        if (this.isByIndex) {
            // Mark present StudentInfo by index
            assert this.index.isPresent();
            Index targetIndex = this.index.get();

            // Return error message if index is out of range
            if (targetIndex.getZeroBased() >= uniqueStudentInfoList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, targetIndex.getOneBased()));
            }
            return uniqueStudentInfoList.get(targetIndex.getZeroBased());
        }

        // Since it is not mark all or mark by index, there should be a student
        assert this.toMarkPresent.isPresent();
        Student student = this.toMarkPresent.get();

        Optional<StudentInfo> optionalStudentInfo =
                uniqueStudentInfoList.stream().filter(s -> s.containsStudent(student)).findFirst();

        // Return error message if Student not found in StudentInfoList
        if (optionalStudentInfo.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, student));
        }
        return optionalStudentInfo.get();
    }

    private UniqueList<StudentInfo> getUpdatedListForMarkAll(ObservableList<StudentInfo> currentList) {
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentList);
        List<StudentInfo> updatedStudentInfo = updatedList.stream()
                .map(s -> new StudentInfo(s.getStudent(), s.getParticipation(), new Attendance(true)))
                .collect(Collectors.toList());
        updatedList.setElementsWithList(updatedStudentInfo);
        return updatedList;
    }

    private UniqueList<StudentInfo> getUpdatedListForMarkOneStudent(ObservableList<StudentInfo> currentList,
                                                                    StudentInfo targetStudentInfo) {
        UniqueList<StudentInfo> updatedList = new UniqueStudentInfoList();
        updatedList.setElementsWithList(currentList);
        StudentInfo updatedStudentInfo = new StudentInfo(targetStudentInfo.getStudent(),
                targetStudentInfo.getParticipation(), new Attendance(true));
        updatedList.setElement(targetStudentInfo, updatedStudentInfo);
        return updatedList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkPresentCommand // instanceof handles nulls
                && this.toMarkPresent.equals(((MarkPresentCommand) other).toMarkPresent)
                && this.index.equals(((MarkPresentCommand) other).index)
                && this.isByIndex == (((MarkPresentCommand) other).isByIndex)
                && this.isWholeClass == ((MarkPresentCommand) other).isWholeClass);
    }

}
