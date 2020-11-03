package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.STUDENT_DESC;
import static team.serenity.logic.commands.CommandTestUtil.VALID_INDEX;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.student.DelStudentCommand;
import team.serenity.logic.parser.student.DelStudentCommandParser;
import team.serenity.model.group.GroupContainsKeywordPredicate;

public class DelStudentCommandParserTest {
    private DelStudentCommandParser parser = new DelStudentCommandParser();
    @Test
    public void parse_missingArguments() {
        String missingGroup = PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U";
        String missingStudent = PREFIX_GRP + "G04" + " " + PREFIX_MATRIC + "A0123456U";
        String missingId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John";
        String doubleGroup = PREFIX_GRP + "G04 G05" + " " + PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U";
        String doubleId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U A0101010B";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelStudentCommand.MESSAGE_USAGE);
        CommandParserTestUtil.assertParseFailure(parser, "", expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingGroup, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingStudent, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, missingId, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, doubleGroup, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, doubleId, expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, INVALID_INDEX, expectedMessage);
    }

    @Test
    public void parse_studentAndIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelStudentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_INDEX + STUDENT_DESC + GRP_DESC_GROUP_A , expectedMessage);
    }

    @Test
    public void parse_successfulArguments() {
        String studentName = "John";
        String studentId = "A0123456U";
        String groupName = "G04";
        String args = " " + PREFIX_GRP + groupName + " " + PREFIX_NAME
            + studentName + " " + PREFIX_MATRIC + studentId;
        DelStudentCommand result = new DelStudentCommand(studentName, studentId,
            new GroupContainsKeywordPredicate(groupName));
        assertParseSuccess(parser, args, result);
        String indexArgs = " " + VALID_INDEX + " " + PREFIX_GRP + groupName;
        Index index = Index.fromOneBased(Integer.parseInt(VALID_INDEX));
        DelStudentCommand indexTest = new DelStudentCommand(index, new GroupContainsKeywordPredicate(groupName));
        assertParseSuccess(parser, indexArgs, indexTest);
    }
}
