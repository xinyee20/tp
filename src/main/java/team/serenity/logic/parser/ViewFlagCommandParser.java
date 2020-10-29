package team.serenity.logic.parser;

import team.serenity.logic.commands.ViewFlagCommand;

/**
 * Parses input arguments and creates a new ViewFlagCommand object.
 */
public class ViewFlagCommandParser implements Parser<ViewFlagCommand> {

    @Override
    public ViewFlagCommand parse(String args) {
        return new ViewFlagCommand();
    }

}
