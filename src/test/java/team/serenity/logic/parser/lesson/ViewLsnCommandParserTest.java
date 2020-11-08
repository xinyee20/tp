package team.serenity.logic.parser.lesson;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G01;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G02;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_TEN;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_1;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_2;
import static team.serenity.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G01;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G02;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_1;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_2;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.lesson.ViewLsnCommand;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;

class ViewLsnCommandParserTest {

    private final ViewLsnCommandParser parser = new ViewLsnCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewLsnCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingInputs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewLsnCommand.MESSAGE_USAGE);

        // no group specified
        assertParseFailure(parser, LESSON_DESC_LESSON_1_1, expectedMessage);

        // no lesson specified
        assertParseFailure(parser, GRP_DESC_GROUP_G01, expectedMessage);
    }

    @Test
    public void parse_invalidInputs_throwsParseException() {
        String invalidGroup = GroupName.MESSAGE_CONSTRAINTS;
        String invalidLesson = LessonName.MESSAGE_CONSTRAINTS;
        String missingPrefix = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewLsnCommand.MESSAGE_USAGE);

        // invalid group
        assertParseFailure(parser, INVALID_GROUP_NAME_DASH + LESSON_DESC_LESSON_1_1, invalidGroup);

        // invalid lesson
        assertParseFailure(parser, GRP_DESC_GROUP_G01 + INVALID_LESSON_NAME_TEN, invalidLesson);

        // invalid group and lesson
        assertParseFailure(parser, INVALID_GROUP_NAME_DASH + INVALID_LESSON_NAME_TEN, invalidGroup);

        // missing prefixes
        assertParseFailure(parser, VALID_GROUP_NAME_G01 + LESSON_DESC_LESSON_1_1, missingPrefix);
        assertParseFailure(parser, GRP_DESC_GROUP_G01 + VALID_LESSON_NAME_1_1, missingPrefix);
        assertParseFailure(parser, VALID_GROUP_NAME_G01 + VALID_LESSON_NAME_1_1, missingPrefix);

    }

    @Test
    public void parse_validValueFollowedByInvalidInputs_throwsParseException() {
        String invalidGroup = GroupName.MESSAGE_CONSTRAINTS;
        String invalidLesson = LessonName.MESSAGE_CONSTRAINTS;
        String userInput;

        // valid group then invalid group
        userInput = GRP_DESC_GROUP_G01 + INVALID_GROUP_NAME_DASH + LESSON_DESC_LESSON_1_1;
        assertParseFailure(parser, userInput, invalidGroup);

        // valid lesson then invalid lesson
        userInput = GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1 + INVALID_LESSON_NAME_TEN;
        assertParseFailure(parser, userInput, invalidLesson);

        // valid group and lesson then invalid group and lesson
        userInput = GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1 + INVALID_GROUP_NAME_DASH + INVALID_LESSON_NAME_TEN;
        assertParseFailure(parser, userInput, invalidGroup);
    }

    @Test
    public void parse_validArgs_returnsViewLsnCommand() {
        ViewLsnCommand expected = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G01),
                        new LessonName(VALID_LESSON_NAME_1_1)
                ));
        assertParseSuccess(parser, " grp/ g01 lsn/ 1-1", expected);
        assertParseSuccess(parser, " grp/ g01 lsn/1-1", expected);
        assertParseSuccess(parser, " grp/g01 lsn/ 1-1", expected);
        assertParseSuccess(parser, " grp/g01 lsn/1-1", expected);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        ViewLsnCommand expected;

        // Multiple groups
        expected = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G02),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G02),
                        new LessonName(VALID_LESSON_NAME_1_1)
                ));
        assertParseSuccess(parser, GRP_DESC_GROUP_G01 + GRP_DESC_GROUP_G02
                + LESSON_DESC_LESSON_1_1, expected);

        // Multiple lessons
        expected = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_2),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G01),
                        new LessonName(VALID_LESSON_NAME_1_2)
                ));
        assertParseSuccess(parser, GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1
                + LESSON_DESC_LESSON_1_2, expected);

        // Multiple groups and lessons
        expected = new ViewLsnCommand(new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G02),
                new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_2),
                new QuestionFromGroupLessonPredicate(new GroupName(VALID_GROUP_NAME_G02),
                        new LessonName(VALID_LESSON_NAME_1_2)
                ));
        assertParseSuccess(parser, GRP_DESC_GROUP_G01 + GRP_DESC_GROUP_G02
                + LESSON_DESC_LESSON_1_1 + LESSON_DESC_LESSON_1_2, expected);
    }

    @Test
    public void parse_invalidValueFollowedByValidInputs_acceptsLast() {
        String userInput;
        GroupContainsKeywordPredicate grpPredicate = new GroupContainsKeywordPredicate(VALID_GROUP_NAME_G01);
        LessonContainsKeywordPredicate lsnPredicate = new LessonContainsKeywordPredicate(VALID_LESSON_NAME_1_1);
        QuestionFromGroupLessonPredicate qnPredicate = new QuestionFromGroupLessonPredicate(
                new GroupName(VALID_GROUP_NAME_G01), new LessonName(VALID_LESSON_NAME_1_1)
        );
        ViewLsnCommand expectedCommand;

        // invalid group then valid group
        userInput = INVALID_GROUP_NAME_DASH + GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1;
        expectedCommand = new ViewLsnCommand(grpPredicate, lsnPredicate, qnPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // invalid lesson then valid lesson
        userInput = GRP_DESC_GROUP_G01 + INVALID_LESSON_NAME_TEN + LESSON_DESC_LESSON_1_1;
        expectedCommand = new ViewLsnCommand(grpPredicate, lsnPredicate, qnPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // invalid group and lesson then valid group and lesson
        userInput = INVALID_GROUP_NAME_DASH + INVALID_LESSON_NAME_TEN + GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1;
        expectedCommand = new ViewLsnCommand(grpPredicate, lsnPredicate, qnPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
