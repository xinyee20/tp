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

class SubScoreCommandTest {
    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Index) null, 1));
        assertThrows(NullPointerException.class, () -> new SubScoreCommand((Student) null, 1));
    }

    @Test
    public void execute_subScoreOutOfRange_throwCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSubScore = new StudentBuilder().build();
        int subScoreOutOfRange = 4;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, subScoreOutOfRange);

        assertThrows(CommandException.class, MESSAGE_SCORE_NOT_WITHIN_RANGE, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_subScore_success() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student toSubScore = new StudentBuilder().build();
        int validSubScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore - validSubScore;

        CommandResult commandResult = new SubScoreCommand(toSubScore, validSubScore).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_studentAbsent_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toSubScore = new StudentBuilder().build();
        int validSubScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validSubScore);

        assertThrows(CommandException.class, String.format(
                SubScoreCommand.MESSAGE_STUDENT_NOT_PRESENT, toSubScore), () -> subScoreCommand.execute(modelStub));;
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNameOne = new StudentBuilder().withName("Aaron").withId("A0123456U").build();
        Student wrongNameTwo = new StudentBuilder().withName("Betty Tan").withId("A0123456U").build();
        int validScore = 1;
        SubScoreCommand subScoreCommandOne = new SubScoreCommand(wrongNameOne, validScore);
        SubScoreCommand subScoreCommandTwo = new SubScoreCommand(wrongNameTwo, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameOne), () -> subScoreCommandOne.execute(modelStub));
        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNameTwo), () -> subScoreCommandTwo.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() throws CommandException {
        ModelStubWithStudentsPresent modelStub = new ModelStubWithStudentsPresent();
        Student wrongNumber = new StudentBuilder().withName("Aaron Tan").withId("A0000000U").build();
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongNumber, validScore);

        assertThrows(CommandException.class,
                String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInGroup_throwsCommandException() throws CommandException {
        ModelStubWithNoGroup modelStub = new ModelStubWithNoGroup();
        Student toSubScore = new StudentBuilder().build();
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_GROUP, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_notInLesson_throwsCommandException() throws CommandException {
        ModelStubWithNoLesson modelStub = new ModelStubWithNoLesson();
        Student toSubScore = new StudentBuilder().build();
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(toSubScore, validScore);

        assertThrows(CommandException.class, MESSAGE_NOT_VIEWING_A_LESSON, () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toSubScore = new StudentBuilder().build();
        int validSubScore = 1;
        int originalScore = 3;
        int expectedScore = originalScore - validSubScore;

        CommandResult commandResult = new SubScoreCommand(validIndex, validSubScore).execute(modelStub);
        assertEquals(String.format(SubScoreCommand.MESSAGE_SUCCESS, toSubScore, expectedScore),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() throws CommandException {
        ModelStubWithIndexPresent modelStub = new ModelStubWithIndexPresent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        int validScore = 1;
        SubScoreCommand subScoreCommand = new SubScoreCommand(wrongIndex, validScore);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX,
                wrongIndex.getOneBased()), () -> subScoreCommand.execute(modelStub));
    }

    @Test
    public void equals() throws CommandException {
        int validSubScore = 1;
        SubScoreCommand subScoreStudentCommandA = new SubScoreCommand(AARON, validSubScore);
        SubScoreCommand copySubScoreStudentCommandA = new SubScoreCommand(AARON, validSubScore);
        SubScoreCommand subScoreStudentCommandB = new SubScoreCommand(BENJAMIN, validSubScore);
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
