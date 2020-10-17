package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.Student;

class UnmarkAttCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAbsentCommand(null));
    }

    @Test
    public void execute_unmarkStudent_success() {
//        ModelStubGroupAndLessonFiltered lessonView = new ModelStubGroupAndLessonFiltered();
//        Student student = model.getFilteredGroupList().get(0).
//                getLessonsAsUnmodifiableObservableList().get(0).
//                getStudentsInfoAsUnmodifiableObservableList().get(0).
//                getStudent();
//
//        ModelStubStudentMarkedAbsent modelStub = new ModelStubStudentMarkedAbsent();
//
//        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(new Student(student.getName(), student.getStudentNumber()));
//        String expectedMessage = String.format(MarkAbsentCommand.MESSAGE_SUCCESS, student);
//        Attendance studentAtt = model.getFilteredLessonList().get(0).getStudentsInfoAsUnmodifiableObservableList().get(0).getAttendance();
//
//        assertTrue(studentAtt.getAttendance());
//        CommandResult commandResult = markAbsentCommand.execute(modelStub);
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongName_throwsCommandException() {

    }

    @Test
    public void execute_wrongStudentId_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentName_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

}
