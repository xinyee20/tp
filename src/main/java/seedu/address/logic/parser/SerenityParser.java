package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGrpCommand;
import seedu.address.logic.commands.AddLsnCommand;
import seedu.address.logic.commands.AddQnCommand;
import seedu.address.logic.commands.AddScoreCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DelGrpCommand;
import seedu.address.logic.commands.DelQnCommand;
import seedu.address.logic.commands.DelStudentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLsnCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAttCommand;
import seedu.address.logic.commands.UnmarkAttCommand;
import seedu.address.logic.commands.ViewGrpCommand;
import seedu.address.logic.commands.ViewLsnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SerenityParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
        Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        // Serenity
        case AddGrpCommand.COMMAND_WORD:
            return new AddGrpCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DelStudentCommand.COMMAND_WORD:
            return new DelStudentCommandParser().parse(arguments);

        case AddLsnCommand.COMMAND_WORD:
            return new AddLsnCommandParser().parse(arguments);

        case DelGrpCommand.COMMAND_WORD:
            return new DelGrpCommandParser().parse(arguments);

        case ViewGrpCommand.COMMAND_WORD:
            return new ViewGrpCommandParser().parse(arguments);

        case ViewLsnCommand.COMMAND_WORD:
            return new ViewLsnCommandParser().parse(arguments);

        case MarkAttCommand.COMMAND_WORD:
            return new MarkAttCommandParser().parse(arguments);

        case AddScoreCommand.COMMAND_WORD:
            return new AddScoreCommandParser().parse(arguments);

        case UnmarkAttCommand.COMMAND_WORD:
            return new UnmarkAttCommandParser().parse(arguments);

        case AddQnCommand.COMMAND_WORD:
            return new AddQnCommandParser().parse(arguments);

        case DelQnCommand.COMMAND_WORD:
            return new DelQnCommandParser().parse(arguments);

        // AB3
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteLsnCommand.COMMAND_WORD:
            return new DeleteLsnCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
