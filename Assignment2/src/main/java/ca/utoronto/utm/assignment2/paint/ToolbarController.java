package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import java.util.Map;

public class ToolbarController implements EventHandler<ActionEvent> {
    private PaintModel model;
    private Map<String, ToggleButton> toolButtons;
    private ToolChooserPopUp toolChooserPopUp;
    private ShapeChooserPopUp shapeChooserPopUp;

    public ToolbarController(PaintModel model, Map<String, ToggleButton> toolButtons, ToolChooserPopUp toolChooserPopUp,ShapeChooserPopUp shapeChooserPopUp) {
        this.model = model;
        this.toolButtons = toolButtons;
        this.toolChooserPopUp = toolChooserPopUp;
        this.shapeChooserPopUp = shapeChooserPopUp;
    }

    @Override
    public void handle(ActionEvent event) {
        model.removeSelectionTool();

        toolButtons.values().forEach(button ->
                button.setStyle("-fx-background-color: transparent; -fx-padding: 10;")
        );

        ToggleButton button = (ToggleButton) event.getSource();
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; " +
                "-fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");

        if (button.getId().equals("Selection")) {
            model.setMode("Selection Tool");
            shapeChooserPopUp.hideShapePopup();
        }

        if (button.getId().equals("Tools")) {
            toolChooserPopUp.toggleShapePopup(button);
            shapeChooserPopUp.hideShapePopup();
        }

        if (button.getId().equals("Shapes")) {
            shapeChooserPopUp.toggleShapePopup(button);
            toolChooserPopUp.hideShapePopup();
        }
    }
}