package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_DIGITS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_PATH;
import static team.serenity.logic.commands.CommandTestUtil.PATH_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.PATH_DESC_GROUP_B;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_B;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;

public class AddGrpCommandParserTest {

    private AddGrpCommandParser parser = new AddGrpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(VALID_PATH_A);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Group expectedGroup = new Group(new GroupName(VALID_GROUP_NAME_A), new XlsxUtil(VALID_PATH_A, workbook));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GRP_DESC_GROUP_A
            + PREAMBLE_WHITESPACE + PATH_DESC_GROUP_A + PREAMBLE_WHITESPACE,
            new AddGrpCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGrpCommand.MESSAGE_USAGE);

        // missing grp prefix
        assertParseFailure(parser, VALID_GROUP_NAME_A + PATH_DESC_GROUP_B, expectedMessage);

        // missing path prefix
        assertParseFailure(parser, GRP_DESC_GROUP_A + VALID_PATH_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid group names
        assertParseFailure(parser, INVALID_GROUP_NAME_NON_DIGITS + PATH_DESC_GROUP_A,
            GroupName.MESSAGE_CONSTRAINTS);

        // invalid file path
        assertParseFailure(parser, GRP_DESC_GROUP_A + INVALID_PATH, MESSAGE_INVALID_FILE_PATH);
    }

}
