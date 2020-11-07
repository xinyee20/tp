package team.serenity.logic.parser.studentinfo;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_EMPTY;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_INVALID_CHARS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_MULTIPLE;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_ALPHABET;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_DIGITS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_WITHOUT_NAME;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_A;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.studentinfo.ExportScoreCommand;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;

public class ExportScoreCommandParserTest {

    private ExportScoreCommandParser parser = new ExportScoreCommandParser();

    @Test
    public void parse_validGroupName_returnsExportScoreCommand() {
        ExportScoreCommand expectedCommand =
            new ExportScoreCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_A));
        assertParseSuccess(parser, GRP_DESC_GROUP_A, expectedCommand);
    }

    @Test
    public void parse_missingGroupName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportScoreCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_GROUP_WITHOUT_NAME;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameInvalidChars_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_NAME_INVALID_CHARS, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameNonAlphabet_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_NAME_NON_ALPHABET, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameNonDigits_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_NAME_NON_DIGITS, expectedMessage);
    }

    @Test
    public void parse_invalidFilePathDash_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_NAME_DASH, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameEmpty_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_GROUP_NAME_EMPTY;
        assertParseFailure(parser, INVALID_GROUP_NAME_EMPTY, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameMultiple_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_GROUP_NAME_MULTIPLE;
        assertParseFailure(parser, INVALID_GROUP_NAME_MULTIPLE, expectedMessage);
    }

}
