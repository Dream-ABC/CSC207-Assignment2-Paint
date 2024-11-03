package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StatusbarPanel extends GridPane implements EventHandler<ActionEvent> {
    private PaintPanel paintPanel;

    public StatusbarPanel(PaintPanel paintPanel) throws FileNotFoundException {
        String[] buttonIds = {"zoomOut", "zoomIn"};
        ImageView[] buttonImages = new ImageView[buttonIds.length];
        String[] buttonImageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomOut.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomIn.png",
        };
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Cursor.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ObjectSize.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/CanvasSize.png",
        };

        this.paintPanel = paintPanel;
        this.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        int col = 0;
        for (int i = 0; i < imageFiles.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);

            this.add(imageView, col, 0);
            col++;

            TextArea textArea = new TextArea();
            textArea.setEditable(false);

            this.add(textArea, col, 0);
            col++;

            if (i < imageFiles.length) {
                Separator separator = new Separator();
                separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
                separator.setPrefWidth(1);
                separator.setPrefHeight(10);
                separator.setPadding(new Insets(4, 0, 4, 3));
                this.add(separator, col, 0);
                col++;
            }
        }
    }
    @Override
    public void handle(ActionEvent event) {
        for (Node node : this.getChildren()) {
            if (node instanceof VBox) {
                VBox container = (VBox) node;
                Button button = (Button) container.getChildren().getFirst();
                button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
            }
        }
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
    }
}
