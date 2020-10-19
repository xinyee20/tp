package team.serenity.ui.lessondata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.Question;
import team.serenity.model.group.StudentInfo;
import team.serenity.ui.DataPanel;
import team.serenity.ui.groupdata.GroupDataPanel;

public class LessonDataPanel extends DataPanel {
    private static final String FXML = "LessonDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupDataPanel.class);

    @FXML
    private ListView<StudentInfo> studentInfoListView;

    @FXML
    private ListView<Question> questionListView;

    @FXML
    private TabPane tabPane;

    /**
     * Constructor for panel to display Lesson data
     */
    public LessonDataPanel(ObservableList<StudentInfo> studentInfoList, ObservableList<Question> questionList) {
        super(FXML);
        this.studentInfoListView.setItems(studentInfoList);
        this.studentInfoListView.setCellFactory(listView -> new StudentInfoListViewCell());
        this.questionListView.setItems(questionList);
        this.questionListView.setCellFactory(listView -> new QuestionListViewCell());
        this.tabPane.getSelectionModel().select(0);
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

    class QuestionListViewCell extends ListCell<Question> {

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionCard(question, getIndex() + 1).getRoot());
            }
        }
    }
}

