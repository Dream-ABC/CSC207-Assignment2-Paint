package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StatusbarPanel extends GridPane {
    private PaintPanel paintPanel;

    public StatusbarPanel(PaintPanel paintPanel) throws FileNotFoundException {
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Cursor.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ObjectSize.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/CanvasSize.png",
        };

        this.paintPanel = paintPanel;

        this.setStyle("-fx-background-color: #f8f1f0;");
        this.setPadding(new Insets(7, 0, 7, 10));

        int col = 0;
        for (int i = 0; i < imageFiles.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);

            this.add(imageView, col, 0);
            col++;

            TextField textField = new TextField();
            textField.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 12px;");
            textField.setPrefWidth(110);
            textField.setPrefHeight(12);
            textField.setEditable(false);
            textField.setText("1325 x 748px");

            this.add(textField, col, 0);
            col++;

            if (i < imageFiles.length - 1) {
                Separator separator = new Separator();
                separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
                separator.setPrefWidth(1);
                separator.setPrefHeight(10);
                separator.setPadding(new Insets(4, 4, 4, 0));
                this.add(separator, col, 0);
                col++;
            }
        }
    }
}
