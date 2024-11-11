package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import java.util.*;

/**
 * A class that provides a graphical user interface for managing layers.
 * LayerChooserPanel extends GridPane and implements the EventHandler interface
 * to handle action events triggered by buttons representing different layers.
 */
public class LayerChooserPanel extends GridPane implements EventHandler<ActionEvent> {
    private final View view;
    private final PaintModel model;
    private Map<Integer, ImageView> buttonImages;

    /**
     * Constructs a LayerChooserPanel and initializes it with the given View.
     *
     * @param view The View associated with this LayerChooserPanel.
     *             It provides access to the PaintModel and other necessary components.
     */
    public LayerChooserPanel(View view) {
        this.view = view;
        this.model = view.getPaintModel();
        this.buttonImages = new HashMap<>();
        updateAllLayers();
    }

    /**
     * Creates a thumbnail image representation of the given {@link PaintLayer}.
     * This method captures a snapshot of the layer's current state and generates
     * a resized thumbnail image that can be used for visual representation in the UI.
     *
     * @param layer the specific PaintLayer to create a thumbnail for
     * @return an ImageView containing the thumbnail of the layer
     */
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

    /**
     * Updates the visual representation of all layer buttons in the LayerChooserPanel.
     *
     * This method clears the current child nodes, retrieves the list of layers from the model
     * and creates a thumbnail image for each layer. It then creates a button for each layer,
     * setting the appropriate styles and event handlers based on the layer's visibility and
     * selection status. Additionally, it adds buttons for adding and removing layers.
     */
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

    /**
     * Handles the action event triggered by the buttons in the LayerChooserPanel.
     * This method resets the style for all buttons within the panel and updates
     * the layer based on the button clicked.
     *
     * @param event The action event triggered by clicking a button. The event source
     *              is expected to be a button which identifies the layer to be handled.
     */
    @Override
    public void handle(ActionEvent event) {
        for (Node node : this.getChildren()) {
            if (node instanceof Button button) {
                button.setStyle("");
            }
        }

        String command = ((Button) event.getSource()).getId();
        view.setLayer(command);
    }
}
