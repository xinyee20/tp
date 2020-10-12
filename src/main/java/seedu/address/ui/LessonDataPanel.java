package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.group.StudentInfo;

public class LessonDataPanel extends UiPart<Region> {
    private static final String FXML = "LessonDataPanel.fxml";
    private StudentInfoListPanel studentInfoListPanel;

    @FXML
    private StackPane studentInfoListPanelPlaceholder;

    /**
     * Constructor for panel to display Lesson data
     */
    public LessonDataPanel(ObservableList<StudentInfo> studentInfoList) {
        super(FXML);
        studentInfoListPanel = new StudentInfoListPanel(studentInfoList);
        studentInfoListPanelPlaceholder.getChildren().add(studentInfoListPanel.getRoot());
    }
}

