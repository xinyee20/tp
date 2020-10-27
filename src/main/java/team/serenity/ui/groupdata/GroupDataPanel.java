package team.serenity.ui.groupdata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import team.serenity.commons.core.LogsCenter;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.ui.DataPanel;

public class GroupDataPanel extends DataPanel {
    private static final String FXML = "GroupDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupDataPanel.class);

    @FXML
    private ListView<Student> studentListView;

    @FXML
    private ListView<Lesson> lessonListView;

    @FXML
    private SplitPane splitPane;

    /**
     * Constructor for panel to display tutorial group data.
     */
    public GroupDataPanel(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        super(FXML);
        this.studentListView.setItems(studentList);
        this.studentListView.setCellFactory(listView -> new StudentListViewCell());
        this.lessonListView.setItems(lessonList);
        this.lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    class LessonListViewCell extends ListCell<Lesson> {

        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }

    class StudentListViewCell extends ListCell<Student> {

        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1).getRoot());
            }
        }
    }
}
