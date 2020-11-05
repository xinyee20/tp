package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.*;
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

class SubScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Index) null, 1));
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Student) null, 1));
    }

    @Test
    public void execute_subScoreOutOfRange_failure() throws CommandException {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int subScoreOutOfRange = 4;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, subScoreOutOfRange);

        assertThrows(CommandException.class, MESSAGE_SCORE_NOT_WITHIN_RANGE, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_subScore_success() throws CommandException {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int validSubScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore - validSubScore;

        CommandResult commandResult = new SubScoreCommand(toSubScore, validSubScore).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int validSubScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validSubScore);

        assertThrows(CommandException.class, String.format(SubScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toSubScore), () -> subScoreCommand.execute(modelStub));;
    }

    @Test
    public void execute_wrongName_throwsCommandException() {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student wrongNameOne = new Student("Aaron", "A0123456U");
        Student wrongNameTwo = new Student("Betty Tan", "A0123456U");
        int validScore = 1;
        SubScoreCommand subScoreCommandOne = new SubScoreCommand(wrongNameOne, validScore);
        SubScoreCommand subScoreCommandTwo = new SubScoreCommand(wrongNameTwo, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> subScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> subScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsWithScores modelStub = new ModelStubWithStudentsWithScores();
        Student wrongNumber = new Student("Aaron Tan", "A0000000U");
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongNumber, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexWithScore modelStub = new ModelStubWithIndexWithScore();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSubScore = new Student("Aaron Tan", "A0123456U");
        int validSubScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore - validSubScore;

        CommandResult commandResult = new SubScoreCommand(validIndex, validSubScore).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexWithScore modelStub = new ModelStubWithIndexWithScore();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongIndex, validScore);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        int validSubScore = 1;
        SubScoreCommand subScoreStudentCommandA = new SubScoreCommand(AARON, validSubScore);
        SubScoreCommand copySubScoreStudentCommandA = new SubScoreCommand(AARON, validSubScore);
        SubScoreCommand subScoreStudentCommandB = new SubScoreCommand(JOHN, validSubScore);
        SubScoreCommand subScoreIndexCommandA = new SubScoreCommand(INDEX_FIRST, validSubScore);
        SubScoreCommand copySubScoreIndexCommandA = new SubScoreCommand(INDEX_FIRST, validSubScore);
        SubScoreCommand subScoreIndexCommandB = new SubScoreCommand(INDEX_SECOND, validSubScore);

        // same object -> returns true
        assertTrue(subScoreStudentCommandA.equals(subScoreStudentCommandA));
        assertTrue(subScoreIndexCommandA.equals(subScoreIndexCommandA));

        // same values -> return true
        assertTrue(subScoreStudentCommandA.equals(copySubScoreStudentCommandA));
        assertTrue(subScoreIndexCommandA.equals(copySubScoreIndexCommandA));

        // different types -> return false
        assertFalse(subScoreStudentCommandA.equals(subScoreIndexCommandA));

        // different values -> return different
        assertFalse(subScoreStudentCommandA.equals(subScoreStudentCommandB));
        assertFalse(subScoreIndexCommandA.equals(subScoreIndexCommandB));
    }

}