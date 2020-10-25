package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_ID;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.student.DelStudentCommand;
import team.serenity.logic.parser.student.DelStudentCommandParser;
import team.serenity.model.group.GroupContainsKeywordPredicate;

public class DelStudentCommandParserTest {
    private DelStudentCommandParser parser = new DelStudentCommandParser();
    @Test
    public void parse_missingArguments() {
        String missingGroup = PREFIX_NAME + "John" + " " + PREFIX_ID + "e1234567";
        String missingStudent = PREFIX_GRP + "G04" + " " + PREFIX_ID + "e1234567";
        String missingId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John";
        String doubleGroup = PREFIX_GRP + "G04 G05" + " " + PREFIX_NAME + "John" + " " + PREFIX_ID + "e1234567";
        String doubleId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John" + " " + PREFIX_ID + "e1234567 e7654321";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelStudentCommand.MESSAGE_USAGE);
        CommandParserTestUtil.assertParseFailure(parser, "", expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingGroup, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingStudent, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingId, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, doubleGroup, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, doubleId, expectedMessage);
    }

    @Test
    public void parse_successfulArguments() {
        String studentName = "John";
        String studentId = "e1234567";
        String groupName = "G04";
        String args = " " + PREFIX_GRP + groupName + " " + PREFIX_NAME
            + studentName + " " + PREFIX_ID + studentId;
        DelStudentCommand result = new DelStudentCommand(studentName, studentId,
            new GroupContainsKeywordPredicate(groupName));
        assertParseSuccess(parser, args, result);
    }
}
