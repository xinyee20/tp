package team.serenity.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonBar extends VBox {

    public ButtonBar() {
        setFillWidth(true);
    }

    public void addGroupButton(Button groupButton) {
        this.getChildren().add(groupButton);
    }

    public void deleteGroupButton(Node groupButtonNode) {
        this.getChildren().remove(groupButtonNode);
    }

}
