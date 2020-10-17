package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Serenity;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.GrpContainsKeywordPredicate;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;

class MarkAttCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_markStudent_success() {

//        ModelStubGroupAndLessonFiltered lessonView = new ModelStubGroupAndLessonFiltered();
//        Student student = model.getFilteredGroupList().get(0).
//                getLessonsAsUnmodifiableObservableList().get(0).
//                getStudentsInfoAsUnmodifiableObservableList().get(0).
//                getStudent();
//
//        ModelStubMarkStudentPresent modelStub = new ModelStubMarkStudentPresent();
//
//        String expectedMessage = String.format(MarkPresentCommand.MESSAGE_SUCCESS, student);
//
//        MarkPresentCommand markPresentCommand = new MarkPresentCommand(new Student(student.getName(), student.getStudentNumber()));
//        CommandResult commandResult = markPresentCommand.execute(modelStub);
//
//        Attendance studentAtt = model.getFilteredLessonList().get(0).getStudentsInfoAsUnmodifiableObservableList().get(0).getAttendance();
//        assertTrue(studentAtt.getAttendance());
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongName_throwsCommandException() {

//        ModelStubGroupAndLessonFiltered lessonView = new ModelStubGroupAndLessonFiltered();
//        Student student = new Student("wrongName", "validId");
//        StudentInfo studentInfo = model.getFilteredGroupList().get(0).
//                getLessonsAsUnmodifiableObservableList().get(0).
//                getStudentsInfoAsUnmodifiableObservableList().get(0);
//
//        ModelStubMarkStudentPresent modelStub = new ModelStubMarkStudentPresent();
//
//        String expectedMessage = String.format(MarkPresentCommand.MESSAGE_STUDENT_NOT_FOUND, student);
//
//        MarkPresentCommand markPresentCommand = new MarkPresentCommand(student);
//        CommandResult commandResult = markPresentCommand.execute(modelStub);
//
//        assertFalse(studentInfo.containsStudent(student));
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());


        /**
         * Enter lesson
         * Get UniqueStudentInfo List
         * Try to find the correct student but cannot find student with name given
         * Throw Exception
         */

    }

    @Test
    public void execute_wrongStudentId_throwsCommandException() {

//        ModelStubGroupAndLessonFiltered lessonView = new ModelStubGroupAndLessonFiltered();
//        Student student = new Student("ValidName", "wrongId");
//        StudentInfo studentInfo = model.getFilteredGroupList().get(0).
//                getLessonsAsUnmodifiableObservableList().get(0).
//                getStudentsInfoAsUnmodifiableObservableList().get(0);
//
//        ModelStubMarkStudentPresent modelStub = new ModelStubMarkStudentPresent();
//
//        String expectedMessage = String.format(MarkPresentCommand.MESSAGE_STUDENT_NOT_FOUND, student);
//
//        MarkPresentCommand markPresentCommand = new MarkPresentCommand(student);
//        CommandResult commandResult = markPresentCommand.execute(modelStub);
//
//        assertFalse(studentInfo.containsStudent(student));
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        /**
         * Enter lesson
         * Get UniqueStudentInfo List
         * Try to find the correct student but cannot find student with id given
         * Throw Exception
         */
    }


    @Test
    public void execute_markAllStudent_success() {
//
//        ModelStubGroupAndLessonFiltered lessonView = new ModelStubGroupAndLessonFiltered();
//        ModelStubAllMarkedPresent modelStub = new ModelStubAllMarkedPresent();
//        MarkPresentCommand markAttCommand = new MarkPresentCommand();
//        String expectedMessage = String.format(MarkPresentCommand.MESSAGE_ALL_SUCCESS);
//        CommandResult commandResult = markAttCommand.execute(modelStub);
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

    }

}
