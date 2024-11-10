package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.util.Locale;

/**
 * A class to represent a popup to select colors for the PaintPanel.
 * This popup allows users to select a color from a color picker,
 * which will then be applied to all the shapes for drawing.
 */
public class ColorPickerPopup extends Popup {

    private final PaintPanel paintPanel;
    private final View view;
    private final ColorPicker colorPicker;

    /**
     * Constructs a ColorPickerPopup which allows users to select a color for shapes.
     * on the PaintPanel. The selected color will be applied to the PaintPanel and
     * the popup will hide after selection.
     *
     * @param panel the PaintPanel on which the selected color will be applied
     * @param view the View context used by this popup
     */
    public ColorPickerPopup(PaintPanel panel, View view) {
        Locale.setDefault(Locale.ENGLISH);

        this.paintPanel = panel;
        this.view = view;
        this.paintPanel.setColor(Color.BLACK);

        // Set default color
        this.colorPicker = new ColorPicker();
        this.colorPicker.setValue(this.paintPanel.getColor());

        // Add listener
        this.colorPicker.setOnAction(event -> {
            Color selectedColor = this.colorPicker.getValue();
            this.paintPanel.setColor(selectedColor);
            this.hide();  // hide popup after selecting the color
        });
    }

    /**
     * Displays the color picker popup and manages its interactions with the PaintPanel.
     *
     * In addition, this method disables any actions on the PaintPanel to prevent
     * unintended changes while the popup is active, and sets an event handler to re-enable
     * actions on the PaintPanel and refocus on the stage once the popup is closed.
     */
    public void display() {
        // Prevent unexpected actions on canvas
        this.paintPanel.setDisable(true);

        // Add the color picker to the popup
        this.getContent().clear();
        this.getContent().add(this.colorPicker);

        // when the popup event is ended (popup closed), enable actions on canvas again
        this.setOnHidden(event -> {
            this.paintPanel.setDisable(false);
            this.view.getStage().requestFocus();
        });
        this.show(this.paintPanel.getScene().getWindow());
    }
}
