package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SuppressWarnings("unchecked")
public class ZoomPanel extends GridPane implements EventHandler<ActionEvent> {

    public ZoomPanel() throws FileNotFoundException {
        String[] buttonIds = {"ZoomOut", "ZoomIn"};
        ImageView[] buttonImages = new ImageView[buttonIds.length];
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomOut.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/ZoomIn.png"
        };

        this.setAlignment(Pos.BASELINE_RIGHT);
        this.setStyle("-fx-background-color: #f8f1f0;");
        this.setPadding(new Insets(7, 4, 7, 10));

        for (int i = 0; i < buttonIds.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            buttonImages[i] = imageView;
        }

        int col = 0;

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("12.5%", "25%", "50%", "75%", "100%", "200%", "300%", "400%", "500%", "600%", "700%", "800%");
        comboBox.setPromptText("30%");
        comboBox.setPrefHeight(28);
        comboBox.setPrefWidth(90);
        this.add(comboBox, col, 0);
        col++;

        Button ZoomOut = new Button();
        ZoomOut.setGraphic(buttonImages[0]);
        ZoomOut.setStyle("-fx-background-color: #f8f1f0;");
        ZoomOut.setPadding(new Insets(5, 8, 5, 16));
        this.add(ZoomOut, col, 0);
        col++;

        Slider ZoomSlider = new Slider();
        ZoomSlider.setMin(12);
        ZoomSlider.setMax(800);
        ZoomSlider.setBlockIncrement(1);
        ZoomSlider.setPrefWidth(140);
        this.add(ZoomSlider, col, 0);
        col++;

        Button ZoomIn = new Button();
        ZoomIn.setGraphic(buttonImages[1]);
        ZoomIn.setStyle("-fx-background-color: #f8f1f0;");
        ZoomIn.setPadding(new Insets(5, 8, 5, 8));
        this.add(ZoomIn, col, 0);
    }

    @Override
    public void handle(ActionEvent event) {}
}
