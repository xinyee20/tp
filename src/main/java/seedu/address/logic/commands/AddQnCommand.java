package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QN;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Question;
import seedu.address.model.group.UniqueQuestionList;

/**
 * Adds a question to the question list of a specific tutorial group's lesson in Serenity.
 */
public class AddQnCommand extends Command {

    public static final String COMMAND_WORD = "addqn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new question to the specific lesson. "
            + "Parameters: "
            + PREFIX_QN + "QUESTION";

    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists.";

    private final Question toAdd;

    /**
     * Creates an AddQnCommand to add the specified {@code Lesson}
     */
    public AddQnCommand(Question question) {
        requireNonNull(question);
        toAdd = question;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredGroupList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_GROUP);
        }

        if (model.getFilteredLessonList().size() != 1) {
            throw new CommandException(MESSAGE_NOT_VIEWING_A_LESSON);
        }

        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueQuestionList uniqueQuestionList = uniqueLesson.getQuestionList();

        if (uniqueQuestionList.contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        uniqueQuestionList.add(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddQnCommand // instanceof handles nulls
                && toAdd.equals(((AddQnCommand) other).toAdd));
    }

}
