package team.serenity.logic.parser.question;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.question.DelQnCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DelQnCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DelQnCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the SerenityParserUtilTest.
 */
class DelQnCommandParserTest {

    private DelQnCommandParser parser = new DelQnCommandParser();

    @Test
    public void parse_validArgs_returnsDelQnCommand() {
        assertParseSuccess(parser, "1", new DelQnCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelQnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
    }
}
