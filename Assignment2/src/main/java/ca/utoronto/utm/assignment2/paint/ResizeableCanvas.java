package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ResizeableCanvas extends StackPane {
    PaintPanel panel;

    public ResizeableCanvas(PaintPanel panel) {
        super();

        this.panel = panel;
        BorderPane pane = new BorderPane();
        pane.setPrefSize(panel.getWidth()+20, panel.getHeight()+20);
        pane.setCenter(panel);

        VBox left = new VBox();
        VBox right = new VBox();

        Pane outside = new Pane();

        ResizingButton topLeft = new ResizingButton();
        ResizingButton bottomLeft = new ResizingButton();
        ResizingButton topRight = new ResizingButton();
        ResizingButton bottomRight = new ResizingButton();


        panel.layoutXProperty().addListener((obs, oldX, newX) -> {
                topLeft.setLayoutX(newX.doubleValue()-10);
                bottomLeft.setLayoutX(newX.doubleValue()-10);
                topRight.setLayoutX(newX.doubleValue()+panel.getWidth());
                bottomRight.setLayoutX(newX.doubleValue()+panel.getWidth());
            });
        panel.layoutYProperty().addListener((obs, oldY, newY) -> {
            topLeft.setLayoutY(newY.doubleValue()-10);
            bottomLeft.setLayoutY(newY.doubleValue()+panel.getHeight());
            topRight.setLayoutY(newY.doubleValue()-10);
            bottomRight.setLayoutY(newY.doubleValue()+panel.getHeight());
        });

        outside.getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight);

        getChildren().addAll(outside, pane);


        addResizingActions(topLeft, bottomLeft, topRight, bottomRight, panel);
    }

    private void addResizingActions(ResizingButton topLeft, ResizingButton bottomLeft, ResizingButton topRight, ResizingButton bottomRight, PaintPanel panel) {
        panel.getModel().addObserver(bottomRight);

        bottomRight.setOnMousePressed(event -> handleResizeStart(event, bottomRight));
        bottomRight.setOnMouseDragged(event -> handleResize(event, panel, bottomRight));
        bottomRight.setOnMouseReleased(event -> handleResizeEnd(panel));
    }
    private void handleResizeStart(MouseEvent event, ResizingButton button) {
        System.out.println("Starting resize with " + button);
    }

    private void handleResize(MouseEvent event, PaintPanel panel, ResizingButton button) {
        double newWidth = event.getSceneX() - panel.getLayoutX();
        double newHeight = event.getSceneY() - panel.getLayoutY();

        panel.setWidth(newWidth);
        panel.setHeight(newHeight);
    }

    private void handleResizeEnd(PaintPanel panel) {
        System.out.println("Resize complete. New size: " + panel.getWidth() + " x " + panel.getHeight());
    }

    public void handle(MouseEvent event) {
        System.out.println("Starting resize");
        double newWidth = event.getSceneX() - panel.getLayoutX();
        double newHeight = event.getSceneY() - panel.getLayoutY();
        panel.setWidth(newWidth);
        panel.setHeight(newHeight);
        System.out.println("Resize complete. New size: " + panel.getWidth() + " x " + panel.getHeight());
    }
}
