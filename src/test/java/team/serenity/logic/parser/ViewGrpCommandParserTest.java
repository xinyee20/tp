package team.serenity.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

class ViewGrpCommandParserTest {

    private final ViewGrpCommandParser parser = new ViewGrpCommandParser();

    @Test
    void parse_emptyString_throwsParseException() {
        assertThrows(ParseException.class,() -> this.parser.parse(""));
    }

    @Test
    void parse_whiteSpace_throwsParseException() {
        assertThrows(ParseException.class,() -> this.parser.parse("  "));
    }

    @Test
    void parse_noPrefixRandomStringInput_throwsParseException() {
        assertThrows(ParseException.class,() -> this.parser.parse("12da"));
    }

    @Test
    void parse_prefixWithoutParameters_throwsParseException() {
        assertThrows(ParseException.class,() -> this.parser.parse("grp/"));
    }

    @Test
    void parse_prefixWithWhiteSpaceOnly_throwsParseException() {
        assertThrows(ParseException.class,() -> this.parser.parse("grp/   "));
    }

    @Test
    void parse_prefixWithParameters() throws ParseException {
        ViewGrpCommand expected = new ViewGrpCommand(new GroupContainsKeywordPredicate("g04"));
        assertEquals(expected, parser.parse("grp/g04"));
    }
}
