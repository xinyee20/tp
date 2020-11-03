package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.JOHN;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.student.Student;

class MarkAbsentCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAbsentCommand((Index) null));
        assertThrows(NullPointerException.class, () -> new MarkAbsentCommand((Student) null));
    }

    @Test
    public void execute_unmarkStudent_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toMarkAbsent = new Student("Aaron Tan", "A0123456U");

        CommandResult commandResult = new MarkAbsentCommand(toMarkAbsent).execute(modelStub);
        assertEquals(String.format(MarkAbsentCommand.MESSAGE_SUCCESS, toMarkAbsent),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongName_throwsCommandException() {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new Student("Aaron", "A0123456U");
        Student wrongNameTwo = new Student("Betty Tan", "A0123456U");
        MarkAbsentCommand markAbsentCommandOne = new MarkAbsentCommand(wrongNameOne);
        MarkAbsentCommand markAbsentCommandTwo = new MarkAbsentCommand(wrongNameTwo);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> markAbsentCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> markAbsentCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new Student("Aaron Tan", "A0000000U");
        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(wrongNumber);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> markAbsentCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toMarkAbsent = new Student("Aaron Tan", "A0123456U");
        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(toMarkAbsent);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> markAbsentCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toMarkAbsent = new Student("Aaron Tan", "A0123456U");
        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(toMarkAbsent);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> markAbsentCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toMarkAbsent = new Student("Aaron Tan", "A0123456U");

        CommandResult commandResult = new MarkAbsentCommand(validIndex).execute(modelStub);
        assertEquals(String.format(MarkAbsentCommand.MESSAGE_SUCCESS, toMarkAbsent),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(wrongIndex);

        assertThrows(CommandException.class, () -> markAbsentCommand.execute(modelStub));
    }

    @Test
    public void execute_markAllStudent_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();

        CommandResult commandResult = new MarkAbsentCommand().execute(modelStub);
        assertEquals(String.format(MarkAbsentCommand.MESSAGE_ALL_SUCCESS), commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        MarkAbsentCommand markAllAbsentCommandA = new MarkAbsentCommand();
        MarkAbsentCommand markStudentAbsentCommandA = new MarkAbsentCommand(AARON);
        MarkAbsentCommand copyMarkStudentAbsentCommandA = new MarkAbsentCommand(AARON);
        MarkAbsentCommand markStudentAbsentCommandB = new MarkAbsentCommand(JOHN);
        MarkAbsentCommand markIndexAbsentCommandA = new MarkAbsentCommand(INDEX_FIRST);
        MarkAbsentCommand copyMarkIndexAbsentCommandA = new MarkAbsentCommand(INDEX_FIRST);
        MarkAbsentCommand markIndexAbsentCommandB = new MarkAbsentCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(markAllAbsentCommandA.equals(markAllAbsentCommandA));
        assertTrue(markStudentAbsentCommandA.equals(markStudentAbsentCommandA));
        assertTrue(markIndexAbsentCommandA.equals(markIndexAbsentCommandA));

        // same values -> return true
        assertTrue(markStudentAbsentCommandA.equals(copyMarkStudentAbsentCommandA));
        assertTrue(markIndexAbsentCommandA.equals(copyMarkIndexAbsentCommandA));

        // different types -> return false
        assertFalse(markStudentAbsentCommandA.equals(markIndexAbsentCommandA));

        // different values -> return different
        assertFalse(markStudentAbsentCommandA.equals(markStudentAbsentCommandB));
        assertFalse(markIndexAbsentCommandA.equals(markIndexAbsentCommandB));
    }
}

