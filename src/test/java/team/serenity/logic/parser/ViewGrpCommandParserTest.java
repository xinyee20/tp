package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;

class ViewGrpCommandParserTest {

    private final ViewGrpCommandParser parser = new ViewGrpCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGrpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyInputs_throwsParseException() {
        assertParseFailure(parser, GroupName.MESSAGE_GROUP_NAME_MULTIPLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGrpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewGrpCommand() {
        ViewGrpCommand expected = new ViewGrpCommand(new GroupContainsKeywordPredicate("G04"));
        assertParseSuccess(parser, " grp/ g04", expected);
        assertParseSuccess(parser, " grp/g04", expected);
    }
}
