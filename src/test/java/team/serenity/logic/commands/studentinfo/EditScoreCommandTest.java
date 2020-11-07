package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_NOT_WITHIN_RANGE;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.StudentBuilder;

class EditScoreCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditScoreCommand((Index) null, 1));
        assertThrows(NullPointerException.class, () -> new EditScoreCommand((Student) null, 1));
    }

    @Test
    public void execute_setScoreOutOfRange_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSetScore = new StudentBuilder().build();
        int scoreOutOfRange = 6;
        EditScoreCommand setScoreCommand = new EditScoreCommand(toSetScore, scoreOutOfRange);

        assertThrows(CommandException.class, MESSAGE_SCORE_NOT_WITHIN_RANGE, () -> setScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_setScore_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSetScore = new StudentBuilder().build();
        int validScore = 1;

        CommandResult commandResult = new EditScoreCommand(toSetScore, validScore).execute(modelStub);
        assertEquals(String.format(EditScoreCommand.MESSAGE_SUCCESS, toSetScore, validScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toSetScore = new StudentBuilder().build();
        int validScore = 1;
        EditScoreCommand setScoreCommand = new EditScoreCommand(toSetScore, validScore);

        assertThrows(CommandException.class,
                String.format(EditScoreCommand.MESSAGE_STUDENT_NOT_PRESENT,
                        toSetScore), () -> setScoreCommand.execute(modelStub));;
    }

    @Test
    public void execute_wrongName_throwsCommandException() {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        int validScore = 1;
        EditScoreCommand setScoreCommandOne = new EditScoreCommand(wrongNameOne, validScore);
        EditScoreCommand setScoreCommandTwo = new EditScoreCommand(wrongNameTwo, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> setScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> setScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        int validScore = 1;
        EditScoreCommand setScoreCommand = new EditScoreCommand(wrongNumber, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> setScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toSetScore = new StudentBuilder().build();
        int validScore = 1;
        EditScoreCommand setScoreCommand = new EditScoreCommand(toSetScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> setScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toSetScore = new StudentBuilder().build();
        int validScore = 1;
        EditScoreCommand setScoreCommand = new EditScoreCommand(toSetScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> setScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSetScore = new StudentBuilder().build();
        int validScore = 1;

        CommandResult commandResult = new EditScoreCommand(validIndex, validScore).execute(modelStub);
        assertEquals(String.format(EditScoreCommand.MESSAGE_SUCCESS, toSetScore, validScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        int validScore = 1;
        EditScoreCommand setScoreCommand = new EditScoreCommand(wrongIndex, validScore);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> setScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        int validScore = 1;
        EditScoreCommand setScoreStudentCommandA = new EditScoreCommand(AARON, validScore);
        EditScoreCommand copySetScoreStudentCommandA = new EditScoreCommand(AARON, validScore);
        EditScoreCommand setScoreStudentCommandB = new EditScoreCommand(BENJAMIN, validScore);
        EditScoreCommand setScoreIndexCommandA = new EditScoreCommand(INDEX_FIRST, validScore);
        EditScoreCommand copySetScoreIndexCommandA = new EditScoreCommand(INDEX_FIRST, validScore);
        EditScoreCommand setScoreIndexCommandB = new EditScoreCommand(INDEX_SECOND, validScore);

        // same object -> returns true
        assertTrue(setScoreStudentCommandA.equals(setScoreStudentCommandA));
        assertTrue(setScoreIndexCommandA.equals(setScoreIndexCommandA));

        // same values -> return true
        assertTrue(setScoreStudentCommandA.equals(copySetScoreStudentCommandA));
        assertTrue(setScoreIndexCommandA.equals(copySetScoreIndexCommandA));

        // different types -> return false
        assertFalse(setScoreStudentCommandA.equals(setScoreIndexCommandA));

        // different values -> return different
        assertFalse(setScoreStudentCommandA.equals(setScoreStudentCommandB));
        assertFalse(setScoreIndexCommandA.equals(setScoreIndexCommandB));
    }

}
