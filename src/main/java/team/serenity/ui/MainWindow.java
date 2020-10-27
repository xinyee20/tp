package team.serenity.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import team.serenity.commons.core.GuiSettings;
import team.serenity.commons.core.LogsCenter;
import team.serenity.logic.Logic;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Group;
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
    private TitleDisplay titleDisplay;
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
    private StackPane titleDisplayPlaceholder;

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
        this.titleDisplay = new TitleDisplay();
        this.titleDisplayPlaceholder.getChildren().add(this.titleDisplay.getRoot());

        this.resultDisplay = new ResultDisplay();
        this.resultDisplayPlaceholder.getChildren().add(this.resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        this.commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.buttonBar = new ButtonBar();
        this.buttonPanelPlaceholder.getChildren().add(this.buttonBar);
    }

    /**
     * Adds group buttons for existing groups.
     */
    void loadGroupButtons() {
        if (logic.hasGroup()) {
            for (Group group : logic.getGroups()) {
                handleAddGrp(group.getGroupName().toString());
            }
        }
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
        this.lessonDataPanel = new LessonDataPanel(this.logic.getStudentInfoList(),
                this.logic.getFilteredQuestionList());
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
        setUpGroupButton(groupButton);
        buttonBar.addGroupButton(groupButton);
    }

    /**
     * Sets up the newly created group button.
     */
    public void setUpGroupButton(Button groupButton) {
        groupButton.setLayoutX(20);
        groupButton.setLayoutY(65);
        groupButton.setMnemonicParsing(false);
        groupButton.setPrefWidth(65);
        groupButton.setId(groupButton.getText());

        Image groupImage = new Image("images/group.png");
        ImageView groupImageView = new ImageView(groupImage);
        groupImageView.setFitHeight(15);
        groupImageView.setFitWidth(15);
        groupImageView.setPickOnBounds(true);
        groupImageView.setPreserveRatio(true);

        groupButton.setGraphic(groupImageView);
        VBox.setMargin(buttonPanelPlaceholder, new Insets(10));
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
     * Views attendance sheet of the specified group.
     */
    private void handleViewAtt() {
        GroupDataPanel groupDataPanel = (GroupDataPanel) this.groupDataPanel;
        groupDataPanel.changeAttendanceTab();
    }

    /**
     * Views participation score sheet of the specified group.
     */
    private void handleViewScore() {
        GroupDataPanel groupDataPanel = (GroupDataPanel) this.groupDataPanel;
        groupDataPanel.changeParticipationTab();
    }

    private String getGroupName(String commandText) {
        return commandText.split(" ")[1].split("/")[1];
    }

    private String getLessonName(String commandText) {
        return commandText.split(" ")[2].split("/")[1];
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
                String groupName = getGroupName(commandText);
                this.titleDisplay.setGroupTitle(groupName);
            }

            if (commandResult.isToggleLsnView()) {
                toggleLsnView();
                String groupName = getGroupName(commandText);
                String lessonName = getLessonName(commandText);
                this.titleDisplay.setLessonTitle(groupName, lessonName);
            }

            if (commandResult.isAddGrp()) {
                String groupName = getGroupName(commandText);
                handleAddGrp(groupName);
            }

            if (commandResult.isDelGrp()) {
                String groupName = getGroupName(commandText);
                handleDelGrp(groupName);
            }

            if (commandResult.isViewAtt()) {
                handleViewAtt();
            }

            if (commandResult.isViewScore()) {
                handleViewScore();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            this.logger.info("Invalid command: " + commandText);
            this.resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
