package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class LayerChooserPanel extends GridPane implements EventHandler<ActionEvent> {

    private View view;
    private ArrayList<ImageView> buttonImages;

    public void updateAllLayers() {

        this.getChildren().clear();

        ArrayList<PaintLayer> layers = view.getPaintModel().getLayers();
        for (int i = 0; i < layers.size(); i++) {
            // Snapshot the Canvas content (layer) as a list of thumbnails
            if (layers.get(i).getLastVisible()) {  // only update when visible
                WritableImage snapshot = layers.get(i).snapshot(new SnapshotParameters(), null);

                // Create an ImageView to display the thumbnail and set the zoom size
                ImageView thumbnail = new ImageView(snapshot);
                thumbnail.setFitWidth(30);  // ? adjust according to the canvas's size
                thumbnail.setFitHeight(30);
                thumbnail.setPreserveRatio(true);
                if (this.buttonImages.size() > i) {
                    this.buttonImages.set(i, thumbnail);
                } else {
                    this.buttonImages.add(thumbnail);
                }
            }
        }

        int row = 0;
        for (int i = 0; i < layers.size(); i++) {
            Button button = new Button();
            button.setId("Layer" + i);
            button.setMinWidth(100);
            this.add(button, 0, row);
            row++;
            button.setOnAction(this);
            button.setGraphic(this.buttonImages.get(i));

            if (view.getPaintModel().getSelectedLayer() == layers.get(i)) {
                button.setStyle("-fx-background-color: lightblue");
            }
            if (!layers.get(i).getVisible()) {
                if (view.getPaintModel().getSelectedLayer() == layers.get(i)){
                    button.setStyle("-fx-background-color: blue");
                } else {
                    button.setStyle("-fx-background-color: lightgray");
                }
            }
        }

        // Button for adding layer
        Button button = new Button();
        button.setId("Layer+");
        button.setMinWidth(100);
        this.add(button, 0, row);
        row++;
        button.setOnAction(this);
        button.setText("+");

        // Button for removing layer
        button = new Button();
        button.setId("Layer-");
        button.setMinWidth(100);
        this.add(button, 0, row);
        button.setOnAction(this);
        button.setText("-");
    }

    public LayerChooserPanel(View view) {
        this.view = view;
        this.buttonImages = new ArrayList<>();

        updateAllLayers();
    }

    @Override
    public void handle(ActionEvent event) {
        for (Node node : this.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setStyle("");
            }
        }

        String command = ((Button) event.getSource()).getId();
        view.setLayer(command);
        System.out.println(command);
    }
}
