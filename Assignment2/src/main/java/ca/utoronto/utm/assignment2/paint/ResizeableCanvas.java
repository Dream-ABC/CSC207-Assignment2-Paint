package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ResizeableCanvas extends StackPane implements EventHandler<ActionEvent> {
    PaintPanel panel;
    ResizingButton bottomRight;

    public ResizeableCanvas(PaintPanel panel) {
        super();

        this.panel = panel;
        BorderPane pane = new BorderPane();
        pane.setPrefSize(panel.getWidth()+20, panel.getHeight()+20);
        pane.setCenter(panel);

        Pane outside = new Pane();

        ResizingButton topLeft = new ResizingButton();
        ResizingButton bottomLeft = new ResizingButton();
        ResizingButton topRight = new ResizingButton();
        this.bottomRight = new ResizingButton();

//        bottomRight.setLayoutX(panel.getLayoutX()+panel.getWidth());
//        bottomRight.setLayoutY(panel.getLayoutY()+panel.getHeight());

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
        panel.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            topRight.setLayoutX(panel.getLayoutX() + newWidth.doubleValue());
            bottomRight.setLayoutY(panel.getLayoutX() + newWidth.doubleValue());
        });
        panel.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            bottomLeft.setLayoutY(panel.getLayoutY() + newHeight.doubleValue());
            bottomRight.setLayoutY(panel.getLayoutY() + newHeight.doubleValue());
        });

        outside.getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight);

        getChildren().addAll(outside, pane);


//        panel.getModel().addObserver(bottomRight);
//        bottomRight.setOnMousePressed(event -> {});
//        bottomRight.setOnMouseDragged(this);
//        bottomRight.setOnMouseReleased(this);
        panel.getModel().addObserver(bottomRight);
        bottomRight.setOnAction(this);
    }


//    @Override
//    public void handle(MouseEvent event) {
//        System.out.println("Starting resize");
//        double newWidth = event.getSceneX() - panel.getLayoutX();
//        double newHeight = event.getSceneY() - panel.getLayoutY();
//        panel.setWidth(newWidth);
//        panel.setHeight(newHeight);
//        bottomRight.setLayoutX(event.getSceneX());
//        bottomRight.setLayoutY(event.getSceneY());
//
////        topRight.setLayoutX(panel.getLayoutX() + newWidth.doubleValue());
////        bottomRight.setLayoutY(panel.getLayoutX() + newWidth.doubleValue());
////        bottomLeft.setLayoutY(panel.getLayoutY() + newHeight.doubleValue());
////        bottomRight.setLayoutY(panel.getLayoutY() + newHeight.doubleValue());
//
//
//        System.out.println("Resize complete. New size: " + panel.getWidth() + " x " + panel.getHeight());
//    }
    @Override
    public void handle(ActionEvent event) {
        System.out.println("bananas");
    }
}
