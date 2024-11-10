package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

public class ToolbarController implements EventHandler<ActionEvent> {
    private PaintModel model;

    public ToolbarController(PaintModel model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
//        for (Node node : this.getChildren()) {
//            if (node instanceof VBox container) {
//                ToggleButton button = (ToggleButton) container.getChildren().getFirst();
//                button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
//            }
//        }
//        ToggleButton button = (ToggleButton) event.getSource();
//        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
    }
}
