package team.serenity.logic.commands;

import static team.serenity.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import team.serenity.model.Model;
import team.serenity.model.ModelManager;

public class ExitCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, CommandResult.UiAction.EXIT);
        CommandTestUtil.assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

}
