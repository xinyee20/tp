package team.serenity.logic.commands;

import static team.serenity.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.serenity.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;

public class HelpCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
            new CommandResult(SHOWING_HELP_MESSAGE, CommandResult.UiAction.SHOW_HELP);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
