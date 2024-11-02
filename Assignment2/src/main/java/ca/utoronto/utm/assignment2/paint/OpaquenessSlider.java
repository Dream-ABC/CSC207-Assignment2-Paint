package ca.utoronto.utm.assignment2.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class OpaquenessSlider extends Slider implements ChangeListener {

    private PaintPanel paintPanel;
    private Slider slider;

    /**
     * Initializes a slider for controlling transparency values, associates
     * its values to transparency settings for shapes, and adds it to the view.
     *
     * @param paintPanel the main panel where drawing actions are managed.
     */
    public OpaquenessSlider(PaintPanel paintPanel) {

        this.paintPanel = paintPanel;
        this.paintPanel.setOpaqueness(100);

        // Create popup and slider
        this.slider = new Slider(0, 100, 100);
        this.slider.setShowTickLabels(true);
        this.slider.setMajorTickUnit(10);
        this.slider.setBlockIncrement(1);

        // Add event handler
        this.slider.valueProperty().addListener(this);
    }

    /**
     * Returns the slider node that allows users to adjust transparency.
     *
     * @return the Slider instance.
     */
    public Slider getSlider() {
        return slider;
    }

    /**
     * Whenever the value of the transparency slider changes, makes changes
     * in paint panel as well.
     *
     * @param observable the observable value that has changed.
     * @param oldValue the previous value of the slider before the change.
     * @param newValue the new value of the slider after the change.
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        int transparencyValue = ((Number) newValue).intValue();
        System.out.println(transparencyValue);
        this.paintPanel.setOpaqueness(transparencyValue);
    }
}
