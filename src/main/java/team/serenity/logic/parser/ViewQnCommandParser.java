package team.serenity.logic.parser;

import team.serenity.logic.commands.question.ViewQnCommand;

/**
 * Parses input arguments and creates a new ViewQnCommand object.
 */
public class ViewQnCommandParser implements Parser<ViewQnCommand> {

    @Override
    public ViewQnCommand parse(String args) {
        return new ViewQnCommand();
    }

}
