package team.serenity.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.logic.Logic;
import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.parser.SerenityParser;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;
import team.serenity.ui.groupdata.GroupDataPanel;
import team.serenity.ui.lessondata.LessonDataPanel;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ButtonBar buttonBar;

    // Ui parts relating to serenity
    private DataPanel groupDataPanel;
    private DataPanel lessonDataPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane dataDisplayPlaceholder;

    @FXML
    private VBox buttonPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        this.helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(this.helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.resultDisplay = new ResultDisplay();
        this.resultDisplayPlaceholder.getChildren().add(this.resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        this.commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.buttonBar = new ButtonBar();
        this.buttonPanelPlaceholder.getChildren().add(this.buttonBar);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        this.primaryStage.setHeight(guiSettings.getWindowHeight());
        this.primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            this.primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            this.primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!this.helpWindow.isShowing()) {
            this.helpWindow.show();
        } else {
            this.helpWindow.focus();
        }
    }

    void show() {
        this.primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(this.primaryStage.getWidth(), this.primaryStage.getHeight(),
            (int) this.primaryStage.getX(), (int) this.primaryStage.getY());
        this.logic.setGuiSettings(guiSettings);
        this.helpWindow.hide();
        this.primaryStage.hide();
    }

    /**
     * Switch to group data view if in lesson data view.
     */
    @FXML
    private void toggleLsnView() {
        this.lessonDataPanel = new LessonDataPanel(this.logic.getStudentInfoList(), this.logic.getQuestionList());
        this.dataDisplayPlaceholder.getChildren().clear();
        this.dataDisplayPlaceholder.getChildren().add(this.lessonDataPanel.getRoot());
    }

    /**
     * Switch to lesson data view if in group data view.
     */
    @FXML
    private void toggleGrpView() {
        this.groupDataPanel = new GroupDataPanel(this.logic.getLessonList(), this.logic.getStudentList());
        this.dataDisplayPlaceholder.getChildren().clear();
        this.dataDisplayPlaceholder.getChildren().add(this.groupDataPanel.getRoot());
    }

    /**
     * Adds a new group button.
     */
    @FXML
    private void handleAddGrp(String groupName) {
        Button groupButton = new Button(groupName);
        setUpButton(groupButton);
        buttonBar.addGroupButton(groupButton);
    }

    /**
     * Sets up the newly created group button.
     */
    public void setUpButton(Button groupButton) {
        groupButton.setId(groupButton.getText());
        groupButton.setAlignment(Pos.CENTER);
        groupButton.setContentDisplay(ContentDisplay.CENTER);
        groupButton.setMnemonicParsing(false);
        groupButton.setPrefWidth(50);
        groupButton.setTextAlignment(TextAlignment.CENTER);
        groupButton.setOnAction(event -> {
            String commandText = "viewgrp grp/" + groupButton.getText();
            try {
                executeCommand(commandText);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Deletes an existing group button.
     */
    @FXML
    private void handleDelGrp(String groupName) {
        for (Node groupButton : buttonBar.getChildren()) {
            if (groupButton.getId().equals(groupName)) {
                buttonBar.deleteGroupButton(groupButton);
                break;
            }
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = this.logic.execute(commandText);
            this.logger.info("Result: " + commandResult.getFeedbackToUser());
            this.resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isToggleGrpView()) {
                toggleGrpView();
            }

            if (commandResult.isToggleLsnView()) {
                toggleLsnView();
            }

            if (commandResult.isAddGrp()) {
                // commandText would be in AddGrpCommand format correctly by the time it reaches here
                String groupName = commandText.split(" ")[1].split("/")[1];
                handleAddGrp(groupName);
            }

            if (commandResult.isDelGrp()) {
                // commandText would be in DelGrpCommand format correctly by the time it reaches here
                String groupName = commandText.split(" ")[1].split("/")[1];
                handleDelGrp(groupName);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            this.logger.info("Invalid command: " + commandText);
            this.resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
