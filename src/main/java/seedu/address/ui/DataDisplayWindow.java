package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

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

    /**
     * Area where data is being displayed and managed depending on the commands of the user
     */
    public DataDisplayWindow(Logic logic) {
        super(FXML);
        groupDataPanel = new GroupDataPanel(logic.getLessonList(), logic.getStudentList());
        lessonDataPanel = new LessonDataPanel(logic.getStudentInfoList());
        Node node = groupDataPanel.getRoot();
        dataPanelPlaceholder.getChildren().add(groupDataPanel.getRoot());
    }

    /**
     * Toggle to lesson data view
     */
    public boolean toggleLsnView() {
        if (isDisplayGroupData) {
            dataPanelPlaceholder.getChildren().clear();
            dataPanelPlaceholder.getChildren().add(lessonDataPanel.getRoot());
            isDisplayGroupData = !isDisplayGroupData;
            return true;
        }
        return false;
    }

    /**
     * Toggle to tutorial group data view
     */
    public boolean toggleGrpView() {
        if (!isDisplayGroupData) {
            dataPanelPlaceholder.getChildren().clear();
            dataPanelPlaceholder.getChildren().add(groupDataPanel.getRoot());
            isDisplayGroupData = !isDisplayGroupData;
            return true;
        }
        return false;
    }

}
