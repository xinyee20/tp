package team.serenity.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.question.Question;

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
        assertEquals(INDEX_FIRST, SerenityParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, SerenityParserUtil.parseIndex("  1  "));
    }

    // For Serenity

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SerenityParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseDescription(INVALID_QUESTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        String expectedQuestion = new Question(VALID_QUESTION).getDescription();
        assertEquals(expectedQuestion, SerenityParserUtil.parseDescription(VALID_QUESTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION + WHITESPACE;
        String expectedQuestion = new Question(VALID_QUESTION).getDescription();
        assertEquals(expectedQuestion, SerenityParserUtil.parseDescription(questionWithWhitespace));
    }

}
