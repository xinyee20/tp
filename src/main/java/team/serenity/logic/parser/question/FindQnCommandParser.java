package team.serenity.logic.parser.question;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import team.serenity.logic.commands.question.FindQnCommand;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.question.QuestionContainsKeywordPredicate;

/**
 * Finds and lists all questions whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindQnCommandParser implements Parser<FindQnCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindQuestionCommand
     * and returns a FindQuestionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindQnCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindQnCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindQnCommand(new QuestionContainsKeywordPredicate(Arrays.asList(nameKeywords)));
    }
}


