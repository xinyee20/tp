package team.serenity.logic.parser.question;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.question.FindQnCommand;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;

class FindQnCommandParserTest {

    private final FindQnCommandParser parser = new FindQnCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(this.parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindQnCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindQnCommand() {
        // no leading and trailing whitespaces
        FindQnCommand expectedFindQnCommand =
                new FindQnCommand(new QuestionContainsKeywordPredicate(Arrays.asList("Due", "Deadline")));
        assertParseSuccess(this.parser, "Due Deadline", expectedFindQnCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(this.parser, " \n Due \n \t Deadline  \t", expectedFindQnCommand);
    }

}

