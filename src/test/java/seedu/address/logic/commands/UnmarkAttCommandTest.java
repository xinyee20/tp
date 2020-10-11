package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

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
