package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.commons.core.Messages.MESSAGE_SUBTRACTED_SCORE_NOT_WITHIN_RANGE;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;
import static team.serenity.testutil.TypicalStudentInfo.ORIGINAL_SCORE;
import static team.serenity.testutil.TypicalStudentInfo.SCORE_OUT_OF_RANGE;
import static team.serenity.testutil.TypicalStudentInfo.SUB_TILL_SCORE_OUT_OF_RANGE;
import static team.serenity.testutil.TypicalStudentInfo.VALID_SUB_SCORE;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.StudentBuilder;

class SubScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Index) null, VALID_SUB_SCORE));
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Student) null, VALID_SUB_SCORE));
    }

    @Test
    public void execute_subScoreOutOfRange_throwCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSubScore = new StudentBuilder().build();
        SubScoreCommand subScoreCommandOne = new SubScoreCommand(toSubScore, SUB_TILL_SCORE_OUT_OF_RANGE);
        SubScoreCommand subScoreCommandTwo = new SubScoreCommand(toSubScore, SCORE_OUT_OF_RANGE);
        String expectedMessageOne = String.format(MESSAGE_SUBTRACTED_SCORE_NOT_WITHIN_RANGE,
                SUB_TILL_SCORE_OUT_OF_RANGE,
                ORIGINAL_SCORE - SUB_TILL_SCORE_OUT_OF_RANGE);
        String expectedMessageTwo = String.format(MESSAGE_SUBTRACTED_SCORE_NOT_WITHIN_RANGE,
                SCORE_OUT_OF_RANGE,
                ORIGINAL_SCORE - SCORE_OUT_OF_RANGE);
        assertThrows(CommandException.class, expectedMessageOne, () -> subScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class, expectedMessageTwo, () -> subScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_subScore_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSubScore = new StudentBuilder().build();
        int expectedScoreOne = ORIGINAL_SCORE - VALID_SUB_SCORE;
        int expectedScoreTwo = ORIGINAL_SCORE - ORIGINAL_SCORE;

        CommandResult commandResultOne = new SubScoreCommand(toSubScore, VALID_SUB_SCORE).execute(modelStub);
        CommandResult commandResultTwo = new SubScoreCommand(toSubScore, ORIGINAL_SCORE).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScoreOne),
                commandResultOne.getFeedbackToUser());
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScoreTwo),
                commandResultTwo.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toSubScore = new StudentBuilder().build();
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, VALID_SUB_SCORE);

        assertThrows(CommandException.class, String.format(
                SubScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toSubScore), () -> subScoreCommand.execute(modelStub));;
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        SubScoreCommand subScoreCommandOne = new SubScoreCommand(wrongNameOne, VALID_SUB_SCORE);
        SubScoreCommand subScoreCommandTwo = new SubScoreCommand(wrongNameTwo, VALID_SUB_SCORE);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> subScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> subScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongNumber, VALID_SUB_SCORE);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() throws CommandException {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toSubScore = new StudentBuilder().build();
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, VALID_SUB_SCORE);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() throws CommandException {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toSubScore = new StudentBuilder().build();
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, VALID_SUB_SCORE);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSubScore = new StudentBuilder().build();
        int expectedScore = ORIGINAL_SCORE - VALID_SUB_SCORE;

        CommandResult commandResult = new SubScoreCommand(validIndex, VALID_SUB_SCORE).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongIndex, VALID_SUB_SCORE);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() throws CommandException {
        SubScoreCommand subScoreStudentCommandA = new SubScoreCommand(AARON, VALID_SUB_SCORE);
        SubScoreCommand copySubScoreStudentCommandA = new SubScoreCommand(AARON, VALID_SUB_SCORE);
        SubScoreCommand subScoreStudentCommandB = new SubScoreCommand(BENJAMIN, VALID_SUB_SCORE);
        SubScoreCommand subScoreIndexCommandA = new SubScoreCommand(INDEX_FIRST, VALID_SUB_SCORE);
        SubScoreCommand copySubScoreIndexCommandA = new SubScoreCommand(INDEX_FIRST, VALID_SUB_SCORE);
        SubScoreCommand subScoreIndexCommandB = new SubScoreCommand(INDEX_SECOND, VALID_SUB_SCORE);

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
