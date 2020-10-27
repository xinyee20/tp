package team.serenity.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonBar extends VBox {

    public ButtonBar() {
        setFillWidth(true);
    }

    public void addButton(Button button) {
        this.getChildren().add(button);
    }

    public void deleteButton(Node buttonNode) {
        this.getChildren().remove(buttonNode);
    }

}
