package team.serenity.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.model.Model;
import team.serenity.model.group.GroupContainsKeywordPredicate;

public class ButtonBar extends VBox {

    public ButtonBar() {
        setFillWidth(true);
    }

    public void addButton(Button button) {
        button.setAlignment(Pos.CENTER);
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setMnemonicParsing(false);
        button.setPrefWidth(50);
        button.setTextAlignment(TextAlignment.CENTER);
        getChildren().add(button);
    }

}
