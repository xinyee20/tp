package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;

public class GroupDataPanel extends UiPart<Region> {
    private static final String FXML = "GroupDataPanel.fxml";
    private StudentListPanel studentListPanel;
    private LessonListPanel lessonListPanel;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane lessonListPanelPlaceholder;

    /**
     * Constructor for panel to display tutorial group datea
     */
    public GroupDataPanel(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        super(FXML);
        studentListPanel = new StudentListPanel(studentList);
        lessonListPanel = new LessonListPanel(lessonList);
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
        lessonListPanelPlaceholder.getChildren().add(lessonListPanel.getRoot());
    }
}
