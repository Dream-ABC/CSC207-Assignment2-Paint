package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.Observable;
import java.util.Observer;

public class ResizableCanvas extends Pane implements Observer {
    private final PaintPanel panel;
    private final double handleSize = 6;

    private final Button topLeftHandle, topHandle, topRightHandle;
    private final Button rightHandle, bottomRightHandle, bottomHandle;
    private final Button bottomLeftHandle, leftHandle;

    private double width, height;

    public ResizableCanvas(double initialWidth, double initialHeight, PaintPanel panel) {
        this.panel = panel;
        this.panel.setStyle("-fx-background-color: white;");
        this.panel.setMinSize(95, 95);
        this.panel.getModel().addObserver(this);

        setMinSize(100, 100);
        getChildren().add(panel);

        this.width = initialWidth;
        this.height = initialHeight;

        this.topLeftHandle = createHandle();
        this.topHandle = createHandle();
        this.topRightHandle = createHandle();
        this.rightHandle = createHandle();
        this.bottomRightHandle = createHandle();
        this.bottomHandle = createHandle();
        this.bottomLeftHandle = createHandle();
        this.leftHandle = createHandle();

        getChildren().addAll(topLeftHandle, topHandle, topRightHandle, rightHandle, bottomRightHandle, bottomHandle, bottomLeftHandle, leftHandle);
        updateLayers();
        setPrefSize(initialWidth + handleSize, initialHeight + handleSize);
        setupDragEvents();
        setUpPositions();
    }

    private Button createHandle() {
        Button handle = new Button();
        handle.setPrefSize(handleSize, handleSize);
        handle.setMaxSize(handleSize, handleSize);
        handle.setMinSize(handleSize, handleSize);
        handle.setStyle("-fx-background-color: gray; -fx-border-color: black;"); // Styling for visibility
        return handle;
    }

    private void setupDragEvents() {
        topLeftHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), -1, -1));
        topHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), 0, -1));
        topRightHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), 1, -1));
        rightHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), 1, 0));
        bottomRightHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), 1, 1));
        bottomHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), 0, 1));
        bottomLeftHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), -1, 1));
        leftHandle.setOnMouseDragged(e -> resizeCanvas(e.getX(), e.getY(), -1, 0));

//        topLeftHandle.setOnMouseReleased(e -> this.centerCanvas());
//        topHandle.setOnMouseReleased(e -> {centerCanvas();});
//        topRightHandle.setOnMouseReleased(e -> this.centerCanvas());
//        rightHandle.setOnMouseReleased(e -> this.centerCanvas());
//        bottomRightHandle.setOnMouseReleased(e -> this.centerCanvas());
//        bottomHandle.setOnMouseReleased(e -> this.centerCanvas());
//        bottomLeftHandle.setOnMouseReleased(e -> this.centerCanvas());
//        leftHandle.setOnMouseReleased(e -> this.centerCanvas());
    }

    private void resizeCanvas(double mouseX, double mouseY, int xMultiplier, int yMultiplier) {
        width = panel.getWidth() + xMultiplier * mouseX;
        height = panel.getHeight() + yMultiplier * mouseY;
        updateLayers();

        setPrefSize(width + handleSize, height + handleSize);

        this.panel.update(this.panel.getModel(), null);

        this.panel.getModel().notifyChange();
        setUpPositions();
    }

    public void setUpPositions() {
        double w = panel.getWidth();
        double h = panel.getHeight();
        double halfHandle = handleSize / 2;

        setPosition(topLeftHandle, 0, 0);
        setPosition(topHandle, w / 2, 0);
        setPosition(topRightHandle, w, 0);
        setPosition(rightHandle, w, h / 2);
        setPosition(bottomRightHandle, w, h);
        setPosition(bottomHandle, w / 2, h);
        setPosition(bottomLeftHandle, 0, h);
        setPosition(leftHandle, 0, h / 2);

        panel.setLayoutX(halfHandle);
        panel.setLayoutY(halfHandle);
    }

    private void setPosition(Button handle, double x, double y) {
        handle.setLayoutX(x);
        handle.setLayoutY(y);
    }

    public void scaleCanvas(double zoomFactor) {
        double scale = zoomFactor / 100.0;

        // Scale the paint panel
        panel.getTransforms().clear();
        panel.getTransforms().add(new Scale(scale, scale));

        // Calculate translations for handlers based on the scale
        double widthDiff = (panel.getWidth() * scale) - panel.getWidth();
        double heightDiff = (panel.getHeight() * scale) - panel.getHeight();

        // Clear existing transforms for all handlers
        clearHandleTransforms();

        // Top-left handle stays fixed (0,0)

        // Handles that move horizontally only
        topHandle.getTransforms().add(new Translate(widthDiff/2, 0));
        bottomHandle.getTransforms().add(new Translate(widthDiff/2, heightDiff));

        // Handles that move vertically only
        leftHandle.getTransforms().add(new Translate(0, heightDiff/2));
        rightHandle.getTransforms().add(new Translate(widthDiff, heightDiff/2));

        // Handles that move both directions
        topRightHandle.getTransforms().add(new Translate(widthDiff, 0));
        bottomLeftHandle.getTransforms().add(new Translate(0, heightDiff));
        bottomRightHandle.getTransforms().add(new Translate(widthDiff, heightDiff));

        // Update the container size to accommodate the scaled content
        updateLayers();
        setPrefSize((width * scale) + handleSize, (height * scale) + handleSize);
    }

    private void clearHandleTransforms() {
        topLeftHandle.getTransforms().clear();
        topHandle.getTransforms().clear();
        topRightHandle.getTransforms().clear();
        rightHandle.getTransforms().clear();
        bottomRightHandle.getTransforms().clear();
        bottomHandle.getTransforms().clear();
        bottomLeftHandle.getTransforms().clear();
        leftHandle.getTransforms().clear();
    }

    private void updateLayers() {
        for (PaintLayer layer: this.panel.getModel().getLayers()) {
            layer.setWidth(width);
            layer.setHeight(height);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateLayers();
        setUpPositions();
    }
}
