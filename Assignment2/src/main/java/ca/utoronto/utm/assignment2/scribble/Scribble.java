package ca.utoronto.utm.assignment2.scribble;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Scribble extends Application {

        @Override
        public void start(Stage stage) {

            //  Canvas canvas = new Canvas(200,200);
                ScribblePanel scribblePanel = new ScribblePanel();
                HBox root = new HBox(); // LAYOUT
                root.setPadding(new Insets(5));
                root.getChildren().add(scribblePanel);

                Scene scene = new Scene(root); // SCENE

                stage.setTitle("Scribble");
                stage.setScene(scene);
                stage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
