package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;

import java.util.Objects;

public class ZoomController implements EventHandler<ActionEvent> {
    private PaintModel model;
    private ResizableCanvas canvas;
    public ZoomController(PaintModel model, ResizableCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
    }

    @Override
    public void handle(ActionEvent event) {
        Control source = (Control) event.getSource();

        if (Objects.equals(source.getId(), "zoomComboBox")) {
            ComboBox zoomComboBox = (ComboBox) source;
            String selectedZoom = (String) zoomComboBox.getValue();
            int zoomFactor = Integer.parseInt(selectedZoom.replace("%", ""));
            model.setZoomFactor(zoomFactor, canvas);
        } else if (Objects.equals(source.getId(), "zoomInBtn")) {
            int zoomFactor = model.getZoomFactor();
            int increment;

            if (zoomFactor + 10 >= 200 || zoomFactor + 25 >= 200) {
                increment = 25;
            } else {
                increment = 10;
            }

            zoomFactor += increment;
            zoomFactor = (zoomFactor / increment) * increment;

            if (zoomFactor > 800) zoomFactor = 800;
            model.setZoomFactor(zoomFactor, canvas);


        } else if (Objects.equals(source.getId(), "zoomOutBtn")) {
            int zoomFactor = model.getZoomFactor();
            int increment;

            if ((zoomFactor - 10 >= 200 || zoomFactor - 25 >= 200) ) {
                increment = 25;
            } else {
                increment = 10;
            }

            zoomFactor -= increment;
            zoomFactor = (zoomFactor / increment) * increment;

            if (zoomFactor < 12) zoomFactor = 12;
            model.setZoomFactor(zoomFactor, canvas);
        }
    }
}