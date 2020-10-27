package team.serenity.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class TitleDisplay extends UiPart<Region> {

    private static final String FXML = "TitleDisplay.fxml";

    @FXML
    private TextArea titleDisplay;

    public TitleDisplay() {
        super(FXML);
        setDefaultTitle();
    }

    public void setDefaultTitle() {
        String defaultTitle = "Serenity";
        this.titleDisplay.setText(defaultTitle);
    }

    public void setGroupTitle(String groupName) {
        String groupTitle = String.format("Tutorial Group: %s", groupName);
        this.titleDisplay.setText(groupTitle);
    }

    public void setLessonTitle(String groupName, String lessonName) {
        String lessonTitle = String.format("Tutorial Lesson: %s %s", groupName, lessonName);
        this.titleDisplay.setText(lessonTitle);
    }

}
