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

class AddScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Index) null, 1));
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Student) null, 1));
    }

    @Test
    public void execute_addScoreOutOfRange_throwCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toAddScore = new StudentBuilder().build();
        int scoreOutOfRange = 6;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, scoreOutOfRange);

        assertThrows(CommandException.class, MESSAGE_SCORE_NOT_WITHIN_RANGE, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_addScore_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toAddScore = new StudentBuilder().build();
        int validAddScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore + validAddScore;

        CommandResult commandResult = new AddScoreCommand(toAddScore, validAddScore).execute(modelStub);
        assertEquals(String.format(AddScoreCommand.MESSAGE_SUCCESS, toAddScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toAddScore = new StudentBuilder().build();
        int validAddScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validAddScore);

        assertThrows(CommandException.class, String.format(
                AddScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toAddScore), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        int validScore = 1;
        AddScoreCommand addScoreCommandOne = new AddScoreCommand(wrongNameOne, validScore);
        AddScoreCommand addScoreCommandTwo = new AddScoreCommand(wrongNameTwo, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> addScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> addScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongNumber, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() throws CommandException {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toAddScore = new StudentBuilder().build();
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() throws CommandException {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toAddScore = new StudentBuilder().build();
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSetScore = new StudentBuilder().build();
        int validAddScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore + validAddScore;

        CommandResult commandResult = new AddScoreCommand(validIndex, validAddScore).execute(modelStub);
        assertEquals(String.format(AddScoreCommand.MESSAGE_SUCCESS, toSetScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongIndex, validScore);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() throws CommandException {
        int validAddScore = 1;
        AddScoreCommand addScoreStudentCommandA = new AddScoreCommand(AARON, validAddScore);
        AddScoreCommand copyAddScoreStudentCommandA = new AddScoreCommand(AARON, validAddScore);
        AddScoreCommand addScoreStudentCommandB = new AddScoreCommand(BENJAMIN, validAddScore);
        AddScoreCommand addScoreIndexCommandA = new AddScoreCommand(INDEX_FIRST, validAddScore);
        AddScoreCommand copyAddScoreIndexCommandA = new AddScoreCommand(INDEX_FIRST, validAddScore);
        AddScoreCommand addScoreIndexCommandB = new AddScoreCommand(INDEX_SECOND, validAddScore);

        // same object -> returns true
        assertTrue(addScoreStudentCommandA.equals(addScoreStudentCommandA));
        assertTrue(addScoreIndexCommandA.equals(addScoreIndexCommandA));

        // same values -> return true
        assertTrue(addScoreStudentCommandA.equals(copyAddScoreStudentCommandA));
        assertTrue(addScoreIndexCommandA.equals(copyAddScoreIndexCommandA));

        // different types -> return false
        assertFalse(addScoreStudentCommandA.equals(addScoreIndexCommandA));

        // different values -> return different
        assertFalse(addScoreStudentCommandA.equals(addScoreStudentCommandB));
        assertFalse(addScoreIndexCommandA.equals(addScoreIndexCommandB));
    }
}
