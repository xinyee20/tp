package team.serenity.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static team.serenity.logic.commands.CommandTestUtil.ADD_SCORE_DESC;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G01;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G04;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_1;
import static team.serenity.logic.commands.CommandTestUtil.PATH_DESC_GROUP_G04;
import static team.serenity.logic.commands.CommandTestUtil.QN_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.STUDENT_DESC_AARON;
import static team.serenity.logic.commands.CommandTestUtil.VALID_INDEX;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.commands.DelGrpCommand;
import team.serenity.logic.commands.ExitCommand;
import team.serenity.logic.commands.HelpCommand;
import team.serenity.logic.commands.ViewFlagCommand;
import team.serenity.logic.commands.lesson.AddLsnCommand;
import team.serenity.logic.commands.lesson.DelLsnCommand;
import team.serenity.logic.commands.question.AddQnCommand;
import team.serenity.logic.commands.question.DelQnCommand;
import team.serenity.logic.commands.question.ViewQnCommand;
import team.serenity.logic.commands.student.AddStudentCommand;
import team.serenity.logic.commands.student.DelStudentCommand;
import team.serenity.logic.commands.studentinfo.AddScoreCommand;
import team.serenity.logic.parser.exceptions.ParseException;

public class SerenityParserTest {

    private final SerenityParser parser = new SerenityParser();

    @Test
    public void parseCommand_addGrp() throws Exception {
        String userInput = AddGrpCommand.COMMAND_WORD + GRP_DESC_GROUP_G04 + PATH_DESC_GROUP_G04;
        assertTrue(parser.parseCommand(userInput) instanceof AddGrpCommand);
    }

    @Test
    public void parseCommand_addLsn() throws Exception {
        String userInput = AddLsnCommand.COMMAND_WORD + GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1;
        assertTrue(parser.parseCommand(userInput) instanceof AddLsnCommand);
    }

    @Test
    public void parseCommand_addStudent() throws Exception {
        String userInput = AddStudentCommand.COMMAND_WORD + GRP_DESC_GROUP_G01 + STUDENT_DESC_AARON;
        assertTrue(parser.parseCommand(userInput) instanceof AddStudentCommand);
    }

    @Test
    public void parseCommand_addScore() throws Exception {
        String userInput = AddScoreCommand.COMMAND_WORD + " " + VALID_INDEX + ADD_SCORE_DESC;
        assertTrue(parser.parseCommand(userInput) instanceof AddScoreCommand);
    }

    @Test
    public void parseCommand_addQn() throws Exception {
        String userInput = AddQnCommand.COMMAND_WORD + QN_DESC_GROUP_A;
        assertTrue(parser.parseCommand(userInput) instanceof AddQnCommand);
    }

    @Test
    public void parseCommand_delGrp() throws Exception {
        String userInput = DelGrpCommand.COMMAND_WORD + GRP_DESC_GROUP_G01;
        assertTrue(parser.parseCommand(userInput) instanceof DelGrpCommand);
    }

    @Test
    public void parseCommand_delLsn() throws Exception {
        String userInput = DelLsnCommand.COMMAND_WORD + GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1;
        assertTrue(parser.parseCommand(userInput) instanceof DelLsnCommand);
    }

    @Test
    public void parseCommand_delStudent() throws Exception {
        String userInput = DelStudentCommand.COMMAND_WORD + GRP_DESC_GROUP_G01 + STUDENT_DESC_AARON;
        assertTrue(parser.parseCommand(userInput) instanceof DelStudentCommand);
    }

    @Test
    public void parseCommand_delQn() throws Exception {
        String userInput = DelQnCommand.COMMAND_WORD + " " + VALID_INDEX;
        assertTrue(parser.parseCommand(userInput) instanceof DelQnCommand);
    }

    @Test
    public void parseCommand_viewFlag() throws Exception {
        assertTrue(parser.parseCommand(ViewFlagCommand.COMMAND_WORD) instanceof ViewFlagCommand);
        assertTrue(parser.parseCommand(ViewFlagCommand.COMMAND_WORD + " 3") instanceof ViewFlagCommand);
    }

    @Test
    public void parseCommand_viewQn() throws Exception {
        assertTrue(parser.parseCommand(ViewQnCommand.COMMAND_WORD) instanceof ViewQnCommand);
        assertTrue(parser.parseCommand(ViewQnCommand.COMMAND_WORD + " 3") instanceof ViewQnCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
