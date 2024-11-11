package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;

import java.util.Objects;

/**
 * Controls the zoom functionality through buttons and combo box interactions.
 * Handles zoom in/out buttons and direct zoom level selection via combo box.
 */
public class ZoomController implements EventHandler<ActionEvent> {
    private PaintModel model;
    private ResizableCanvas canvas;

    /**
     * Constructs a new ZoomController.
     *
     * @param model The PaintModel that maintains the application state
     * @param canvas The ResizableCanvas that will be zoomed
     */
    public ZoomController(PaintModel model, ResizableCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
    }

    /**
     * Handles action events from zoom controls.
     * Supports three types of interactions:
     * - Zoom combo box selection: Sets zoom directly to selected percentage
     * - Zoom in button: Increases zoom by increments (10% or 25% based on current zoom level)
     * - Zoom out button: Decreases zoom by increments (10% or 25% based on current zoom level)
     *
     * The zoom factor is constrained between 12% and 800%.
     * For zoom levels >= 200%, the increment/decrement step is 25%.
     * For zoom levels < 200%, the increment/decrement step is 10%.
     *
     * @param event The ActionEvent triggered by interacting with zoom controls
     */
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