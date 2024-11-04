package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ResizableCanvas extends Pane {
    private final PaintPanel canvas;
    private final double handleSize = 5;

    private final Button topLeftHandle, topHandle, topRightHandle;
    private final Button rightHandle, bottomRightHandle, bottomHandle;
    private final Button bottomLeftHandle, leftHandle;

    public ResizableCanvas(double initialWidth, double initialHeight, PaintPanel panel) {
        this.canvas = panel;
        getChildren().add(canvas);

        topLeftHandle = createHandle();
        topHandle = createHandle();
        topRightHandle = createHandle();
        rightHandle = createHandle();
        bottomRightHandle = createHandle();
        bottomHandle = createHandle();
        bottomLeftHandle = createHandle();
        leftHandle = createHandle();

        getChildren().addAll(topLeftHandle, topHandle, topRightHandle, rightHandle, bottomRightHandle, bottomHandle, bottomLeftHandle, leftHandle);

        canvas.setWidth(initialWidth);
        canvas.setHeight(initialHeight);
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
    }

    private void resizeCanvas(double mouseX, double mouseY, int xMultiplier, int yMultiplier) {
        double newWidth = canvas.getWidth() + xMultiplier * mouseX;
        double newHeight = canvas.getHeight() + yMultiplier * mouseY;


        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
        setPrefSize(newWidth + handleSize, newHeight + handleSize);

        setUpPositions();
        this.canvas.update(this.canvas.getModel(), null);
    }

    private void setUpPositions() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        double halfHandle = handleSize / 2;

        setPosition(topLeftHandle, -halfHandle, -halfHandle);
        setPosition(topHandle, w / 2 - halfHandle, -halfHandle);
        setPosition(topRightHandle, w - halfHandle, -halfHandle);
        setPosition(rightHandle, w - halfHandle, h / 2 - halfHandle);
        setPosition(bottomRightHandle, w - halfHandle, h - halfHandle);
        setPosition(bottomHandle, w / 2 - halfHandle, h - halfHandle);
        setPosition(bottomLeftHandle, -halfHandle, h - halfHandle);
        setPosition(leftHandle, -halfHandle, h / 2 - halfHandle);

        canvas.setLayoutX(halfHandle);
        canvas.setLayoutY(halfHandle);
    }

    private void setPosition(Button handle, double x, double y) {
        handle.setLayoutX(x);
        handle.setLayoutY(y);
    }
}
