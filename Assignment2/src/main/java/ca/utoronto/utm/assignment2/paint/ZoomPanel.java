package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents the zoom control panel in the paint application.
 * This panel includes zoom in/out buttons, a slider, and a combo box for zoom level selection.
 * Implements Observer pattern to stay synchronized with the PaintModel.
 */
public class ZoomPanel extends GridPane implements Observer {
    private ZoomController zoomController;
    private ZoomSliderController zoomSliderController;
    private Slider zoomSlider;
    private ComboBox comboBox;

    /**
     * Constructs a new ZoomPanel with all zoom controls.
     *
     * @param model The PaintModel that maintains the application state
     * @param canvas The ResizableCanvas that will be zoomed
     * @throws FileNotFoundException If the zoom button images cannot be loaded
     */
    public ZoomPanel(PaintModel model, ResizableCanvas canvas) throws FileNotFoundException {
        this.zoomController = new ZoomController(model, canvas);
        this.zoomSliderController = new ZoomSliderController(model, canvas);

        String[] buttonIds = {"ZoomOut", "ZoomIn"};
        ImageView[] buttonImages = new ImageView[buttonIds.length];
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomOut.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomIn.png"
        };

        this.setAlignment(Pos.BASELINE_RIGHT);
        this.setStyle("-fx-background-color: #f8f1f0;");
        this.setPadding(new Insets(7, 4, 7, 10));

        for (int i = 0; i < buttonIds.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            buttonImages[i] = imageView;
        }

        int col = 0;

        this.comboBox = new ComboBox();
        comboBox.setId("zoomComboBox");
        comboBox.setOnAction(zoomController);
        comboBox.getItems().addAll("12%", "25%", "50%", "75%", "100%", "200%", "300%", "400%", "500%", "600%", "700%", "800%");
        comboBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 3px");
        comboBox.setPromptText("100%");
        comboBox.setPrefHeight(28);
        comboBox.setPrefWidth(90);
        this.add(comboBox, col, 0);
        col++;

        Button zoomOut = new Button();
        zoomOut.setId("zoomOutBtn");
        zoomOut.setOnAction(zoomController);
        zoomOut.setGraphic(buttonImages[0]);
        zoomOut.setStyle("-fx-background-color: #f8f1f0;");
        zoomOut.setPadding(new Insets(5, 8, 5, 16));
        this.add(zoomOut, col, 0);
        col++;

        this.zoomSlider = new Slider();
        zoomSlider.setId("zoomSlider");
        zoomSlider.setOnMouseDragged(zoomSliderController);
        zoomSlider.setMin(12);
        zoomSlider.setMax(800);
        zoomSlider.setBlockIncrement(1);
        zoomSlider.setPrefWidth(140);
        zoomSlider.setValue(100);
        this.add(zoomSlider, col, 0);
        col++;

        Button zoomIn = new Button();
        zoomIn.setId("zoomInBtn");
        zoomIn.setOnAction(zoomController);
        zoomIn.setGraphic(buttonImages[1]);
        zoomIn.setStyle("-fx-background-color: #f8f1f0;");
        zoomIn.setPadding(new Insets(5, 8, 5, 8));
        this.add(zoomIn, col, 0);
    }

    /**
     * Updates the zoom controls to reflect changes in the model.
     * This method is called when the observed PaintModel changes.
     *
     * @param o The Observable object (PaintModel)
     * @param arg The argument passed to the notifyObservers method (unused)
     */
    @Override
    public void update(Observable o, Object arg) {
        PaintModel model = (PaintModel) o;
        int factor = model.getZoomFactor();
        comboBox.valueProperty().set(factor + "%");
        comboBox.setPromptText(factor + "%");
        zoomSlider.setValue(factor);
    }
}
