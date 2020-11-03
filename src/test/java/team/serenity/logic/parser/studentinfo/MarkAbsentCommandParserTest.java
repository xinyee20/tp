package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_STUDENT_WITHOUT_NAME;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_STUDENT_WITHOUT_NUMBER;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.STUDENT_DESC;
import static team.serenity.logic.commands.CommandTestUtil.VALID_INDEX;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.studentinfo.MarkAbsentCommand;
import team.serenity.model.group.student.Student;
import team.serenity.testutil.StudentBuilder;

class MarkAbsentCommandParserTest {

    private MarkAbsentCommandParser parser = new MarkAbsentCommandParser();

    @Test
    public void parse_missingStudentName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_STUDENT_WITHOUT_NAME, expectedMessage);
    }

    @Test
    public void parse_missingStudentId_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_STUDENT_WITHOUT_NUMBER, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE);
        String empty = "";

        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_INDEX, expectedMessage);
        assertParseFailure(parser, empty, expectedMessage);
    }

    @Test
    public void parse_studentAndIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_INDEX + STUDENT_DESC, expectedMessage);
    }

    @Test
    public void parse_validStudentAndNumberParameter_returnsMarkAbsentCommand() {
        Student student = new StudentBuilder().build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_DESC, new MarkAbsentCommand(student));
    }

    @Test
    public void parse_validIndexParameter_returnsMarkAbsentCommand() {
        Index index = Index.fromOneBased(Integer.parseInt(VALID_INDEX));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INDEX, new MarkAbsentCommand(index));
    }

    @Test
    public void parse_validPreambleParameter_returnsMarkAbsentCommand() {
        String preamble = "all";

        assertParseSuccess(parser, preamble, new MarkAbsentCommand());
    }
}
