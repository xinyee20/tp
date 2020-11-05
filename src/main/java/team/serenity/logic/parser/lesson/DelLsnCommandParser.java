package team.serenity.logic.parser.lesson;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;

import java.util.stream.Stream;

import team.serenity.logic.commands.lesson.AddLsnCommand;
import team.serenity.logic.commands.lesson.DelLsnCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.Prefix;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

/**
 * Parses input arguments and creates a new DelLsnCommand object.
 */
public class DelLsnCommandParser implements Parser<DelLsnCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddGrpCommand and returns an AddGrpCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DelLsnCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_LSN);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRP, PREFIX_LSN) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLsnCommand.MESSAGE_USAGE));
        }

        GroupName groupName = SerenityParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GRP).get());
        LessonName lessonName = SerenityParserUtil.parseLessonName(argMultimap.getValue(PREFIX_LSN).get());

        return new DelLsnCommand(groupName, lessonName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
