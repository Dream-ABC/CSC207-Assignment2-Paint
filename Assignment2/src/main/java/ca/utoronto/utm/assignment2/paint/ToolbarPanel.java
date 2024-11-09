package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ToolbarPanel extends GridPane implements EventHandler<ActionEvent> {

    public ToolbarPanel() throws FileNotFoundException {
        String[] buttonIds = {"Selection", "Image", "Tools", "Brushes", "Shapes", "Colours", "Layers"};
        ImageView[] buttonImages = new ImageView[buttonIds.length];
        String[] imageFiles = {
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/RectangularSelectionLarge.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Resize.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Tools.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/BrushIcon.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Shapes.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Colors.png",
                "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Layers.png"
        };
        String arrowImageFile = "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/DownArrowModifier.png";

        this.setStyle("-fx-background-color: #fcf8f7");

        int col = 0;
        for (int i = 0; i < buttonIds.length; i++) {
            FileInputStream input = new FileInputStream(imageFiles[i]);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            buttonImages[i] = imageView;

            FileInputStream inputArrow = new FileInputStream(arrowImageFile);
            Image imageArrow = new Image(inputArrow);
            ImageView downArrow = new ImageView(imageArrow);
            downArrow.setPreserveRatio(true);
            downArrow.setFitHeight(6);

            ImageView icon = buttonImages[i];

            VBox buttonContent = new VBox(2);
            buttonContent.setAlignment(Pos.CENTER);
            buttonContent.setSpacing(8);
            buttonContent.getChildren().addAll(icon, downArrow);

            ToggleButton button = new ToggleButton();
            button.setGraphic(buttonContent);
            button.setId(buttonIds[i]);
            button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");

            button.setOnAction(this);

            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
            });
            button.setOnMouseExited(e -> {
                if (button.isSelected()) {
                    button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
                } else {
                    button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
                }
            });

            Label label = new Label(buttonIds[i]);
            label.setStyle("-fx-font-size: 12px; -fx-text-fill: #666465; -fx-text-alignment: center;");
            label.setPadding(new Insets(0, 11, 0, 11));

            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(14, 0, 6, 0));
            container.setSpacing(10);
            container.getChildren().addAll(button, label);

            this.add(container, col, 0);
            col++;

            if (i < buttonIds.length - 1) {
                Separator separator = new Separator();
                separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
                separator.setPrefWidth(1);
                separator.setPrefHeight(40);
                separator.setPadding(new Insets(4, 0, 4, 3));
                this.add(separator, col, 0);
                col++;
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
        for (Node node : this.getChildren()) {
            if (node instanceof VBox container) {
                ToggleButton button = (ToggleButton) container.getChildren().getFirst();
                button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
            }
        }
        ToggleButton button = (ToggleButton) event.getSource();
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
    }
}


