package team.serenity.model.group.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ParticipationTest {

    @Test
    public void testGetScore() {
        Participation testOne = new Participation();
        assertEquals(0, testOne.getScore());

        int score = 4;
        Participation testTwo = new Participation(4);
        assertEquals(score, testTwo.getScore());
    }

    @Test
    public void constructor_scoreBelowRange_throwsIllegalArgumentException() {
        int scoreBelowRange = -1;
        assertThrows(IllegalArgumentException.class, () -> new Participation(scoreBelowRange));
    }

    @Test
    public void constructor_scoreWithinRange_success() {
        int scoreZero = 0;
        assertEquals(scoreZero, new Participation(scoreZero).getScore());

        int scoreThree = 3;
        assertEquals(scoreThree, new Participation(scoreThree).getScore());

        int scoreFive = 5;
        assertEquals(scoreFive, new Participation(scoreFive).getScore());
    }

    @Test
    public void constructor_scoreAboveRange_throwsIllegalArgumentException() {
        int scoreAboveRange = 6;
        assertThrows(IllegalArgumentException.class, () -> new Participation(scoreAboveRange));
    }

    @Test
    public void nullConstructor_scoreSetToZero_success() {
        assertEquals(0, new Participation().getScore());
    }

    @Test
    public void testSetNewScore() {
        Participation newPartipation = new Participation(3);

        Participation originalPartipation = new Participation();
        assertEquals(newPartipation, originalPartipation.setNewScore(3));

        Participation currentPartipation = new Participation(2);
        assertEquals(newPartipation, currentPartipation.setNewScore(3));
    }

    @Test
    public void testEquals() {
        Participation scoreOne = new Participation(1);
        Participation scoreTwo = new Participation(1);
        Participation scoreThree = new Participation(3);
        assertTrue(scoreOne.equals(scoreTwo));
        assertFalse(scoreOne.equals(scoreThree));
    }

    @Test
    public void testToString() {
        String expectedString = "5";
        assertEquals(expectedString, new Participation(5).toString());
    }

}
