package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DelStudentCommand;
import seedu.address.model.group.GrpContainsKeywordPredicate;

public class DelStudentCommandParserTest {
    private DelStudentCommandParser parser = new DelStudentCommandParser();
    @Test
    public void parse_missingArguments() {
        String missingGroup = PREFIX_STUDENT + "John" + " " + PREFIX_ID + "e1234567";
        String missingStudent = PREFIX_GRP + "G04" + " " + PREFIX_ID + "e1234567";
        String missingId = PREFIX_GRP + "G04" + " " + PREFIX_STUDENT + "John";
        String doubleGroup = PREFIX_GRP + "G04 G05" + " " + PREFIX_STUDENT + "John" + " " + PREFIX_ID + "e1234567";
        String doubleId = PREFIX_GRP + "G04" + " " + PREFIX_STUDENT + "John" + " " + PREFIX_ID + "e1234567 e7654321";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelStudentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, missingGroup, expectedMessage);
        assertParseFailure(parser, missingStudent, expectedMessage);
        assertParseFailure(parser, missingId, expectedMessage);
        assertParseFailure(parser, doubleGroup, expectedMessage);
        assertParseFailure(parser, doubleId, expectedMessage);
    }

    @Test
    public void parse_successfulArguments() {
        String studentName = "John";
        String studentId = "e1234567";
        String groupName = "G04";
        String args = " " + PREFIX_GRP + groupName + " " + PREFIX_STUDENT
            + studentName + " " + PREFIX_ID + studentId;
        DelStudentCommand result = new DelStudentCommand(studentName, studentId,
            new GrpContainsKeywordPredicate(groupName));
        assertParseSuccess(parser, args, result);
    }
}
