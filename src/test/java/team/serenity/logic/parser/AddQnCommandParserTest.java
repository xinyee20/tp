package team.serenity.logic.parser;

import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.AddQnCommand;
import team.serenity.logic.commands.CommandTestUtil;
import team.serenity.model.group.Question;

class AddQnCommandParserTest {

    private AddQnCommandParser parser = new AddQnCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Question expectedQuestion = new Question(CommandTestUtil.VALID_QN_A);
        assertParseSuccess(parser, CommandTestUtil.QN_DESC_GROUP_A, new AddQnCommand(expectedQuestion));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddQnCommand.MESSAGE_USAGE);
        // assertParseFailure(parser, VALID_QN_A, expectedMessage); // missing prefix
    }

    @Test
    public void parse_invalidValue_failure() {
        // assertParseFailure(parser, INVALID_QN_DESC, Question.MESSAGE_CONSTRAINTS);
    }

}
