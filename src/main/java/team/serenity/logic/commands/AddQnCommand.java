package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_GROUP;
import static team.serenity.commons.core.Messages.MESSAGE_NOT_VIEWING_A_LESSON;
import static team.serenity.logic.parser.CliSyntax.PREFIX_QN;

import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.util.UniqueList;

/**
 * Adds a question to the question list of a specific tutorial group's lesson in serenity.
 */
public class AddQnCommand extends Command {

    public static final String COMMAND_WORD = "addqn";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a new question to the specific lesson. "
        + "Parameters: "
        + PREFIX_QN + "QUESTION\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_QN + "What is the deadline for the report?\n";

    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists.";

    private final Question toAdd;

    /**
     * Creates an AddQnCommand to add the specified {@code Question}.
     */
    public AddQnCommand(Question question) {
        requireNonNull(question);
        this.toAdd = question;
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

        Group uniqueGroup = model.getFilteredGroupList().get(0);
        Lesson uniqueLesson = model.getFilteredLessonList().get(0);
        UniqueList<Question> uniqueQuestionList = uniqueLesson.getQuestionList();

        if (uniqueQuestionList.contains(this.toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        this.toAdd.setGroupAndLesson(uniqueGroup.getName(), uniqueLesson.getName());
        uniqueQuestionList.add(this.toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddQnCommand // instanceof handles nulls
                && this.toAdd.equals(((AddQnCommand) other).toAdd));
    }

    @Override
    public int hashCode() {
        return this.toAdd.hashCode();
    }
}
