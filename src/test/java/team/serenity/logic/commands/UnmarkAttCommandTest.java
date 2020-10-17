package team.serenity.logic.commands;

import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class UnmarkAttCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnmarkAttCommand(null));
    }

    @Test
    public void execute_unmarkStudent_success() {

    }

}
