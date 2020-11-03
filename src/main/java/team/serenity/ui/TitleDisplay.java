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

    /**
     * Constructs a TitleDisplay.
     */
    public TitleDisplay() {
        super(FXML);
        setDefaultTitle();
    }

    /**
     * Display the default title.
     */
    public void setDefaultTitle() {
        String defaultTitle = "Serenity";
        this.titleDisplay.setText(defaultTitle);
    }

    /**
     * Display the title of the group which the user is currently viewing.
     * @param groupName The title of the group.
     */
    public void setGroupTitle(String groupName) {
        String groupTitle = String.format("Tutorial Group: %s", groupName);
        this.titleDisplay.setText(groupTitle);
    }

    /**
     * Display the title of the lesson which the user is currently viewing.
     * Displays the title of the group which the lesson belongs to as well.
     * @param groupName The title of the group.
     * @param lessonName The title of the lesson.
     */
    public void setLessonTitle(String groupName, String lessonName) {
        String lessonTitle = String.format("Tutorial Lesson: %s %s", groupName, lessonName);
        this.titleDisplay.setText(lessonTitle);
    }

}
