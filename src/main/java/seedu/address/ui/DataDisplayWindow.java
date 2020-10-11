package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class DataDisplayWindow extends UiPart<Region> {

    private static final String FXML = "DataDisplayWindow.fxml";

    private GroupDataPanel groupDataPanel;
    private LessonDataPanel lessonDataPanel;
    private boolean isDisplayGroupData = true;

    @FXML
    private StackPane dataPanelPlaceholder;

    public DataDisplayWindow(Logic logic) {
        super(FXML);
        groupDataPanel = new GroupDataPanel(logic.getLessonList(), logic.getStudentList());
        lessonDataPanel = new LessonDataPanel(logic.getStudentInfoList());
        Node node = groupDataPanel.getRoot();
        dataPanelPlaceholder.getChildren().add(groupDataPanel.getRoot());
    }

    public boolean ToggleLsnView() {
        if (isDisplayGroupData) {
            dataPanelPlaceholder.getChildren().clear();
            dataPanelPlaceholder.getChildren().add(lessonDataPanel.getRoot());
            isDisplayGroupData = !isDisplayGroupData;
            return true;
        }
        return false;
    }

    public boolean ToggleGrpView() {
        if (!isDisplayGroupData) {
            dataPanelPlaceholder.getChildren().clear();
            dataPanelPlaceholder.getChildren().add(groupDataPanel.getRoot());
            isDisplayGroupData = !isDisplayGroupData;
            return true;
        }
        return false;
    }

}