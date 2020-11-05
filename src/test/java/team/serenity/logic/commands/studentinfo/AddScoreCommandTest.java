package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_SCORE_NOT_WITHIN_RANGE;
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

class AddScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Index) null, 1));
        assertThrows(NullPointerException.class, () -> new AddScoreCommand((Student) null, 1));
    }

    @Test
    public void execute_addScoreOutOfRange_failure() throws CommandException {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student toAddScore = new Student("Aaron Tan", "A0123456U");
        int scoreOutOfRange = 6;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, scoreOutOfRange);

        assertThrows(CommandException.class, MESSAGE_SCORE_NOT_WITHIN_RANGE, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_addScore_success() throws CommandException {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student toAddScore = new Student("Aaron Tan", "A0123456U");
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
        Student toAddScore = new Student("Aaron Tan", "A0123456U");
        int validAddScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validAddScore);

        assertThrows(CommandException.class, String.format(
                AddScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toAddScore), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_wrongName_throwsCommandException() {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student wrongNameOne = new Student("Aaron", "A0123456U");
        Student wrongNameTwo = new Student("Betty Tan", "A0123456U");
        int validScore = 1;
        AddScoreCommand addScoreCommandOne = new AddScoreCommand(wrongNameOne, validScore);
        AddScoreCommand addScoreCommandTwo = new AddScoreCommand(wrongNameTwo, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> addScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> addScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student wrongNumber = new Student("Aaron Tan", "A0000000U");
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongNumber, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toAddScore = new Student("Aaron Tan", "A0123456U");
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toAddScore = new Student("Aaron Tan", "A0123456U");
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(toAddScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexWithScore modelStub = new ModelStubWithIndexWithScore();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSetScore = new Student("Aaron Tan", "A0123456U");
        int validAddScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore + validAddScore;

        CommandResult commandResult = new AddScoreCommand(validIndex, validAddScore).execute(modelStub);
        assertEquals(String.format(AddScoreCommand.MESSAGE_SUCCESS, toSetScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexWithScore modelStub = new ModelStubWithIndexWithScore();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        int validScore = 1;
        AddScoreCommand addScoreCommand = new AddScoreCommand(wrongIndex, validScore);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> addScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        int validAddScore = 1;
        AddScoreCommand addScoreStudentCommandA = new AddScoreCommand(AARON, validAddScore);
        AddScoreCommand copyAddScoreStudentCommandA = new AddScoreCommand(AARON, validAddScore);
        AddScoreCommand addScoreStudentCommandB = new AddScoreCommand(JOHN, validAddScore);
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
