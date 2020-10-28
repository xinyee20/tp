package team.serenity.logic.parser.question;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_QN_DESC;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.QN_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_QN_DESC_A;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.question.AddQnCommand;
import team.serenity.model.group.question.Description;

class AddQnCommandParserTest {

    private AddQnCommandParser parser = new AddQnCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Description expectedDescription = new Description(VALID_QN_DESC_A);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QN_DESC_GROUP_A,
                new AddQnCommand(expectedDescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddQnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_QN_DESC_A, expectedMessage); // missing prefix
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_QN_DESC, Description.MESSAGE_CONSTRAINTS);
    }

}
