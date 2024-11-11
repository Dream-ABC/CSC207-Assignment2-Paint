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
     * Toggles the display state of a color picker popup.
     * If the popup is currently visible, hides it.
     * If hidden, displays it below the source button with the color picker content.
     *
     * @param sourceButton The ToggleButton that triggered the popup toggle action.
     *                     Used to position the popup relative to the button's location.
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

    /**
     * Attempts to hide the popup if it is currently showing.
     *
     * @return true if the popup was showing and was hidden,
     *         false if the popup was already hidden
     */
    public boolean hidePopup() {
        if (this.isShowing()) {
            this.hide();
            return true;
        }
        return false;
    }
}
