package team.serenity.logic.commands.studentinfo;

import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.serenity.commons.core.index.Index;
import team.serenity.model.group.student.Student;

class FlagAttCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkPresentCommand((Index) null));
        assertThrows(NullPointerException.class, () -> new MarkPresentCommand((Student) null));
    }
}
