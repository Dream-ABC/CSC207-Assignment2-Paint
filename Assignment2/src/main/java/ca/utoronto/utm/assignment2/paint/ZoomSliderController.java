package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/**
 * Controls the zoom slider functionality in the paint application.
 * This controller handles mouse events on the zoom slider to adjust the canvas zoom level.
 */
public class ZoomSliderController implements EventHandler<MouseEvent> {
    private PaintModel model;
    private ResizableCanvas canvas;

    /**
     * Constructs a new ZoomSliderController.
     *
     * @param model The PaintModel that maintains the application state
     * @param canvas The ResizableCanvas that will be zoomed
     */
    public ZoomSliderController(PaintModel model, ResizableCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
    }

    /**
     * Handles mouse events on the zoom slider by updating the zoom factor in the model.
     *
     * @param event The MouseEvent triggered by interacting with the slider
     */
    @Override
    public void handle(MouseEvent event) {
        Slider zoomSlider = (Slider) event.getSource();
        int zoomFactor = (int) zoomSlider.getValue();
        model.setZoomFactor(zoomFactor, canvas);
    }
}
