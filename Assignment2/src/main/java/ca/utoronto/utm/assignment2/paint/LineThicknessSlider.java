package ca.utoronto.utm.assignment2.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.stage.Popup;

/**
 * A class for the line thickness slider, which will allow users to select their desired
 * line thickness when drawing outlined shapes. The range is from 1.0 to 10.0.
 */
public class LineThicknessSlider extends Slider implements ChangeListener {

    private final PaintPanel paintPanel;
    private final Slider slider;

    /**
     * Initializes a slider for controlling line thickness values, associates
     * its values to line thickness settings for shapes, and adds it to the view.
     *
     * @param paintPanel the main panel where drawing actions are managed.
     */
    public LineThicknessSlider(PaintPanel paintPanel) {

        this.paintPanel = paintPanel;

        // Create popup and slider
        this.slider = new Slider(0, 50, 1);
        this.slider.setShowTickLabels(true);
        this.slider.setMajorTickUnit(5);
        this.slider.setBlockIncrement(5);
        this.slider.setPrefWidth(400);

        // Add event handler
        this.slider.valueProperty().addListener(this);
    }

    /**
     * Displays the line thickness slider in a popup, enabling the user to adjust
     * its position and select a value.
     */
    public void show() {
        // Prevent unexpected actions on canvas
        this.paintPanel.setDisable(true);

        Popup popup = new Popup();
        popup.getContent().add(this.slider); // Add the slider directly to the popup
        popup.setAutoHide(true);

        // when the popup event is ended (popup closed), enable actions on canvas again
        popup.setOnHidden(event -> this.paintPanel.setDisable(false));

        popup.show(this.paintPanel.getScene().getWindow());
    }

    /**
     * Whenever the value of the line thickness slider changes, makes changes
     * in paint panel as well.
     *
     * @param observable the observable value that has changed.
     * @param oldValue the previous value of the slider before the change.
     * @param newValue the new value of the slider after the change.
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        int thicknessValue = ((Number) newValue).intValue();
        this.paintPanel.setLineThickness(thicknessValue);
    }
}
