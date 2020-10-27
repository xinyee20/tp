package team.serenity.ui.serenitydata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.ui.DataPanel;
import team.serenity.ui.lessondata.LessonDataPanel.StudentInfoListViewCell;
import team.serenity.ui.lessondata.StudentInfoCard;

public class SerenityDataPanel extends DataPanel {
    private static final String FXML = "SerenityDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SerenityDataPanel.class);

    @FXML
    private TabPane tabPane;

    private SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

    @FXML
    private ListView<StudentInfo> flaggedAttendanceListView;

    @FXML
    private ListView<Question> questionListView;

    /**
     * Constructor for panel to display tutorial group data.
     */
    public SerenityDataPanel(ObservableList<StudentInfo> studentInfo, ObservableList<Question> questionList) {
        super(FXML);
        this.flaggedAttendanceListView.setItems(studentInfo);
        this.flaggedAttendanceListView.setCellFactory(listView -> new StudentInfoListViewCell());
        this.questionListView.setItems(questionList);
        this.questionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Switch to attendanceTab.
     */
    public void changeAttendanceTab() {
        selectionModel.select(2);
    }

    /**
     * Switch to participationTab.
     */
    public void changeParticipationTab() {
        selectionModel.select(3);
    }

    class FlaggedAttendanceListViewCell extends ListCell<StudentInfo> {

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
