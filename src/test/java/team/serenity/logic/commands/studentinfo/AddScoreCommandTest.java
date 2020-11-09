package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_ADDED_SCORE_NOT_WITHIN_RANGE;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;
import static team.serenity.testutil.TypicalStudentInfo.ORIGINAL_SCORE;
import static team.serenity.testutil.TypicalStudentInfo.SCORE_OUT_OF_RANGE;
import static team.serenity.testutil.TypicalStudentInfo.VALID_ADD_SCORE;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.StudentBuilder;

class AddScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Index) null, VALID_ADD_SCORE));
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Student) null, VALID_ADD_SCORE));
    }

    @Test
    public void execute_addScoreOutOfRange_throwsIllegalArgumentException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toAddScore = new StudentBuilder().build();
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, SCORE_OUT_OF_RANGE);
        String expectedMessage = String.format(MESSAGE_ADDED_SCORE_NOT_WITHIN_RANGE, SCORE_OUT_OF_RANGE, ORIGINAL_SCORE
                + SCORE_OUT_OF_RANGE);
        assertThrows(CommandException.class, expectedMessage, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_addScore_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toAddScore = new StudentBuilder().build();
        int expectedScore = ORIGINAL_SCORE + VALID_ADD_SCORE;

        CommandResult commandResult = new AddScoreCommand(toAddScore, VALID_ADD_SCORE).execute(modelStub);
        assertEquals(String.format(AddScoreCommand.MESSAGE_SUCCESS, toAddScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toAddScore = new StudentBuilder().build();
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, VALID_ADD_SCORE);

        assertThrows(CommandException.class, String.format(
                AddScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toAddScore), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        AddScoreCommand addScoreCommandOne = new AddScoreCommand(wrongNameOne, VALID_ADD_SCORE);
        AddScoreCommand addScoreCommandTwo = new AddScoreCommand(wrongNameTwo, VALID_ADD_SCORE);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> addScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> addScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongNumber, VALID_ADD_SCORE);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() throws CommandException {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toAddScore = new StudentBuilder().build();
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, VALID_ADD_SCORE);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() throws CommandException {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toAddScore = new StudentBuilder().build();
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, VALID_ADD_SCORE);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSetScore = new StudentBuilder().build();
        int expectedScore = ORIGINAL_SCORE + VALID_ADD_SCORE;

        CommandResult commandResult = new AddScoreCommand(validIndex, VALID_ADD_SCORE).execute(modelStub);
        assertEquals(String.format(AddScoreCommand.MESSAGE_SUCCESS, toSetScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongIndex, VALID_ADD_SCORE);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() throws CommandException {
        AddScoreCommand addScoreStudentCommandA = new AddScoreCommand(AARON, VALID_ADD_SCORE);
        AddScoreCommand copyAddScoreStudentCommandA = new AddScoreCommand(AARON, VALID_ADD_SCORE);
        AddScoreCommand addScoreStudentCommandB = new AddScoreCommand(BENJAMIN, VALID_ADD_SCORE);
        AddScoreCommand addScoreIndexCommandA = new AddScoreCommand(INDEX_FIRST, VALID_ADD_SCORE);
        AddScoreCommand copyAddScoreIndexCommandA = new AddScoreCommand(INDEX_FIRST, VALID_ADD_SCORE);
        AddScoreCommand addScoreIndexCommandB = new AddScoreCommand(INDEX_SECOND, VALID_ADD_SCORE);

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
