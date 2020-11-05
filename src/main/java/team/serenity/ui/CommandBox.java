package team.serenity.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import team.serenity.logic.Logic;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private String defaultColor = "F8F8FF";
    private String errorColor = "#FFC107";

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        this.commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            this.commandExecutor.execute(this.commandTextField.getText());
            this.commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        String textFillDefaultColor = String.format("-fx-text-fill: %s;", defaultColor);
        this.commandTextField.setStyle(textFillDefaultColor);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        String textFillErrorColor = String.format("-fx-text-fill: %s;", errorColor);
        this.commandTextField.setStyle(textFillErrorColor);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {

        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
