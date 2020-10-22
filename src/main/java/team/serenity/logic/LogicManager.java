package team.serenity.logic;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.parser.SerenityParser;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Question;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SerenityParser serenityParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.serenityParser = new SerenityParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = this.serenityParser.parseCommand(commandText);
        commandResult = command.execute(model);

        /*
        TODO: To write the data to the external file after each command is executed.
        try {
            // storage.saveSerenity(model.getSerenity());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
         */

        return commandResult;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return this.model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        this.model.setGuiSettings(guiSettings);
    }

    // ========== Serenity ==========

    @Override
    public Path getSerenityFilePath() {
        return this.model.getSerenityFilePath();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return this.model.getFilteredGroupList();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return this.model.getStudentList();
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return this.model.getLessonList();
    }

    @Override
    public ObservableList<StudentInfo> getStudentInfoList() {
        return this.model.getStudentsInfoList();
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return this.model.getFilteredQuestionList();
    }

}
