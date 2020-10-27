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

    public ResultDisplay() {
        super(FXML);
        setDefaultFeedback();
    }

    public void setDefaultFeedback() {
        String defaultFeedback = "Welcome!\n"
            + "You may begin by adding or viewing a tutorial group\n"
            + "Start teaching grace and serenity";
        this.resultDisplay.setText(defaultFeedback);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        this.resultDisplay.setText(feedbackToUser);
    }

}
