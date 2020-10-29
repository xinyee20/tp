package team.serenity.ui.lessondata;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.ui.UiPart;

/**
 * An UI component that displays information of a {@code StudentInfo}.
 */
public class StudentInfoCard extends UiPart<Region> {

    private static final String FXML = "StudentInfoListCard.fxml";

    public final StudentInfo studentInfo;

    private Image presentImg = new Image("images/tick.png");
    private Image flaggedImg = new Image("images/flag.png");
    private Image absentImg = new Image("images/cross.png");

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
    @FXML
    private ImageView attendanceImg;
    @FXML
    private ImageView participationImg;

    /**
     * Creates a {@code StudentInfoCard} with the given {@code StudentInfo} and index to display.
     */
    public StudentInfoCard(StudentInfo studentInfo, int displayedIndex) {
        super(FXML);
        this.studentInfo = studentInfo;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(studentInfo.getStudent().getStudentName().toString());
        this.studentNumber.setText(studentInfo.getStudent().getStudentNo().toString());
        if (studentInfo.getAttendance().isPresent()) {
            this.attendance.setText("Present");
            this.attendanceImg.setImage(presentImg);
        } else if (studentInfo.getAttendance().isFlagged()) {
            this.attendance.setText("Flagged");
            this.attendanceImg.setImage(flaggedImg);
        } else {
            this.attendance.setText("Absent");
            this.attendanceImg.setImage(absentImg);
        }
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
