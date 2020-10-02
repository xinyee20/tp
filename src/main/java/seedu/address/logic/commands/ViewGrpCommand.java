package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ViewGrpCommand extends Command {

    public static final String COMMAND_WORD = "viewgrp";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(COMMAND_WORD);
    }
}
