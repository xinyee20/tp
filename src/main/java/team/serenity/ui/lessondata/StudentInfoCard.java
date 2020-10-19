package team.serenity.ui.lessondata;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.serenity.model.group.StudentInfo;
import team.serenity.ui.UiPart;

/**
 * An UI component that displays information of a {@code StudentInfo}.
 */
public class StudentInfoCard extends UiPart<Region> {

    private static final String FXML = "StudentInfoListCard.fxml";

    public final StudentInfo studentInfo;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentNumber;
    @FXML
    private Label attendance;
    @FXML
    private Label participation;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     */
    public StudentInfoCard(StudentInfo studentInfo, int displayedIndex) {
        super(FXML);
        this.studentInfo = studentInfo;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(studentInfo.getStudent().getName());
        this.studentNumber.setText(studentInfo.getStudent().getStudentNumber());
        this.attendance.setText(studentInfo.getAttendance().getAttendance() ? "Present" : "Absent");
        this.participation.setText(String.valueOf(studentInfo.getParticipation().getScore()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentInfoCard)) {
            return false;
        }

        // state check
        StudentInfoCard card = (StudentInfoCard) other;
        return this.id.getText().equals(card.id.getText())
            && this.studentInfo.equals(card.studentInfo);
    }

}
