package team.serenity.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalIndexes.INDEX_FIRST;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.question.Description;

public class SerenityParserUtilTest {

    // Serenity
    private static final String VALID_GROUP_NAME = "G04";
    private static final String VALID_FILE_PATH = "CS2101_G04.xlsx";
    private static final String VALID_QUESTION_DESC = "What is the deadline for the report?";

    private static final String INVALID_GROUP_NAME_LOWERCASE = "g04";
    private static final String INVALID_GROUP_NAME_NON_DIGIT = "Gxx ";
    private static final String INVALID_FILE_PATH = "invalid/path.xlsx";
    private static final String INVALID_QUESTION_DESC = " ";

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
    public void parseGroupName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SerenityParserUtil.parseGroupName(null));
    }

    @Test
    public void parseGroupName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseGroupName(INVALID_GROUP_NAME_NON_DIGIT));
    }

    @Test
    public void parseGroupName_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        GroupName expectedGroupName = new GroupName(VALID_GROUP_NAME);
        assertEquals(expectedGroupName, SerenityParserUtil.parseGroupName(VALID_GROUP_NAME));
    }

    @Test
    public void parseGroupName_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String groupNameWithWhitespace = WHITESPACE + VALID_GROUP_NAME + WHITESPACE;
        GroupName expectedGroupName = new GroupName(VALID_GROUP_NAME);
        assertEquals(expectedGroupName, SerenityParserUtil.parseGroupName(groupNameWithWhitespace));
    }

    @Test
    public void parseFilePath_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SerenityParserUtil.parseFilePath(null));
    }

    @Test
    public void parseFilePath_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseFilePath(INVALID_FILE_PATH));
    }

    @Test
    public void parseFilePath_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        XlsxUtil expectedXlsx = new XlsxUtil(VALID_FILE_PATH, new XSSFWorkbook(VALID_FILE_PATH));
        assertEquals(expectedXlsx, SerenityParserUtil.parseFilePath(VALID_FILE_PATH));
    }

    @Test
    public void parseFilePath_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String filePathWithWhitespace = WHITESPACE + VALID_FILE_PATH + WHITESPACE;
        XlsxUtil expectedXlsx = new XlsxUtil(VALID_FILE_PATH, new XSSFWorkbook(VALID_FILE_PATH));
        assertEquals(expectedXlsx, SerenityParserUtil.parseFilePath(filePathWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SerenityParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> SerenityParserUtil.parseDescription(INVALID_QUESTION_DESC));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        Description expectedDescription = new Description(VALID_QUESTION_DESC);
        assertEquals(expectedDescription, SerenityParserUtil.parseDescription(VALID_QUESTION_DESC));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION_DESC + WHITESPACE;
        Description expectedDescription = new Description(VALID_QUESTION_DESC);
        assertEquals(expectedDescription, SerenityParserUtil.parseDescription(questionWithWhitespace));
    }

}
