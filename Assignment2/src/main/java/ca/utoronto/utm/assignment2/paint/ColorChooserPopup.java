package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.util.Locale;

/**
 * A class to represent a popup to select colors for the PaintPanel.
 * This popup allows users to select a color from a color picker,
 * which will then be applied to all the shapes for drawing.
 */
public class ColorChooserPopup extends Popup {
    private final PaintModel model;
    private final ColorPicker colorPicker;

    /**
     * Constructs a ColorPickerPopup which allows users to select a color for shapes.
     * on the PaintModel. The selected color will be applied to the PaintModel and
     * the popup will hide after selection.
     *
     * @param model the PaintModel on which the selected color will be applied
     */
    public ColorChooserPopup(PaintModel model) {
        Locale.setDefault(Locale.ENGLISH);

        this.model = model;
        this.model.setColor(Color.BLACK);

        // Set default color
        this.colorPicker = new ColorPicker();
        this.colorPicker.setValue(this.model.getColor());

        // Add listener
        this.colorPicker.setOnAction(event -> {
            Color selectedColor = this.colorPicker.getValue();
            this.model.setColor(selectedColor);
            this.hide();  // hide popup after selecting the color
        });
    }

    /**
     * Toggles the color picker popup and manages its interactions with the PaintPanel.
     *
     */
    public void togglePopup(ToggleButton sourceButton) {
        if (!hidePopup()) {
            this.getContent().clear();
            this.getContent().add(this.colorPicker);
            this.show(sourceButton,
                    sourceButton.localToScreen(0, 0).getX(),
                    sourceButton.localToScreen(0, 0).getY() + sourceButton.getHeight());
        }
    }

    public boolean hidePopup() {
        if (this.isShowing()) {
            this.hide();
            return true;
        }
        return false;
    }
}
