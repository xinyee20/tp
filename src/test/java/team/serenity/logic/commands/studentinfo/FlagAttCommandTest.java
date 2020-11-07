package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.logic.commands.studentinfo.FlagAttCommand.MESSAGE_FAILURE;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.GEORGE;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.StudentBuilder;

class FlagAttCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FlagAttCommand((Index) null));
        assertThrows(NullPointerException.class, () -> new FlagAttCommand((Student) null));
    }

    @Test
    public void execute_flagStudent_success() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toFlagAtt = new StudentBuilder().build();

        CommandResult commandResult = new FlagAttCommand(toFlagAtt).execute(modelStub);
        assertEquals(String.format(FlagAttCommand.MESSAGE_SUCCESS, toFlagAtt),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentPresent_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toFlagAtt = new StudentBuilder().build();
        FlagAttCommand flagAttCommand = new FlagAttCommand(toFlagAtt);

        assertThrows(CommandException.class,
                String.format(MESSAGE_FAILURE), () -> flagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").build();
        FlagAttCommand flagAttCommandOne = new FlagAttCommand(wrongNameOne);
        FlagAttCommand flagAttCommandTwo = new FlagAttCommand(wrongNameTwo);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> flagAttCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> flagAttCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student wrongNumber = new StudentBuilder().withId("A0000000U").build();
        FlagAttCommand flagAttCommand = new FlagAttCommand(wrongNumber);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> flagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toFlagAtt = new StudentBuilder().build();
        FlagAttCommand flagAttCommand = new FlagAttCommand(toFlagAtt);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> flagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toFlagAtt = new StudentBuilder().build();
        FlagAttCommand flagAttCommand = new FlagAttCommand(toFlagAtt);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> flagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexAbsent modelStub = new ModelStubWithIndexAbsent();
        Student toFlagAtt = new StudentBuilder().build();

        CommandResult commandResult = new FlagAttCommand(INDEX_FIRST).execute(modelStub);
        assertEquals(String.format(FlagAttCommand.MESSAGE_SUCCESS, toFlagAtt),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexAbsent modelStub = new ModelStubWithIndexAbsent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        FlagAttCommand flagAttCommand = new FlagAttCommand(wrongIndex);

        assertThrows(CommandException.class,
                String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                        wrongIndex.getOneBased()), () -> flagAttCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        FlagAttCommand flagStudentCommandA = new FlagAttCommand(AARON);
        FlagAttCommand copyFlagStudentCommandA = new FlagAttCommand(AARON);
        FlagAttCommand flagStudentCommandB = new FlagAttCommand(GEORGE);
        FlagAttCommand flagIndexCommandA = new FlagAttCommand(INDEX_FIRST);
        FlagAttCommand copyFlagIndexCommandA = new FlagAttCommand(INDEX_FIRST);
        FlagAttCommand flagIndexCommandB = new FlagAttCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(flagStudentCommandA.equals(flagStudentCommandA));
        assertTrue(flagIndexCommandA.equals(flagIndexCommandA));

        // same values -> return true
        assertTrue(flagStudentCommandA.equals(copyFlagStudentCommandA));
        assertTrue(flagIndexCommandA.equals(copyFlagIndexCommandA));

        // different types -> return false
        assertFalse(flagStudentCommandA.equals(flagIndexCommandA));

        // different values -> return different
        assertFalse(flagStudentCommandA.equals(flagStudentCommandB));
        assertFalse(flagIndexCommandA.equals(flagIndexCommandB));
    }

}
