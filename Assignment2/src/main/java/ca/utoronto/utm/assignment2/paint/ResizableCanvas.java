package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

public class ResizableCanvas extends Pane implements Observer {
    private final PaintPanel canvas;
    private final double handleSize = 5;

    private final Button topLeftHandle, topHandle, topRightHandle;
    private final Button rightHandle, bottomRightHandle, bottomHandle;
    private final Button bottomLeftHandle, leftHandle;

    private double width, height;

    public ResizableCanvas(double initialWidth, double initialHeight, PaintPanel panel) {
        this.canvas = panel;
        canvas.setMinSize(100, 100);
        setMinSize(105, 105);
        getChildren().add(canvas);

        width = initialWidth;
        height = initialHeight;

        panel.getModel().addObserver(this);

        topLeftHandle = createHandle();
        topHandle = createHandle();
        topRightHandle = createHandle();
        rightHandle = createHandle();
        bottomRightHandle = createHandle();
        bottomHandle = createHandle();
        bottomLeftHandle = createHandle();
        leftHandle = createHandle();

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

//        topLeftHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        topHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        topRightHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        rightHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        bottomRightHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        bottomHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        bottomLeftHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
//        leftHandle.setOnMouseReleased(e -> {this.canvas.getModel().notifyChange(); setUpPositions();});
    }

    private void resizeCanvas(double mouseX, double mouseY, int xMultiplier, int yMultiplier) {
        width = canvas.getWidth() + xMultiplier * mouseX;
        height = canvas.getHeight() + yMultiplier * mouseY;


        updateLayers();

        setPrefSize(width + handleSize, height + handleSize);

        this.canvas.update(this.canvas.getModel(), null);

        this.canvas.getModel().notifyChange();
        setUpPositions();

    }

    public void setUpPositions() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        double halfHandle = handleSize / 2;

        setPosition(topLeftHandle, 0, 0);
        setPosition(topHandle, w / 2, 0);
        setPosition(topRightHandle, w, 0);
        setPosition(rightHandle, w, h / 2);
        setPosition(bottomRightHandle, w, h);
        setPosition(bottomHandle, w / 2, h);
        setPosition(bottomLeftHandle, 0, h);
        setPosition(leftHandle, 0, h / 2);

        canvas.setLayoutX(halfHandle);
        canvas.setLayoutY(halfHandle);
    }

    private void setPosition(Button handle, double x, double y) {
        handle.setLayoutX(x);
        handle.setLayoutY(y);
    }

    private void updateLayers() {
        for (PaintLayer layer: this.canvas.getModel().getLayers()) {
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
