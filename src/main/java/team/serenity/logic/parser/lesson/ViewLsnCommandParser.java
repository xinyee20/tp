package team.serenity.logic.parser.lesson;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;

import java.util.stream.Stream;

import team.serenity.logic.commands.lesson.ViewLsnCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonContainsKeywordPredicate;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.QuestionFromGroupLessonPredicate;

/**
 * Parses input arguments and creates a new ViewLsnCommand object.
 */
public class ViewLsnCommandParser implements Parser<ViewLsnCommand> {

    private final ParseException viewLsnCommandParserException = new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewLsnCommand.MESSAGE_USAGE));

    @Override
    public ViewLsnCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_LSN);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_LSN) || !argMultimap.getPreamble().isEmpty()) {
            throw this.viewLsnCommandParserException;
        }

        GroupName grpKeyword = SerenityParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GRP).get());
        LessonName lsnKeyword = SerenityParserUtil.parseLessonName(argMultimap.getValue(PREFIX_LSN).get());


        return new ViewLsnCommand(new GroupContainsKeywordPredicate(grpKeyword.groupName),
                new LessonContainsKeywordPredicate(lsnKeyword.lessonName),
                new QuestionFromGroupLessonPredicate(grpKeyword, lsnKeyword));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
