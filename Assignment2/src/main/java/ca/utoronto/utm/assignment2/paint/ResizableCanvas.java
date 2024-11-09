package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.Observable;
import java.util.Observer;

public class ResizableCanvas extends Pane implements Observer {
    private final PaintPanel panel;
    private final PaintModel model;
    private final double handleSize = 6;

    private final Button topLeftHandle, topHandle, topRightHandle;
    private final Button rightHandle, bottomRightHandle, bottomHandle;
    private final Button bottomLeftHandle, leftHandle;

    private double width, height;
    private double lastMouseX, lastMouseY;

    public ResizableCanvas(double initialWidth, double initialHeight, PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
        this.panel.setStyle("-fx-background-color: white;");
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

        getChildren().addAll(topLeftHandle, topHandle, topRightHandle, rightHandle,
                bottomRightHandle, bottomHandle, bottomLeftHandle, leftHandle);
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
        handle.setStyle("-fx-background-color: white; -fx-border-color: black;");
        return handle;
    }

    private void setupDragEvents() {
        setupHandleDragEvents(topLeftHandle, -1, -1);
        setupHandleDragEvents(topHandle, 0, -1);
        setupHandleDragEvents(topRightHandle, 1, -1);
        setupHandleDragEvents(rightHandle, 1, 0);
        setupHandleDragEvents(bottomRightHandle, 1, 1);
        setupHandleDragEvents(bottomHandle, 0, 1);
        setupHandleDragEvents(bottomLeftHandle, -1, 1);
        setupHandleDragEvents(leftHandle, -1, 0);
    }

    private void setupHandleDragEvents(Button handle, int xMultiplier, int yMultiplier) {
        handle.setOnMousePressed(e -> {
            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();
        });

        handle.setOnMouseDragged(e -> {
            double scale = model.getZoomFactor() / 100.0;
            double deltaX = (e.getSceneX() - lastMouseX) / scale;
            double deltaY = (e.getSceneY() - lastMouseY) / scale;

            if (xMultiplier != 0) {
                width += xMultiplier * deltaX;
                width = Math.max(width, getMinWidth());
            }
            if (yMultiplier != 0) {
                height += yMultiplier * deltaY;
                height = Math.max(height, getMinHeight());
            }

            lastMouseX = e.getSceneX();
            lastMouseY = e.getSceneY();

            Point2D canvasPoint = panel.sceneToLocal(e.getSceneX(), e.getSceneY());
            model.setMousePosition(canvasPoint.getX(), canvasPoint.getY());

            updateCanvasSize();
        });
    }

    private void updateCanvasSize() {
        double scale = model.getZoomFactor() / 100.0;
        setPrefSize(width * scale + handleSize, height * scale + handleSize);

        panel.setPrefWidth(width);
        panel.setPrefHeight(height);

        panel.getTransforms().clear();

        Point2D canvasPos = localToScene(handleSize, handleSize);
        model.setCanvasPosition(canvasPos.getX(), canvasPos.getY());
        model.setCanvasSize(width, height); // Use unscaled dimensions

        updateLayers();
        setUpPositions();
        scaleCanvas();
    }

    public void setUpPositions() {
        double w = panel.getWidth();
        double h = panel.getHeight();
        double halfHandle = handleSize / 2;

        setPosition(topLeftHandle, 0, 0);
        setPosition(topHandle, w / 2 + halfHandle, 0);
        setPosition(topRightHandle, w + handleSize, 0);
        setPosition(rightHandle, w + handleSize, h / 2 + halfHandle);
        setPosition(bottomRightHandle, w + handleSize, h + handleSize);
        setPosition(bottomHandle, w / 2 + halfHandle, h + handleSize);
        setPosition(bottomLeftHandle, 0, h + handleSize);
        setPosition(leftHandle, 0, h / 2 + halfHandle);

        panel.setLayoutX(handleSize);
        panel.setLayoutY(handleSize);
    }

    private void setPosition(Button handle, double x, double y) {
        handle.setLayoutX(x);
        handle.setLayoutY(y);
    }

    public void scaleCanvas() {
        double scale = model.getZoomFactor() / 100.0;

        panel.getTransforms().clear();
        panel.getTransforms().add(new Scale(scale, scale));

        double scaledWidth = panel.getWidth() * scale;
        double scaledHeight = panel.getHeight() * scale;
        double widthDiff = scaledWidth - panel.getWidth();
        double heightDiff = scaledHeight - panel.getHeight();

        setPrefSize(scaledWidth + handleSize * 2, scaledHeight + handleSize * 2);

        clearHandleTransforms();

        topHandle.getTransforms().add(new Translate(widthDiff/2, 0));
        bottomHandle.getTransforms().add(new Translate(widthDiff/2, heightDiff));

        leftHandle.getTransforms().add(new Translate(0, heightDiff/2));
        rightHandle.getTransforms().add(new Translate(widthDiff, heightDiff/2));

        topRightHandle.getTransforms().add(new Translate(widthDiff, 0));
        bottomLeftHandle.getTransforms().add(new Translate(0, heightDiff));
        bottomRightHandle.getTransforms().add(new Translate(widthDiff, heightDiff));

        updateLayers();
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
        for (PaintLayer layer: model.getLayers()) {
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