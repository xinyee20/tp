package seedu.address.ui.lessondata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.StudentInfo;
import seedu.address.ui.DataPanel;
import seedu.address.ui.groupdata.GroupDataPanel;


public class LessonDataPanel extends DataPanel {
    private static final String FXML = "LessonDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupDataPanel.class);

    @FXML
    private ListView<StudentInfo> studentInfoListView;

    /**
     * Constructor for panel to display Lesson data
     */
    public LessonDataPanel(ObservableList<StudentInfo> studentInfoList) {
        super(FXML);
        studentInfoListView.setItems(studentInfoList);
        studentInfoListView.setCellFactory(listView -> new StudentInfoListViewCell());
    }

    class StudentInfoListViewCell extends ListCell<StudentInfo> {

        @Override
        protected void updateItem(StudentInfo studentInfo, boolean empty) {
            super.updateItem(studentInfo, empty);

            if (empty || studentInfo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentInfoCard(studentInfo, getIndex() + 1).getRoot());
            }
        }
    }
}

