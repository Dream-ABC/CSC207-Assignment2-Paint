package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;      // dont know yet
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

    private final PaintModel model;

    public ShapeChooserPanel(PaintModel model) throws FileNotFoundException {
        this.model = model;

        String[] buttonIds = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Oval", "Triangle", "Stroke Eraser", "Outline", "Solid", "Selection Tool", "Text", "Precision Eraser"};
        ImageView[] buttonImages = new ImageView[buttonIds.length];
        String[] imageFiles = {"src/main/java/ca/utoronto/utm/assignment2/images/circle.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/rectangle.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/square.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/squiggle.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/polyline.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/oval.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/triangle.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/eraser.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/outline.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/solid.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/RectangularSelectionLarge.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/text.png",
                "src/main/java/ca/utoronto/utm/assignment2/images/eraser.png"};

        for (int i = 0; i < buttonIds.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            buttonImages[i] = imageView;
        }

        int row = 0;
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = new Button();
            button.setId(buttonIds[i]);
            button.setMinWidth(100);
            this.add(button, 0, row);
            row++;
            button.setOnAction(this);
            button.setGraphic(buttonImages[i]);
        }
    }

    @Override
    public void handle(ActionEvent event) {
        this.model.removeSelectionTool();

        Shape shape = model.getSelectedShape();
        if (Objects.equals(model.getMode(), "Polyline") && shape != null) {
            Polyline polyline = (Polyline) shape;
            if (polyline.getSize() > 1) {  // Only close if there's more than one point
                polyline.setClosed(true);
                // Remove the current shape from the model and add it as final
                Shape currentShape = this.model.getSelectedLayer().getShapes().getLast();
                this.model.getSelectedLayer().removeShape(currentShape);
                this.model.addShapeFinal(polyline);
            }
        }

        for (Node node: this.getChildren()) {
            Button button = (Button) node;
            button.setStyle("");
        }
        ((Button) event.getSource()).setStyle("-fx-background-color: lightblue");
        String command = ((Button) event.getSource()).getId();
        if (command.equals("Outline") || command.equals("Solid")) {
            model.setFillStyle(command);
        }
        else{
            model.setMode(command);
        }
    }
}


