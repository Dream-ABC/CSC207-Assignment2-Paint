package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ResizableCanvas extends Pane {
    private final Canvas canvas;
    private final double handleSize = 10;

    // Handles (resize buttons)
    private final Button topLeftHandle, topHandle, topRightHandle;
    private final Button rightHandle, bottomRightHandle, bottomHandle;
    private final Button bottomLeftHandle, leftHandle;

    public ResizableCanvas(double initialWidth, double initialHeight, Canvas panel) {
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

    }

    private Button createHandle() {
        Button handle = new Button();
        handle.setPrefSize(handleSize, handleSize);
        handle.setStyle("-fx-background-color: blue; -fx-border-color: blue;"); // Styling for visibility
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
        // Calculate new width and height for canvas based on handle movement
        double newWidth = canvas.getWidth() + xMultiplier * mouseX;
        double newHeight = canvas.getHeight() + yMultiplier * mouseY;

        // Set minimum canvas size
        newWidth = Math.max(50, newWidth);
        newHeight = Math.max(50, newHeight);

        // Update canvas and pane size
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
        setPrefSize(newWidth + handleSize, newHeight + handleSize);

        // Update handle positions
        layoutChildren();
    }

    @Override
    protected void layoutChildren() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        double halfHandle = handleSize / 2;

        // Position handles around the canvas
        setPosition(topLeftHandle, -halfHandle, -halfHandle);
        setPosition(topHandle, w / 2 - halfHandle, -halfHandle);
        setPosition(topRightHandle, w - halfHandle, -halfHandle);
        setPosition(rightHandle, w - halfHandle, h / 2 - halfHandle);
        setPosition(bottomRightHandle, w - halfHandle, h - halfHandle);
        setPosition(bottomHandle, w / 2 - halfHandle, h - halfHandle);
        setPosition(bottomLeftHandle, -halfHandle, h - halfHandle);
        setPosition(leftHandle, -halfHandle, h / 2 - halfHandle);

        // Set the canvas position within the pane
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
    }

    private void setPosition(Button handle, double x, double y) {
        handle.setLayoutX(x);
        handle.setLayoutY(y);
    }
}
