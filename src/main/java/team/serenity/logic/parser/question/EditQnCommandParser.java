package team.serenity.logic.parser.question;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_LSN;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;

import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.question.EditQnCommand;
import team.serenity.logic.parser.ArgumentMultimap;
import team.serenity.logic.parser.ArgumentTokenizer;
import team.serenity.logic.parser.Parser;
import team.serenity.logic.parser.SerenityParserUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;

/**
 * Parses input arguments and creates a new EditQnCommand.
 */
public class EditQnCommandParser implements Parser<EditQnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditQnCommand
     * and returns an EditQnCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditQnCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRP, PREFIX_LSN, PREFIX_QN);

        Index index;

        try {
            index = SerenityParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditQnCommand.MESSAGE_USAGE), pe);
        }

        EditQnCommand.EditQuestionDescriptor editQuestionDescriptor = new EditQnCommand.EditQuestionDescriptor();

        if (argMultimap.getValue(PREFIX_GRP).isPresent()) {
            editQuestionDescriptor.setGroupName(new GroupName(argMultimap.getValue(PREFIX_GRP).get()));
        }
        if (argMultimap.getValue(PREFIX_LSN).isPresent()) {
            editQuestionDescriptor.setLessonName(new LessonName(argMultimap.getValue(PREFIX_LSN).get()));
        }
        if (argMultimap.getValue(PREFIX_QN).isPresent()) {
            editQuestionDescriptor.setDescription(SerenityParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_QN).get()));
        }

        if (!editQuestionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditQnCommand.MESSAGE_NOT_EDITED);
        }

        return new EditQnCommand(index, editQuestionDescriptor);
    }

}

