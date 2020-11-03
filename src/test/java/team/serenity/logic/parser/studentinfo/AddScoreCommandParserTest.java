package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.ADD_SCORE_DESC;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_ADD_SCORE;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_STUDENT_WITHOUT_NAME;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_STUDENT_WITHOUT_NUMBER;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.STUDENT_DESC;
import static team.serenity.logic.commands.CommandTestUtil.VALID_INDEX;
import static team.serenity.logic.commands.CommandTestUtil.VALID_SCORE;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.studentinfo.AddScoreCommand;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.testutil.StudentBuilder;

class AddScoreCommandParserTest {

    private AddScoreCommandParser parser = new AddScoreCommandParser();

    @Test
    public void parse_missingStudentName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_STUDENT_WITHOUT_NAME + " " + ADD_SCORE_DESC;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingStudentId_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_STUDENT_WITHOUT_NUMBER + " " + ADD_SCORE_DESC;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingScore_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + STUDENT_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_throwsCommandException() {
        String expectedMessage = MESSAGE_INVALID_INDEX;
        String empty = "";

        assertParseFailure(parser, INVALID_INDEX + " " + ADD_SCORE_DESC, expectedMessage);
        assertParseFailure(parser, empty + ADD_SCORE_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidScore_throwsCommandException() {
        String expectedMessage = String.format(Participation.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_INDEX + " " + INVALID_ADD_SCORE, expectedMessage);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + STUDENT_DESC + " " + INVALID_ADD_SCORE, expectedMessage);
    }

    @Test
    public void parse_validStudentAndNumberParameter_returnsAddScoreCommand() {
        Student student = new StudentBuilder().build();
        int score = Integer.parseInt(VALID_SCORE);
        String userInput = PREAMBLE_WHITESPACE + STUDENT_DESC + " " + ADD_SCORE_DESC;

        assertParseSuccess(parser, userInput, new AddScoreCommand(student, score));
    }

    @Test
    public void parse_studentAndIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_INDEX + STUDENT_DESC + ADD_SCORE_DESC , expectedMessage);
    }

    @Test
    public void parse_validIndexParameter_returnsAddScoreCommand() {
        Index index = Index.fromOneBased(Integer.parseInt(VALID_INDEX));
        int score = Integer.parseInt(VALID_SCORE);

        assertParseSuccess(parser, VALID_INDEX + " " + ADD_SCORE_DESC, new AddScoreCommand(index, score));
    }

}
