package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_EMPTY;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_INVALID_CHARS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_WITHOUT_NAME;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_A;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.DelGrpCommand;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;

public class DelGrpCommandParserTest {

    private DelGrpCommandParser parser = new DelGrpCommandParser();

    @Test
    public void parse_validGroupName_returnsDelGrpCommand() {
        DelGrpCommand expectedCommand = new DelGrpCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_A));
        String userInput = GRP_DESC_GROUP_A;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingGroupName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelGrpCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_GROUP_WITHOUT_NAME;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameInvalidChars_throwsParseException() {
        String expectedMessage = MESSAGE_GROUP_EMPTY;
        String userInput = INVALID_GROUP_NAME_INVALID_CHARS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameEmpty_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_GROUP_NAME_EMPTY;
        String userInput = INVALID_GROUP_NAME_EMPTY;

        assertParseFailure(parser, userInput, expectedMessage);
    }

}