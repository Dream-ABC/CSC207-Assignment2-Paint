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

/**
 * A panel that displays status information in the paint application, including cursor position,
 * object size, and canvas dimensions. Implements Observer to update display based on model changes.
 */
public class StatusbarPanel extends GridPane implements Observer {
    private TextField[] fields;

    /**
     * Constructs a new StatusbarPanel with three information fields:
     * cursor position, object size, and canvas dimensions.
     * Initializes the panel with icons and text fields in a grid layout.
     *
     * @throws FileNotFoundException if any of the required icon image files cannot be found
     */
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

    /**
     * Updates the status bar information when the paint model changes.
     * Displays current mouse coordinates when cursor is within canvas bounds
     * and updates the canvas dimensions display.
     *
     * @param o the Observable object (expected to be a PaintModel)
     * @param arg the argument passed to the notifyObservers method (not used)
     */
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
