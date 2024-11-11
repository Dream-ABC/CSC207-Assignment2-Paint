package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Paint class is the entry point for the Paint application.
 * It initializes the {@link PaintModel} and sets up the {@link View},
 * launching the graphical user interface.
 */
public class Paint extends Application {

        PaintModel model; // Model
        View view; // View + Controller

        /**
         * The main method that launches the Paint application.
         *
         * @param args the command-line arguments
         */
        public static void main(String[] args) {
                launch(args);
        }

        /**
         * Initializes the application by creating the PaintModel and setting up the View.
         *
         * @param stage the primary stage for this application
         * @throws Exception if there is an error during application startup
         */
        @Override
        public void start(Stage stage) throws Exception {

                this.model = new PaintModel();

                // View + Controller
                this.view = new View(model, stage);
        }
}
