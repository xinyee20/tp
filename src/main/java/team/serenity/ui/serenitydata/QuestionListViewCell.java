package team.serenity.ui.serenitydata;

import javafx.scene.control.ListCell;
import team.serenity.model.group.question.Question;
import team.serenity.ui.lessondata.QuestionCard;

public class QuestionListViewCell extends ListCell<Question> {

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
