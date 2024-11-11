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
    private ColorChooserPopup colorChooserPopup;

    public ToolbarController(PaintModel model, Map<String, ToggleButton> toolButtons,
                             ToolChooserPopUp toolChooserPopUp, ShapeChooserPopUp shapeChooserPopUp,
                             ColorChooserPopup colorChooserPopup) {
        this.model = model;
        this.toolButtons = toolButtons;
        this.toolChooserPopUp = toolChooserPopUp;
        this.shapeChooserPopUp = shapeChooserPopUp;
        this.colorChooserPopup = colorChooserPopup;
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
            toolChooserPopUp.hidePopup();
            shapeChooserPopUp.hidePopup();
            colorChooserPopup.hidePopup();
        }

        if (button.getId().equals("Tools")) {
            button.setSelected(false);
            toolButtons.get("Selection").setSelected(false);
            toolChooserPopUp.togglePopup(button);
            shapeChooserPopUp.hidePopup();
            colorChooserPopup.hidePopup();
        }

        if (button.getId().equals("Shapes")) {
            button.setSelected(false);
            toolButtons.get("Selection").setSelected(false);
            shapeChooserPopUp.togglePopup(button);
            toolChooserPopUp.hidePopup();
            colorChooserPopup.hidePopup();
        }

        if (button.getId().equals("Colours")) {
            button.setSelected(false);
            toolButtons.get("Selection").setSelected(false);
            colorChooserPopup.togglePopup(button);
            shapeChooserPopUp.hidePopup();
            toolChooserPopUp.hidePopup();
        }
    }
}