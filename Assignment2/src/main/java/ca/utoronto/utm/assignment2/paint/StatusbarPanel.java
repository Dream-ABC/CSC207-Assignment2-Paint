package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

public class StatusbarPanel extends GridPane implements Observer {
    private TextField[] fields;

    public StatusbarPanel() throws FileNotFoundException {
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Cursor.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ObjectSize.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/CanvasSize.png",
        };
        String[] defaultText = {"", "", "700 x 400px"};

        setStyle("-fx-background-color: #f8f1f0;");
        setPadding(new Insets(7, 0, 7, 10));

        this.fields = new TextField[imageFiles.length];

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
            textField.setText(defaultText[i]);
            fields[i] = textField;

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

    @Override
    public void update(Observable o, Object arg) {
        PaintModel model = (PaintModel) o;
        int mouseX = (int) model.getMouseX();
        int mouseY = (int) model.getMouseY();
        int canvasWidth = (int) model.getCanvasWidth();
        int canvasHeight = (int) model.getCanvasHeight();

        if (0 <= mouseX && mouseX <= canvasWidth && 0 <= mouseY && mouseY <= canvasHeight) {
            fields[0].setText(mouseX + " x " + mouseY + "px");
        } else {
            fields[0].setText("");
        }
        fields[1].setText("");
        fields[2].setText(canvasWidth + " x " + canvasHeight + "px");
    }
}
