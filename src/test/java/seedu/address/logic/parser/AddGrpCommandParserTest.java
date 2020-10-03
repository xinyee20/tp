package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PATH_DESC_GROUP_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRP_GROUP_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATH_GROUP_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGrpCommand;

public class AddGrpCommandParserTest {

    private AddGrpCommandParser parser = new AddGrpCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE);

        // missing grp prefix
        assertParseFailure(parser, VALID_GRP_GROUP_B + PATH_DESC_GROUP_B, expectedMessage);

        // missing path prefix
        assertParseFailure(parser, PATH_DESC_GROUP_B + VALID_PATH_GROUP_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // ToDo: add invalid grp and path codes here
    }
}
