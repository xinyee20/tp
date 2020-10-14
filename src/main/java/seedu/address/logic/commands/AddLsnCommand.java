package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GrpContainsKeywordPredicate;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.LsnContainsKeywordPredicate;

public class AddLsnCommand extends Command {

    public static final String COMMAND_WORD = "addlsn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new lesson to a specified tutorial group. "
            + "Parameters: "
            + PREFIX_GRP + "GRP "
            + PREFIX_PATH + "PATH ";

    public static final String MESSAGE_SUCCESS = "New lesson for tutorial group %2$s added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson for tutorial group %1$s already exists.";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";
    private final GrpContainsKeywordPredicate trgtGrp;
    private final String toAdd;

    /**
     * Creates an AddGrpCommand to add the specified {@code Group}
     */
    public AddLsnCommand(String lesson, GrpContainsKeywordPredicate target) {
        requireNonNull(lesson);
        trgtGrp = target;
        toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(trgtGrp);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_DOES_NOT_EXIST);
        }

        Group trgtGrp = model.getFilteredGroupList().get(0);
        Lesson toAdd = new Lesson(this.toAdd, trgtGrp.getStudents());

        if (trgtGrp.getLessons().contains(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_LESSON, toAdd, trgtGrp));
        }

        trgtGrp.getLessons().add(toAdd);

        model.updateLessonList();
        model.updateFilteredLessonList(new LsnContainsKeywordPredicate(this.toAdd));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, trgtGrp), false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLsnCommand // instanceof handles nulls
                && toAdd.equals(((AddLsnCommand) other).toAdd));
    }

}
