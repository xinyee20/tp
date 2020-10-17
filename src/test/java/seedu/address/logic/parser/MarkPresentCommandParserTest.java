package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;

class MarkPresentCommandParserTest {

    private MarkPresentCommandParser parser = new MarkPresentCommandParser();

    @Test
    public void execute_missingStudentName_throwsParseException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

    @Test
    public void execute_invalidParameter_throwsCommandException() {

    }

    @Test
    public void parse_validArgs_returnsMarkPresentCommand() {

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
