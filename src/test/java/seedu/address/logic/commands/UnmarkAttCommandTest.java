package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class UnmarkAttCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAbsentCommand(null));
    }

    @Test
    public void execute_unmarkStudent_success() {
    }

    @Test
    public void execute_wrongName_throwsCommandException() {

    }

    @Test
    public void execute_wrongStudentId_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentName_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

}
