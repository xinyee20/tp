package team.serenity.logic.commands;

import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AddScoreCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScoreCommand(null, 2));
    }

    @Test
    public void execute_addScoreOutOfRange_failure() {

    }

    @Test
    public void execute_addScore_success() {

    }

}
