package team.serenity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.question.TypicalQuestion.getTypicalQuestionManager;

import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.UserPrefs;

class ViewFlagCommandTest {

    @Test
    public void execute_viewFlag_success() {
        Model model = new ModelManager(new Serenity(), getTypicalQuestionManager(), new UserPrefs());
        CommandResult commandResult = new ViewFlagCommand().execute(model);

        String expectedMessage = ViewFlagCommand.MESSAGE_SUCCESS;

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

}