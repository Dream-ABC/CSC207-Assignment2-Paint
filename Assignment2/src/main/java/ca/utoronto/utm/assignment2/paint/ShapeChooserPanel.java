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

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;

        public ShapeChooserPanel(View view) throws FileNotFoundException {
                this.view = view;

                String[] buttonIds = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Oval"};
                ImageView[] buttonImages = new ImageView[buttonIds.length];
                String[] imageFiles = {"src/main/java/ca/utoronto/utm/assignment2/images/circle.png",
                        "src/main/java/ca/utoronto/utm/assignment2/images/rectangle.png",
                        "src/main/java/ca/utoronto/utm/assignment2/images/square.png",
                        "src/main/java/ca/utoronto/utm/assignment2/images/squiggle.png",
                        "src/main/java/ca/utoronto/utm/assignment2/images/polyline.png",
                        "src/main/java/ca/utoronto/utm/assignment2/images/circle.png"};

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
                for (Node node: this.getChildren()) {
                        if (node instanceof Button) {
                                Button button = (Button) node;
                                button.setStyle("");
                        }
                }
                ((Button) event.getSource()).setStyle("-fx-background-color: lightblue");
                String command = ((Button) event.getSource()).getId();
                view.setMode(command);
                System.out.println(command);
        }
}


