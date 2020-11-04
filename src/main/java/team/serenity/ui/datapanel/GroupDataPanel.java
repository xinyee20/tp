package team.serenity.ui.datapanel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import team.serenity.commons.core.LogsCenter;
import team.serenity.commons.core.Messages;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;

public class GroupDataPanel extends DataPanel {
    private static final String FXML = "GroupDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupDataPanel.class);

    @FXML
    private TabPane tabPane;

    private SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

    @FXML
    private ListView<Student> studentListView;

    @FXML
    private ListView<Lesson> lessonListView;

    @FXML
    private TableView<Integer> attendanceTableView;

    @FXML
    private TableView<Integer> participationTableView;

    @FXML
    private TableColumn<Integer, String> nameColumn;

    @FXML
    private TableColumn<Integer, String> studentNoColumn;

    /**
     * Constructor for panel to display tutorial group data.
     */
    public GroupDataPanel(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        super(FXML);
        this.studentListView.setItems(studentList);
        this.studentListView.setCellFactory(listView -> new StudentListViewCell());
        this.studentListView.setPlaceholder(new Label(Messages.MESSAGE_NO_STUDENTS));
        this.lessonListView.setItems(lessonList);
        this.lessonListView.setCellFactory(listView -> new LessonListViewCell());
        this.lessonListView.setPlaceholder(new Label(Messages.MESSAGE_NO_LESSONS));
        this.attendanceTableView.setPlaceholder(new Label(Messages.MESSAGE_NO_STUDENTS));
        this.participationTableView.setPlaceholder(new Label(Messages.MESSAGE_NO_STUDENTS));
        populateTable(lessonList, studentList);
    }

    private void populateTable(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        for (int i = 0; i < studentList.size(); i++) {
            attendanceTableView.getItems().add(i);
            participationTableView.getItems().add(i);
        }

        List<TableColumn<Integer, String>> studentDetailList = getStudentDetailColumns(studentList);
        List<TableColumn<Integer, String>> attendanceColumnList = new ArrayList<>(studentDetailList);
        List<TableColumn<Integer, String>> participationColumnList = new ArrayList<>(studentDetailList);

        for (Lesson lesson : lessonList) {
            ObservableList<StudentInfo> studentInfos = lesson.getStudentsInfo().asUnmodifiableObservableList();
            TableColumn<Integer, String> attendanceColumn = getAttendanceColumn(studentInfos, lesson);
            TableColumn<Integer, String> participationColumn = getParticipationColumn(studentInfos, lesson);
            attendanceColumnList.add(attendanceColumn);
            participationColumnList.add(participationColumn);
        }

        this.attendanceTableView.getColumns().setAll(attendanceColumnList);
        this.participationTableView.getColumns().setAll(participationColumnList);
    }

    private List<TableColumn<Integer, String>> getStudentDetailColumns(ObservableList<Student> studentList) {
        List<TableColumn<Integer, String>> columnList = new ArrayList<>();
        TableColumn<Integer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Integer, String> studentNoColumn = new TableColumn<>("Student No.");
        nameColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(studentList.get(rowIndex).getStudentName().toString());
        });
        studentNoColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(studentList.get(rowIndex).getStudentNo().toString());
        });
        columnList.add(nameColumn);
        columnList.add(studentNoColumn);
        return columnList;
    }

    private TableColumn<Integer, String> getAttendanceColumn(ObservableList<StudentInfo> studentInfos, Lesson lesson) {
        TableColumn<Integer, String> column = new TableColumn<>();
        column.setText(lesson.getLessonName().toString());
        column.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(studentInfos.get(rowIndex).getAttendance().toString());
        });
        return column;
    }

    private TableColumn<Integer, String> getParticipationColumn(ObservableList<StudentInfo> studentInfos,
                                                                Lesson lesson) {
        TableColumn<Integer, String> column = new TableColumn<>();
        column.setText(lesson.getLessonName().toString());
        column.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(studentInfos.get(rowIndex).getParticipation().toString());
        });
        return column;
    }
    /**
     * Switch to attendanceTab.
     */
    public void changeAttendanceTab() {
        selectionModel.select(2);
        attendanceTableView.refresh();
    }

    /**
     * Switch to participationTab.
     */
    public void changeParticipationTab() {
        selectionModel.select(3);
        participationTableView.refresh();
    }

    /**
     * refresh table everytime a change is done to group / lesson
     */
    public void refreshTables(ObservableList<Lesson> lessonList, ObservableList<Student> studentList) {
        populateTable(lessonList, studentList);
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
