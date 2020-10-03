package seedu.address.model.group;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ScoreTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Score(null, null, null));
    }
}
