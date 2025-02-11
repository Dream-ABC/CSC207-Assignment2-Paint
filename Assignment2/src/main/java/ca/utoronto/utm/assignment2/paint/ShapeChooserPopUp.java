package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * A popup component that allows users to choose different shapes for drawing.
 * Manages the display and interaction with shape selection buttons.
 */
public class ShapeChooserPopUp extends VBox implements EventHandler<ActionEvent> {
    private final PaintModel model;
    private final Popup shapePopup;

    private static final String[] SHAPE_IDS = {
            "Circle", "Oval", "Square", "Rectangle", "Polyline", "Triangle",
    };

    private static final String[] IMAGE_FILES = {
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/CircleTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/OvalTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/SquareTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/RectangleTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/PolygonTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/TriangleTool.png",
    };

    /**
     * Constructs a new ShapeChooserPopUp with the specified paint model.
     *
     * @param model the PaintModel to be updated when shapes are selected
     * @throws FileNotFoundException if any of the shape icon image files cannot be found
     */
    public ShapeChooserPopUp(PaintModel model) throws FileNotFoundException {
        this.model = model;
        this.shapePopup = createShapePopup();
    }

    /**
     * Toggles the visibility of the shape chooser popup.
     * If the popup is hidden, displays it below the source button.
     * If visible, hides it.
     *
     * @param sourceButton the ToggleButton that triggered the popup
     */
    public void togglePopup(ToggleButton sourceButton) {
        if (!hidePopup()) {
            shapePopup.show(sourceButton,
                    sourceButton.localToScreen(0, 0).getX(),
                    sourceButton.localToScreen(0, 0).getY() + sourceButton.getHeight());
        }
    }

    /**
     * Attempts to hide the shape chooser popup if it is currently showing.
     *
     * @return true if the popup was showing and was hidden,
     *         false if the popup was already hidden
     */
    public boolean hidePopup() {
        if (shapePopup.isShowing()) {
            shapePopup.hide();
            return true;
        }
        return false;
    }

    /**
     * Creates and configures the shape chooser popup window.
     * Sets up a container with a grid of shape buttons and a label.
     *
     * @return a configured Popup containing the shape selection interface
     * @throws FileNotFoundException if any shape icon image files cannot be found
     */
    private Popup createShapePopup() throws FileNotFoundException {
        Popup popup = new Popup();

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: white; -fx-border-color: lightgray; " +
                "-fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);" +
                "-fx-border-radius: 3px;");

        FlowPane shapesPane = new FlowPane(5, 5);
        shapesPane.setStyle("-fx-border-color: lightgray; -fx-border-radius: 3px;");
        shapesPane.setPadding(new Insets(5));
        shapesPane.setPrefWrapLength(265);

        for (int i = 0; i < SHAPE_IDS.length; i++) {
            Button shapeButton = createShapeButton(SHAPE_IDS[i], IMAGE_FILES[i]);
            shapesPane.getChildren().add(shapeButton);
        }

        Label label = new Label("Shapes");
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: #666465; -fx-text-alignment: center;");
        label.setPadding(new Insets(2, 11, 2, 11));

        container.getChildren().addAll(shapesPane, label);

        popup.getContent().add(container);
        return popup;
    }

    /**
     * Creates and configures a button for a specific shape type.
     * The button includes an icon and hover effects.
     *
     * @param id the identifier for the shape type
     * @param imageFile the path to the icon image file
     * @return a configured Button with the specified shape icon
     * @throws FileNotFoundException if the specified image file cannot be found
     */
    private Button createShapeButton(String id, String imageFile) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(imageFile);
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        imageView.setPreserveRatio(true);

        Button button = new Button();
        button.setGraphic(imageView);
        button.setId(id);
        button.setPrefSize(40, 40);
        button.setStyle("-fx-background-color: transparent;");

        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color: #f0f0f0;")
        );
        button.setOnMouseExited(e ->
                button.setStyle("-fx-background-color: transparent;")
        );

        button.setOnAction(this);

        return button;
    }

    /**
     * Handles shape selection events when a shape button is clicked.
     * Updates the model's mode to the selected shape type and hides the popup.
     *
     * @param event the ActionEvent triggered by clicking a shape button
     */
    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String command = clickedButton.getId();

        this.model.removeSelectionTool();

        if (command.equals("Polyline")) {
            handlePolylineClose();
        }

        model.setMode(command);
        shapePopup.hide();
    }

    /**
     * Handles the closing of a polyline shape when switching to a different shape.
     * If the current shape is a polyline with more than one point,
     * closes the polyline and adds it to the model.
     */
    private void handlePolylineClose() {
        Shape shape = model.getSelectedShape();
        if (Objects.equals(model.getMode(), "Polyline") && shape != null) {
            Polyline polyline = (Polyline) shape;
            if (polyline.getSize() > 1) {
                polyline.setClosed(true);
                Shape currentShape = this.model.getSelectedLayer().getShapes().getLast();
                this.model.getSelectedLayer().removeShape(currentShape);
                this.model.addShapeFinal(polyline);
            }
        }
    }
}