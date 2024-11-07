package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;

import java.util.*;


public class LayerChooserPanel extends GridPane implements EventHandler<ActionEvent> {
    private View view;
    private PaintModel model;
    private Map<Integer, ImageView> buttonImages;

    public LayerChooserPanel(View view) {
        this.view = view;
        this.model = view.getPaintModel();
        this.buttonImages = new HashMap<>();
        updateAllLayers();
    }

    private ImageView createThumbnail(PaintLayer layer) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITE);

        WritableImage snapshot;
        boolean store = layer.isVisible();
        layer.setVisible(true);
        if (layer.getWidth() < 100) {layer.setWidth(100);}
        if (layer.getHeight() < 100) {layer.setHeight(100);}
        GraphicsContext g2d = layer.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        layer.display(g2d);
        snapshot = layer.snapshot(params, null);

        ImageView thumbnail = new ImageView(snapshot);
        thumbnail.setFitWidth(30);
        thumbnail.setFitHeight(30);
        thumbnail.setPreserveRatio(true);

        layer.setVisible(store);
        return thumbnail;
    }

    public void updateAllLayers() {

        this.getChildren().clear();

        ArrayList<PaintLayer> layers = this.model.getLayers();
        buttonImages = new HashMap<>();
        for (int i = 0; i < layers.size(); i++) {
            ImageView thumbnail = createThumbnail(layers.get(i));
            thumbnail.setId("Layer" + i);
            this.buttonImages.put(i, thumbnail);
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
            if (!layers.get(i).isVisible()) {
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
