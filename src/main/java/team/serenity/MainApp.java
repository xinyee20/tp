package team.serenity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import team.serenity.commons.core.Config;
import team.serenity.commons.core.LogsCenter;
import team.serenity.commons.core.Version;
import team.serenity.commons.exceptions.DataConversionException;
import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.commons.util.ConfigUtil;
import team.serenity.commons.util.StringUtil;
import team.serenity.logic.Logic;
import team.serenity.logic.LogicManager;
import team.serenity.model.Model;
import team.serenity.model.ModelManager;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.managers.Serenity;
import team.serenity.model.userprefs.ReadOnlyUserPrefs;
import team.serenity.model.userprefs.UserPrefs;
import team.serenity.model.util.SampleDataUtil;
import team.serenity.storage.JsonSerenityStorage;
import team.serenity.storage.SerenityStorage;
import team.serenity.storage.Storage;
import team.serenity.storage.StorageManager;
import team.serenity.storage.question.JsonQuestionStorage;
import team.serenity.storage.question.QuestionStorage;
import team.serenity.storage.userprefs.JsonUserPrefsStorage;
import team.serenity.storage.userprefs.UserPrefsStorage;
import team.serenity.ui.Ui;
import team.serenity.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 1, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info(
            "=============================[ Initializing Serenity ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        SerenityStorage serenityStorage = new JsonSerenityStorage(userPrefs.getSerenityFilePath());
        QuestionStorage questionStorage = new JsonQuestionStorage(userPrefs.getQuestionStorageFilePath());
        storage = new StorageManager(serenityStorage, questionStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br> The
     * data from the sample address book will be used instead if {@code storage}'s address book is not found, or an
     * empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlySerenity serenity = initSerenity(storage);
        ReadOnlyQuestionManager questionManager = initQuestionManager(storage);
        return new ModelManager(serenity, questionManager, userPrefs);
    }

    private ReadOnlySerenity initSerenity(Storage storage) {
        Optional<ReadOnlySerenity> serenityOptional = null;
        ReadOnlySerenity serenity;
        try {
            serenityOptional = storage.readSerenity();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                + " Serenity.");
            serenityOptional = Optional.of(new Serenity());
        } catch (IllegalValueException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                + "Serenity.");
            serenityOptional = Optional.of(new Serenity());
        }
        if (serenityOptional.isEmpty()) {
            logger.info("Data file not found. Will be starting with a sample Serenity.");
            serenity = SampleDataUtil.getSampleSerenity();
            try {
                storage.saveSerenity(serenity.getGroupManager());
            } catch (IOException e) {
                logger.warning("Data was not saved");
            }
            return serenity;
        } else {
            return serenityOptional.get();
        }
    }

    /**
     * Returns a {@code ReadOnlyQuestionManager} with the data from {@code storage}'s questions.
     * The data from the sample questions will be used instead if {@code storage}'s questions manager is not found,
     * or an empty question manager will be used instead if errors occur when reading {@code storage}'s
     * question manager.
     */
    private ReadOnlyQuestionManager initQuestionManager(Storage storage) {
        ReadOnlyQuestionManager questionManager;
        try {
            Optional<ReadOnlyQuestionManager> questionManagerOptional = storage.readQuestionManager();
            if (questionManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample QuestionManager.");
            }
            questionManager =
                questionManagerOptional.orElseGet(SampleDataUtil::getSampleQuestionManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                + "QuestionManager.");
            questionManager = new QuestionManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                + "QuestionManager.");
            questionManager = new QuestionManager();
        }

        try {
            storage.saveQuestionManager(questionManager);
            logger.info("Saving initial data of QuestionManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return questionManager;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br> The default file path {@code
     * Config#DEFAULT_CONFIG_FILE} will be used instead if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path, or a new {@code UserPrefs}
     * with default configuration if errors occur when reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning(
                "Problem while reading from the file. Will be starting with an empty Serenity");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Serenity " + MainApp.VERSION);
        this.ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info(
            "============================ [ Stopping Serenity ] =============================");
        try {
            this.storage.saveUserPrefs(model.getUserPrefs());
            this.storage.saveQuestionManager(model.getQuestionManager());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
