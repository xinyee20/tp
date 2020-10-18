package seedu.address.ui.lessondata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Question;
import seedu.address.model.group.StudentInfo;
import seedu.address.ui.DataPanel;
import seedu.address.ui.groupdata.GroupDataPanel;


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
        studentInfoListView.setItems(studentInfoList);
        studentInfoListView.setCellFactory(listView -> new StudentInfoListViewCell());
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new QuestionListViewCell());
        tabPane.getSelectionModel().select(0);
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

