package team.serenity.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonBar extends VBox {

    /**
     * Constructs a ButtonBar.
     */
    public ButtonBar() {
        setFillWidth(true);
        setSpacing(10);
        setPadding(new Insets(10, 0, 0, 0));
    }

    /**
     * Adds a new button to ButtonBar.
     * @param button Button to be added.
     */
    public void addButton(Button button) {
        this.getChildren().add(button);
    }

    /**
     * Deletes an existing button from ButtonBar.
     * @param buttonNode Node of the button to be deleted
     */
    public void deleteButton(Node buttonNode) {
        this.getChildren().remove(buttonNode);
    }

}
