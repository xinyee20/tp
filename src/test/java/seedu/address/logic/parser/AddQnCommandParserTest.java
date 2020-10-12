package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.QN_DESC_GROUP_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QN_A;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddQnCommand;
import seedu.address.model.group.Question;

class AddQnCommandParserTest {

    private AddQnCommandParser parser = new AddQnCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Question expectedQuestion = new Question(VALID_QN_A);
        assertParseSuccess(parser, QN_DESC_GROUP_A, new AddQnCommand(expectedQuestion));
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
