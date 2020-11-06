package team.serenity.logic.parser.question;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.commands.CommandTestUtil.GRP_DESC_GROUP_G01;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DASH;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_NON_DIGITS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_TEN;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_QN_DESC;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_1;
import static team.serenity.logic.commands.CommandTestUtil.LESSON_DESC_LESSON_1_2;
import static team.serenity.logic.commands.CommandTestUtil.QN_DESC_GROUP_A;
import static team.serenity.logic.commands.CommandTestUtil.QN_DESC_GROUP_B;
import static team.serenity.logic.commands.CommandTestUtil.VALID_GROUP_NAME_G01;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_1;
import static team.serenity.logic.commands.CommandTestUtil.VALID_LESSON_NAME_1_2;
import static team.serenity.logic.commands.CommandTestUtil.VALID_QN_DESC_A;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.serenity.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;
import static team.serenity.testutil.TypicalIndexes.INDEX_SECOND;
import static team.serenity.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.question.EditQnCommand;
import team.serenity.logic.commands.question.EditQnCommand.EditQuestionDescriptor;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.testutil.question.EditQuestionDescriptorBuilder;

class EditQnCommandParserTest {

    private EditQnCommandParser parser = new EditQnCommandParser();

    @Test
    public void parse_missingParts_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditQnCommand.MESSAGE_USAGE);
        // no index specified
        assertParseFailure(parser, VALID_QN_DESC_A, expectedMessage);

        // no field specified
        assertParseFailure(parser, "1", EditQnCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditQnCommand.MESSAGE_USAGE);

        // negative index
        assertParseFailure(parser, "-5" + QN_DESC_GROUP_A, expectedMessage);

        // zero index
        assertParseFailure(parser, "0" + QN_DESC_GROUP_A, expectedMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", expectedMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
                + INVALID_GROUP_NAME_DASH, GroupName.MESSAGE_CONSTRAINTS); // invalid group
        assertParseFailure(parser, "1"
                + INVALID_LESSON_NAME_TEN, LessonName.MESSAGE_CONSTRAINTS); // invalid lesson
        assertParseFailure(parser, "1"
                + INVALID_QN_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description

        // invalid group followed by valid lesson
        assertParseFailure(parser, "1" + INVALID_GROUP_NAME_DASH + LESSON_DESC_LESSON_1_1,
                GroupName.MESSAGE_CONSTRAINTS);

        // valid group followed by invalid group. The test case for invalid group followed by valid group
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + GRP_DESC_GROUP_G01 + INVALID_GROUP_NAME_DASH,
                GroupName.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_GROUP_NAME_DASH + INVALID_LESSON_NAME_TEN
                        + VALID_QN_DESC_A,
                GroupName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + GRP_DESC_GROUP_G01 + LESSON_DESC_LESSON_1_1 + QN_DESC_GROUP_A;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_G01)
                .withLessonName(VALID_LESSON_NAME_1_1).withDescription(VALID_QN_DESC_A).build();
        EditQnCommand expectedCommand = new EditQnCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + GRP_DESC_GROUP_G01 + QN_DESC_GROUP_A;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_G01)
                .withDescription(VALID_QN_DESC_A).build();
        EditQnCommand expectedCommand = new EditQnCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // group
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + GRP_DESC_GROUP_G01;
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
                .withGroupName(VALID_GROUP_NAME_G01).build();
        EditQnCommand expectedCommand = new EditQnCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // lesson
        userInput = targetIndex.getOneBased() + LESSON_DESC_LESSON_1_1;
        descriptor = new EditQuestionDescriptorBuilder().withLessonName(VALID_LESSON_NAME_1_1).build();
        expectedCommand = new EditQnCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + QN_DESC_GROUP_A;
        descriptor = new EditQuestionDescriptorBuilder().withDescription(VALID_QN_DESC_A).build();
        expectedCommand = new EditQnCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + GRP_DESC_GROUP_G01 + GRP_DESC_GROUP_G01
                + LESSON_DESC_LESSON_1_1 + LESSON_DESC_LESSON_1_2
                + QN_DESC_GROUP_B + QN_DESC_GROUP_A;

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
                .withGroupName(VALID_GROUP_NAME_G01)
                .withLessonName(VALID_LESSON_NAME_1_2)
                .withDescription(VALID_QN_DESC_A)
                .build();
        EditQnCommand expectedCommand = new EditQnCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_GROUP_NAME_NON_DIGITS + GRP_DESC_GROUP_G01;
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
                .withGroupName(VALID_GROUP_NAME_G01).build();
        EditQnCommand expectedCommand = new EditQnCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_GROUP_NAME_DASH + GRP_DESC_GROUP_G01
                + QN_DESC_GROUP_A;
        descriptor = new EditQuestionDescriptorBuilder().withGroupName(VALID_GROUP_NAME_G01)
                .withDescription(VALID_QN_DESC_A).build();
        expectedCommand = new EditQnCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
