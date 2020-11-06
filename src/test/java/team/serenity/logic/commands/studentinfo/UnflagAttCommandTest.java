package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
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
import team.serenity.testutil.StudentBuilder;

class UnflagAttCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnflagAttCommand((Index) null));
        assertThrows(NullPointerException.class, () -> new UnflagAttCommand((Student) null));
    }

    @Test
    public void execute_unflagStudent_success() throws CommandException {
        ModelStubWithStudentsFlagged modelStub = new ModelStubWithStudentsFlagged();
        Student toUnflagAtt = new StudentBuilder().build();

        CommandResult commandResult = new UnflagAttCommand(toUnflagAtt).execute(modelStub);
        assertEquals(String.format(UnflagAttCommand.MESSAGE_SUCCESS, toUnflagAtt),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsFlagged modelStub = new ModelStubWithStudentsFlagged();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        UnflagAttCommand unflagAttCommandOne = new UnflagAttCommand(wrongNameOne);
        UnflagAttCommand unflagAttCommandTwo = new UnflagAttCommand(wrongNameTwo);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> unflagAttCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> unflagAttCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsFlagged modelStub = new ModelStubWithStudentsFlagged();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        UnflagAttCommand unflagAttCommand = new UnflagAttCommand(wrongNumber);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> unflagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toUnflagAtt = new StudentBuilder().build();
        UnflagAttCommand unflagAttCommand = new UnflagAttCommand(toUnflagAtt);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> unflagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toUnflagAtt = new StudentBuilder().build();
        UnflagAttCommand unflagAttCommand = new UnflagAttCommand(toUnflagAtt);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> unflagAttCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexFlagged modelStub = new ModelStubWithIndexFlagged();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toUnflagAtt = new StudentBuilder().build();

        CommandResult commandResult = new UnflagAttCommand(validIndex).execute(modelStub);
        assertEquals(String.format(UnflagAttCommand.MESSAGE_SUCCESS, toUnflagAtt),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexFlagged modelStub = new ModelStubWithIndexFlagged();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        UnflagAttCommand unflagAttCommand = new UnflagAttCommand(wrongIndex);

        assertThrows(CommandException.class, String.format(
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> unflagAttCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        UnflagAttCommand unflagStudentCommandA = new UnflagAttCommand(AARON);
        UnflagAttCommand copyUnflagStudentCommandA = new UnflagAttCommand(AARON);
        UnflagAttCommand unflagStudentCommandB = new UnflagAttCommand(JOHN);
        UnflagAttCommand unflagIndexCommandA = new UnflagAttCommand(INDEX_FIRST);
        UnflagAttCommand copyUnflagIndexCommandA = new UnflagAttCommand(INDEX_FIRST);
        UnflagAttCommand unflagIndexCommandB = new UnflagAttCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unflagStudentCommandA.equals(unflagStudentCommandA));
        assertTrue(unflagIndexCommandA.equals(unflagIndexCommandA));

        // same values -> return true
        assertTrue(unflagStudentCommandA.equals(copyUnflagStudentCommandA));
        assertTrue(unflagIndexCommandA.equals(copyUnflagIndexCommandA));

        // different types -> return false
        assertFalse(unflagStudentCommandA.equals(unflagIndexCommandA));

        // different values -> return different
        assertFalse(unflagStudentCommandA.equals(unflagStudentCommandB));
        assertFalse(unflagIndexCommandA.equals(unflagIndexCommandB));
    }
}
