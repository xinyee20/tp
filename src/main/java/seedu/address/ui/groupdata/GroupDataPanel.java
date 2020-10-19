package seedu.address.ui.groupdata;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.ui.DataPanel;


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
     * Constructor for panel to display tutorial group datea
     */
    public GroupDataPanel(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        super(FXML);
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        lessonListView.setItems(lessonList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
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
