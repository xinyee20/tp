package team.serenity.ui.serenitydata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.ui.DataPanel;
import team.serenity.ui.lessondata.LessonDataPanel.StudentInfoListViewCell;

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
        studentInfo = studentInfo.filtered(info -> info.getAttendance().isFlagged());
        String noFlaggedAttendanceMessage = "Great! You have no flagged attendance to follow up with";
        String noPendingQuestionsMessage = "Great! You have no pending questions to answer";
        this.flaggedAttendanceListView.setItems(studentInfo);
        this.flaggedAttendanceListView.setCellFactory(listView -> new StudentInfoListViewCell());
        this.flaggedAttendanceListView.setPlaceholder(new Label(noFlaggedAttendanceMessage));
        this.questionListView.setItems(questionList);
        this.questionListView.setCellFactory(listView -> new QuestionListViewCell());
        this.questionListView.setPlaceholder(new Label(noPendingQuestionsMessage));
    }

    /**
     * Switch to flaggedAttendanceTab.
     */
    public void changeFlaggedAttendanceTab() {
        selectionModel.select(0);
    }

    /**
     * Switch to questionTab.
     */
    public void changeQuestionTab() {
        selectionModel.select(1);
    }

}
