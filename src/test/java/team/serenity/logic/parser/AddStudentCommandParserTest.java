package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.student.AddStudentCommand;
import team.serenity.logic.parser.student.AddStudentCommandParser;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.student.StudentName;
import team.serenity.model.group.student.StudentNumber;


public class AddStudentCommandParserTest {
    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_missingArguments() {
        String missingGroup = PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U";
        String missingStudent = PREFIX_GRP + "G04" + " " + PREFIX_MATRIC + "A0123456U";
        String missingId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John";
        String doubleGroup = PREFIX_GRP + "G04 G05" + " " + PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U";
        String doubleId = PREFIX_GRP + "G04" + " " + PREFIX_NAME + "John" + " " + PREFIX_MATRIC + "A0123456U A0101010B";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, missingGroup, expectedMessage);
        assertParseFailure(parser, missingStudent, expectedMessage);
        assertParseFailure(parser, missingId, expectedMessage);
        assertParseFailure(parser, doubleGroup, expectedMessage);
        assertParseFailure(parser, doubleId, expectedMessage);
    }

    @Test
    public void parse_invalidStudentName() {
        String studentName = "Wayne Wayne Wayne Wayne Wayne Wayne Wayne Wayne Wayne Wayne";
        String studentId = "A0123456S";
        String groupName = "G04";
        String nameLengthExceeded = " " + PREFIX_GRP + groupName + " " + PREFIX_NAME
            + studentName + " " + PREFIX_MATRIC + studentId;
        assertParseFailure(parser, nameLengthExceeded, StudentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStudentNumber() {
        String studentName = "Wayne";
        String studentId = "A01234567S";
        String groupName = "G04";
        String nameLengthExceeded = " " + PREFIX_GRP + groupName + " " + PREFIX_NAME
            + studentName + " " + PREFIX_MATRIC + studentId;
        assertParseFailure(parser, nameLengthExceeded, StudentNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_successfulArguments() {
        String studentName = "John";
        String studentId = "A0123456S";
        String groupName = "G04";
        String args = " " + PREFIX_GRP + groupName + " " + PREFIX_NAME
            + studentName + " " + PREFIX_MATRIC + studentId;
        AddStudentCommand result = new AddStudentCommand(new StudentName(studentName),
            new StudentNumber(studentId),
            new GroupContainsKeywordPredicate(groupName));
        assertParseSuccess(parser, args, result);
    }
}
