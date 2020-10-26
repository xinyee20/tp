package team.serenity.model.group.studentinfo;

import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentInfoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentInfo(null, null, null));
    }
}
