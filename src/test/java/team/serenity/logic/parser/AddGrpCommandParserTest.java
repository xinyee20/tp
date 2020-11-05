package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_FILE_PATH_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_FILE_NON_XLSX;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_EMPTY;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_INVALID_CHARS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_MULTIPLE;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_ALPHABET;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_DIGITS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_TOO_LESS_CHARS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_TOO_MANY_CHARS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_WITHOUT_NAME;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_WITHOUT_PATH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_PATH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_PATH_EMPTY;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_PATH_NON_XLSX;
import static team.serenity.logic.commands.CommandTestUtil.PATH_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_A;
import static team.serenity.logic.parser.CliSyntax.PREFIX_PATH;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;

public class AddGrpCommandParserTest {

    private AddGrpCommandParser parser = new AddGrpCommandParser();

    @Test
    public void parse_validGroupNameAndFilePath_returnsAddGrpCommand() throws ParseException {
        AddGrpCommand expectedCommand = new AddGrpCommand(new Group(VALID_GROUP_NAME_A, VALID_PATH_A));
        String userInput = GRP_DESC_GROUP_A + PATH_DESC_GROUP_A;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingGroupName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_GROUP_WITHOUT_NAME;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingFilePath_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE);
        String userInput = PREAMBLE_WHITESPACE + INVALID_GROUP_WITHOUT_PATH;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameTooManyChars_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_TOO_MANY_CHARS + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameTooLessChars_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_TOO_LESS_CHARS + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameInvalidChars_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_INVALID_CHARS + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameNonAlphabet_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_NON_ALPHABET + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameNonDigits_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_NON_DIGITS + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidFilePathDash_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        String userInput = INVALID_GROUP_NAME_DASH + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameEmpty_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_GROUP_NAME_EMPTY;
        String userInput = INVALID_GROUP_NAME_EMPTY + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGroupNameMultiple_throwsParseException() {
        String expectedMessage = GroupName.MESSAGE_GROUP_NAME_MULTIPLE;
        String userInput = INVALID_GROUP_NAME_MULTIPLE + " " + PREFIX_PATH + VALID_PATH_A;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidFilePath_throwsParseException() {
        String expectedMessage = MESSAGE_INVALID_FILE_PATH;
        String userInput = GRP_DESC_GROUP_A + " " + PREFIX_PATH + INVALID_PATH;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidFilePathNonXlsx_throwsParseException() {
        String expectedMessage = MESSAGE_INVALID_FILE_NON_XLSX;
        String userInput = GRP_DESC_GROUP_A + " " + PREFIX_PATH + INVALID_PATH_NON_XLSX;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidFilePathEmpty_throwsParseException() {
        String expectedMessage = MESSAGE_FILE_PATH_EMPTY;
        String userInput = GRP_DESC_GROUP_A + " " + PREFIX_PATH + INVALID_PATH_EMPTY;

        assertParseFailure(parser, userInput, expectedMessage);
    }

}
