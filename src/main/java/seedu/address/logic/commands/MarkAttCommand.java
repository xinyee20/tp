package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Student;

/**
 * Marks the attendance of a class / a student in the class.
 */
public class MarkAttCommand extends Command{
    public static final String COMMAND_WORD = "markatt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of all students / a student in a class. "
            + "Parameters: "
            + "all or "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    private Student toMarkAtt;
    private boolean isWholeClass;
    private Set<Student> toMarkAllAtt;

    public static final String MESSAGE_SUCCESS = "Attendance marked: %1$s";

    /**
     * Creates an MarkAttCommand to mark all {@code Student} present
     */
    public MarkAttCommand() {
        // Mark all students present
        isWholeClass = true;
    }

    /**
     * Creates an MarkAttCommand to mark the specified {@code Student} present
     */
    public MarkAttCommand(String student) {
        requireNonNull(student);
        isWholeClass = false;
        // Mark specified student present
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isWholeClass) {
            // Mark single student attendance

            model.getSerenity().getGroupList().indexOf(0);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toMarkAtt));
        }
        // Mark whole class attendance

        return new CommandResult(String.format(MESSAGE_SUCCESS, toMarkAllAtt));
    }
}
