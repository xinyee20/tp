package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.SerenityParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Question;

public class SerenityParserUtilTest {

    // Serenity
    private static final String VALID_QUESTION = "What is the deadline for the report?";

    private static final String INVALID_QUESTION = " ";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> SerenityParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, SerenityParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, SerenityParserUtil.parseIndex("  1  "));
    }

    // For Serenity

    @Test
    public void parseQuestion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SerenityParserUtil.parseQuestion((String) null));
    }

    @Test
    public void parseQuestion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseQuestion(INVALID_QUESTION));
    }

    @Test
    public void parseQuestion_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        Question expectedQuestion = new Question(VALID_QUESTION);
        assertEquals(expectedQuestion, SerenityParserUtil.parseQuestion(VALID_QUESTION));
    }

    @Test
    public void parseQuestion_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION + WHITESPACE;
        Question expectedQuestion = new Question(VALID_QUESTION);
        assertEquals(expectedQuestion, SerenityParserUtil.parseQuestion(questionWithWhitespace));
    }

}
