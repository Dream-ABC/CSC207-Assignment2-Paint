package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class ZoomSliderController implements EventHandler<MouseEvent> {
    private PaintModel model;
    private ResizableCanvas canvas;

    public ZoomSliderController(PaintModel model, ResizableCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
    }

    @Override
    public void handle(MouseEvent event) {
        Slider zoomSlider = (Slider) event.getSource();
        int zoomFactor = (int) zoomSlider.getValue();
        model.setZoomFactor(zoomFactor, canvas);
    }
}
