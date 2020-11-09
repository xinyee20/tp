package team.serenity.logic.parser;

import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G04;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_TEN;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_1;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G04;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_1;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.lesson.AddLsnCommand;
import team.serenity.logic.parser.lesson.AddLsnCommandParser;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;

public class AddLsnCommandParserTest {
    private AddLsnCommandParser parser = new AddLsnCommandParser();

    @Test
    public void parse_invalidGroupName() {
        assertParseFailure(parser, GRP_DESC_GROUP_G04 + INVALID_LESSON_NAME_TEN, LessonName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_GROUP_NAME_DASH + LESSON_DESC_LESSON_1_1, GroupName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_success() {
        AddLsnCommand expected = new AddLsnCommand(new LessonName(VALID_LESSON_NAME_1_1),
            new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G04),
            new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G04),
                new LessonName(VALID_LESSON_NAME_1_1)));
        assertParseSuccess(parser, GRP_DESC_GROUP_G04 + LESSON_DESC_LESSON_1_1, expected);
    }

}
