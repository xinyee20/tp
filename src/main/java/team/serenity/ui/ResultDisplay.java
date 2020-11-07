package team.serenity.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Constructs a ResultDisplay.
     */
    public ResultDisplay() {
        super(FXML);
        setDefaultFeedback();
    }

    /**
     * Feedback to the user a default message.
     */
    public void setDefaultFeedback() {
        String defaultFeedback = "Welcome to Serenity!\n"
            + "You may begin by adding or viewing a tutorial group.\n"
            + "Start teaching with serenity!";
        this.resultDisplay.setText(defaultFeedback);
    }

    /**
     * Feedback to the user.
     * @param feedbackToUser The feedback.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        this.resultDisplay.setText(feedbackToUser);
    }

}
