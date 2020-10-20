package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.commands.CommandTestUtil;

public class AddGrpCommandParserTest {

    private AddGrpCommandParser parser = new AddGrpCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE);

        // missing grp prefix
        assertParseFailure(parser, CommandTestUtil.VALID_GRP_GROUP_B
            + CommandTestUtil.PATH_DESC_GROUP_B, expectedMessage);

        // missing path prefix
        assertParseFailure(parser, CommandTestUtil.PATH_DESC_GROUP_B
            + CommandTestUtil.VALID_PATH_GROUP_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // TODO: add invalid grp and path codes here
    }

}
