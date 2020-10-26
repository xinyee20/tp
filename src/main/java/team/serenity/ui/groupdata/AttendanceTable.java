package team.serenity.ui.groupdata;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import team.serenity.model.group.Lesson;

public class AttendanceTable extends TableView<Lesson> implements Initializable {

    @FXML
    private TableView<Lesson> attendanceTableView;
    @FXML
    private TableColumn<Lesson, String> nameColumn;
    @FXML
    private TableColumn<Lesson, String> studentNoColumn;

    ObservableList<Lesson> lessons;

    public AttendanceTable(ObservableList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendanceTableView = new TableView<>();
        attendanceTableView.setItems(lessons);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellFactory(new PropertyValueFactory("name"));
        studentNoColumn = new TableColumn<>("Student Number");
        studentNoColumn.setCellFactory(new PropertyValueFactory("studentNo"));

        attendanceTableView.getColumns().addAll(nameColumn, studentNoColumn);
    }

}
