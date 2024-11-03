package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

import java.util.*;

public class LayerChooserPanel extends GridPane implements EventHandler<ActionEvent> {

    private View view;
    private PaintModel model;
    private Map<Integer, ImageView> buttonImages;

    private WritableImage createThumbnail(PaintLayer layer) {

//        if (layer.getVisible()) {
            return layer.snapshot(new SnapshotParameters(), null);
//        } else {
//            layer.setVisible(true);
//            WritableImage store = layer.snapshot(new SnapshotParameters(), null);
//            layer.setVisible(false);
//            return store;
//        }
//
//        Canvas tempCanvas = new Canvas(layer.getWidth(), layer.getHeight());
//        GraphicsContext g2d = tempCanvas.getGraphicsContext2D();
//
//        // thumbnail is independent of visibility
//        g2d.clearRect(0, 0, layer.getWidth(), layer.getHeight());
//        layer.display(g2d);
//
//        return tempCanvas.snapshot(new SnapshotParameters(), null);
    }

    public void updateAllLayers() {

        this.getChildren().clear();

        ArrayList<PaintLayer> layers = this.model.getLayers();
        buttonImages = new HashMap<>();
        for (int i = 0; i < layers.size(); i++) {
            // Snapshot the Canvas content (layer) as a list of thumbnails
//            if (layers.get(i).getStatus().equals("changed")) {  // only update when visible
                boolean store = layers.get(i).getVisible();
                layers.get(i).setVisible(true);
                WritableImage snapshot = createThumbnail(layers.get(i));

                // Create an ImageView to display the thumbnail and set the zoom size
                ImageView thumbnail = new ImageView(snapshot);
                thumbnail.setFitWidth(30);
                thumbnail.setFitHeight(30);
                thumbnail.setPreserveRatio(true);
                thumbnail.setId("Layer" + i);
                this.buttonImages.put(i, thumbnail);
                layers.get(i).setVisible(store);

//                if (this.buttonImages.size() > i) {
//                    this.buttonImages.replace(i, thumbnail);
//                } else {
//                    thumbnail.setId("Layer" + i);
//                    this.buttonImages.put(i, thumbnail);
//                }

                // Update status flag
//                layers.get(i).setStatus("unchanged");

//            } else if (layers.get(i).getStatus().equals("removed")) {
//                for (int layerIndex = i; layerIndex < layers.size(); layerIndex++) {
//                    this.buttonImages.replace(layerIndex, this.buttonImages.get(layerIndex + 1));
//                }
//                this.buttonImages.remove(layers.size());
//                layers.remove(layers.get(i));
//            }
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

            if (this.model.getSelectedLayer() == layers.get(i)) {
                button.setStyle("-fx-background-color: lightblue");
            }
            if (!layers.get(i).getVisible()) {
                if (this.model.getSelectedLayer() == layers.get(i)) {
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
        this.model = view.getPaintModel();
        this.buttonImages = new HashMap<>();
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
