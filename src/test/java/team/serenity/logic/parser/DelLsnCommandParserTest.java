package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.lesson.DelLsnCommand;
import team.serenity.logic.parser.lesson.DelLsnCommandParser;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

class DelLsnCommandParserTest {

    @Test
    void parse_emptyInput_throwParseException() {
        DelLsnCommandParser parser = new DelLsnCommandParser();
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelLsnCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsDellsnCommand() {
        DelLsnCommand expected = new DelLsnCommand(new GroupName("G04"), new LessonName("1-1"));
        DelLsnCommandParser parser = new DelLsnCommandParser();
        assertParseSuccess(parser, " grp/ g04 lsn/1-1", expected);
        assertParseSuccess(parser, " grp/g04 lsn/ 1-1", expected);
    }

}
