package team.serenity.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
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
import team.serenity.ui.datapanel.DataPanel;
import team.serenity.ui.datapanel.GroupDataPanel;
import team.serenity.ui.datapanel.LessonDataPanel;
import team.serenity.ui.datapanel.SerenityDataPanel;

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
    private CommandBox commandBox;
    private SideBar sideBar;
    private HelpWindow helpWindow;

    // Ui parts relating to serenity
    private DataPanel serenityDataPanel;
    private DataPanel groupDataPanel;
    private DataPanel lessonDataPanel;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private ScrollPane sidebarPlaceholder;

    @FXML
    private StackPane titleDisplayPlaceholder;

    @FXML
    private StackPane dataDisplayPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

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
        toggleHomeView();

        this.titleDisplay = new TitleDisplay();
        this.titleDisplayPlaceholder.getChildren().add(this.titleDisplay.getRoot());

        this.commandBox = new CommandBox(this::executeCommand);
        this.commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.resultDisplay = new ResultDisplay();
        this.resultDisplayPlaceholder.getChildren().add(this.resultDisplay.getRoot());

        this.sideBar = new SideBar();
        this.sidebarPlaceholder.setContent(this.sideBar.getRoot());
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
     * Switch to lesson data view if in group data view.
     */
    @FXML
    private void handleViewGrp(String groupName) {
        this.dataDisplayPlaceholder.getChildren().clear();
        this.groupDataPanel = new GroupDataPanel(this.logic.getLessonList(), this.logic.getStudentList());
        this.dataDisplayPlaceholder.getChildren().add(this.groupDataPanel.getRoot());
        this.titleDisplay.setGroupTitle(groupName);
    }

    /**
     * Switch to group data view if in lesson data view.
     */
    @FXML
    private void handleViewLsn(String groupName, String lessonName) {
        this.lessonDataPanel = new LessonDataPanel(this.logic.getStudentInfoList(),
                this.logic.getFilteredQuestionList(), groupName, lessonName);
        this.dataDisplayPlaceholder.getChildren().clear();
        this.dataDisplayPlaceholder.getChildren().add(this.lessonDataPanel.getRoot());
        this.titleDisplay.setLessonTitle(groupName, lessonName);
    }

    /**
     * Switch to serenity data view.
     */
    @FXML
    private void toggleHomeView() {
        this.serenityDataPanel = new SerenityDataPanel(this.logic.getAllStudentInfo(),
            this.logic.getFilteredQuestionList());
        this.dataDisplayPlaceholder.getChildren().clear();
        this.dataDisplayPlaceholder.getChildren().add(this.serenityDataPanel.getRoot());
    }

    /**
     * Adds a new group button.
     */
    @FXML
    private void handleAddGrp(String groupName) {
        Button groupButton = new Button(groupName);
        setUpGroupButton(groupButton);
    }

    public void setUpButton(Button button, String imgUrl, EventHandler<ActionEvent> event) {
        button.setLayoutX(20);
        button.setLayoutY(65);
        button.setMnemonicParsing(false);
        button.setPrefWidth(70);
        button.setId(button.getText());

        Image image = new Image(imgUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        VBox.setMargin(sidebarPlaceholder, new Insets(10));
        button.setOnAction(event);
        sideBar.addButton(button);
    }

    public void setUpAttButton() {
        Button attButton = new Button("Flags");
        String attImgUrl = "images/flag.png";
        EventHandler<ActionEvent> attEvent = event -> {
            String commandText = "viewflag";
            try {
                executeCommand(commandText);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        };
        setUpButton(attButton, attImgUrl, attEvent);
    }

    public void setUpQnButton() {
        Button qnButton = new Button("Qns");
        String qnImgUrl = "images/question.png";
        EventHandler<ActionEvent> qnEvent = event -> {
            String commandText = "viewqn";
            try {
                executeCommand(commandText);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        };
        setUpButton(qnButton, qnImgUrl, qnEvent);
    }

    /**
     * Sets up the newly created group button.
     */
    public void setUpGroupButton(Button groupButton) {
        String groupImgUrl = "images/group.png";
        EventHandler<ActionEvent> groupEvent = event -> {
            String commandText = "viewgrp grp/" + groupButton.getText();
            try {
                executeCommand(commandText);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        };
        setUpButton(groupButton, groupImgUrl, groupEvent);
    }

    /**
     * Deletes an existing group button.
     */
    @FXML
    private void handleDelGrp(String groupName) {
        for (Node groupButton : this.sideBar.getButtons()) {
            if (groupButton.getId().equals(groupName)) {
                sideBar.deleteButton(groupButton);
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

    /**
     * View all students with flagged attendance.
     */
    private void handleFlagAtt() {
        toggleHomeView();
        SerenityDataPanel serenityDataPanel = (SerenityDataPanel) this.serenityDataPanel;
        serenityDataPanel.changeFlaggedAttendanceTab();
        this.titleDisplay.setDefaultTitle();
    }

    /**
     * View all pending questions.
     */
    private void handleViewQn() {
        toggleHomeView();
        SerenityDataPanel serenityDataPanel = (SerenityDataPanel) this.serenityDataPanel;
        serenityDataPanel.changeQuestionTab();
        this.titleDisplay.setDefaultTitle();
    }


    private void refreshTable() {
        this.dataDisplayPlaceholder.getChildren().clear();
        this.groupDataPanel = new GroupDataPanel(this.logic.getLessonList(), this.logic.getStudentList());
        this.dataDisplayPlaceholder.getChildren().add(this.groupDataPanel.getRoot());
    }


    private String getGroupName(String commandText) {
        return commandText.split(" ")[1].split("/")[1].toUpperCase();
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
            String groupName;
            String lessonName;
            CommandResult commandResult = this.logic.execute(commandText);
            this.logger.info("Result: " + commandResult.getFeedbackToUser());
            this.resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            switch (commandResult.getUiAction()) {
            case SHOW_HELP:
                handleHelp();
                break;

            case EXIT:
                handleExit();
                break;

            case VIEW_GRP:
                groupName = getGroupName(commandText);
                handleViewGrp(groupName);
                break;

            case VIEW_LSN:
                groupName = getGroupName(commandText);
                lessonName = getLessonName(commandText);
                handleViewLsn(groupName, lessonName);
                break;

            case ADD_GRP:
                groupName = getGroupName(commandText);
                handleAddGrp(groupName);
                handleViewGrp(groupName);
                break;

            case DEL_GRP:
                groupName = getGroupName(commandText);
                handleDelGrp(groupName);
                break;

            case VIEW_ATT:
                groupName = getGroupName(commandText);
                handleViewGrp(groupName);
                handleViewAtt();
                break;

            case VIEW_SCORE:
                groupName = getGroupName(commandText);
                handleViewGrp(groupName);
                handleViewScore();
                break;

            case FLAG_ATT:
                handleFlagAtt();
                break;

            case VIEW_QN:
                handleViewQn();
                break;

            case REFRESH_TABLE:
                refreshTable();
                break;

            default:

            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            this.logger.info("Invalid command: " + commandText);
            this.resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
