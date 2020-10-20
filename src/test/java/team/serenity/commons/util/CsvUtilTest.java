package team.serenity.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CsvUtilTest {

    @Test
    public void computeClassNameTest() {

        String[] expectedResults = new String[] {"1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2",
            "5-1", "5-2", "6-1", "6-2", "7-1", "7-2",
            "8-1", "8-2", "9-1", "9-2", "10-1", "10-2", "11-1", "11-2", "12-1", "12-2"};
        int input = 1;
        for (String expectedResult : expectedResults) {
            assertTrue(CsvUtil.computeLessonName(input)
                .equals(expectedResults[input - 1]));
            input++;
        }
    }

}
