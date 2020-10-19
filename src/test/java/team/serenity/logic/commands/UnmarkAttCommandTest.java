package team.serenity.logic.commands;

import static team.serenity.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;

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
