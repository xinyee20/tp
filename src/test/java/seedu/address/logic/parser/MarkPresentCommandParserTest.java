package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.MarkPresentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.group.StudentInfo;

class MarkPresentCommandParserTest {

    private MarkPresentCommandParser parser = new MarkPresentCommandParser();

    @Test
    public void execute_missingStudentName_throwsParseException() {

//        String arguments = "markpresent id/e0123456";
//
//        MarkPresentCommandParser markPresentCommandParser = new MarkAbsentCommandParser().parse(arguments);
//
//        String expectedMessage = String.format(MarkPresentCommandParser.MESSAGE_STUDENT_NOT_GIVEN);


        /**
         * Enter lesson
         * Get UniqueStudentInfo List
         * Missing student id prefix
         * Throw Exception
         */
    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

//        String arguments = "markpresent name/Aaron Tan";
//
//        MarkPresentCommandParser markPresentCommandParser = new MarkAbsentCommandParser().parse(arguments);
//
//        String expectedMessage = String.format(MarkPresentCommandParser.MESSAGE_STUDENT_NOT_GIVEN);


        /**
         * Enter lesson
         * Get UniqueStudentInfo List
         * Missing student id prefix
         * Throw Exception
         */
    }

    @Test
    public void execute_invalidParameter_throwsCommandException() {

        /**
         * Enter lesson
         * Get UniqueStudentInfo List
         * Does not understand parameter, it is not 'all'
         * Throw Exception
         */

    }

    @Test
    public void parse_validArgs_returnsMarkPresentCommand() {

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
