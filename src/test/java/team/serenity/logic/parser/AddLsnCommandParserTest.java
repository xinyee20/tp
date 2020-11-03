package team.serenity.logic.parser;

import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_TEN;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_ONE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_ONE;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.lesson.AddLsnCommand;
import team.serenity.logic.parser.lesson.AddLsnCommandParser;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

public class AddLsnCommandParserTest {
    private AddLsnCommandParser parser = new AddLsnCommandParser();

    @Test
    public void parse_invalidGroupName() {
        assertParseFailure(parser, GRP_DESC_GROUP_A + INVALID_LESSON_NAME_TEN, LessonName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_GROUP_NAME_DASH + LESSON_DESC_LESSON_ONE, GroupName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_success() {
        AddLsnCommand expected = new AddLsnCommand(new LessonName(VALID_LESSON_NAME_ONE),
            new GroupContainsKeywordPredicate(VALID_GROUP_NAME_A));
        assertParseSuccess(parser, GRP_DESC_GROUP_A + LESSON_DESC_LESSON_ONE, expected);
    }

}
