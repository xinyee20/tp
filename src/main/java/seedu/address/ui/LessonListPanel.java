package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Lesson;

public class LessonListPanel extends UiPart<Region> {

    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Lesson> studentListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public LessonListPanel(ObservableList<Lesson> studentList) {
        super(FXML);
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code StudentCard}.
     */
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
}
