package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import java.util.Map;

/**
 * Controller class for handling toolbar interactions in the paint application.
 * Manages the state of toolbar buttons and their associated popups, including
 * tool selection, shape selection, and color selection.
 */
public class ToolbarController implements EventHandler<ActionEvent> {
    private PaintModel model;
    private Map<String, ToggleButton> toolButtons;
    private ToolChooserPopUp toolChooserPopUp;
    private ShapeChooserPopUp shapeChooserPopUp;
    private ColorChooserPopup colorChooserPopup;

    /**
     * Constructs a new ToolbarController with the specified components.
     *
     * @param model The PaintModel that maintains the application state
     * @param toolButtons Map of button IDs to their corresponding ToggleButtons
     * @param toolChooserPopUp Popup for selecting drawing tools
     * @param shapeChooserPopUp Popup for selecting shapes
     * @param colorChooserPopup Popup for selecting colors
     */
    public ToolbarController(PaintModel model, Map<String, ToggleButton> toolButtons,
                             ToolChooserPopUp toolChooserPopUp, ShapeChooserPopUp shapeChooserPopUp,
                             ColorChooserPopup colorChooserPopup) {
        this.model = model;
        this.toolButtons = toolButtons;
        this.toolChooserPopUp = toolChooserPopUp;
        this.shapeChooserPopUp = shapeChooserPopUp;
        this.colorChooserPopup = colorChooserPopup;
    }

    /**
     * Handles action events from toolbar buttons.
     * This method manages:
     * - Button state and visual appearance
     * - Tool selection mode
     * - Popup visibility for tools, shapes, and colors
     * - Selection tool state
     *
     * When a toolbar button is clicked:
     * 1. The selection tool is removed
     * 2. All buttons are reset to their default style
     * 3. The clicked button is highlighted
     * 4. The appropriate popup is shown/hidden
     * 5. The paint model mode is updated if necessary
     *
     * @param event The ActionEvent from the toolbar button
     */
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