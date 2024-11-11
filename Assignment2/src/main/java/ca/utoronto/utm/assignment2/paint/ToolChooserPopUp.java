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

/**
 * A popup component that provides a selection of drawing tools in the paint application.
 * This class manages a popup window containing various drawing tools such as Squiggle,
 * Stroke Eraser, Precision Eraser, and Text tools.
 */
public class ToolChooserPopUp extends VBox implements EventHandler<ActionEvent> {
    private final PaintModel model;
    private final Popup toolPopup;

    private static final String[] TOOL_IDS = {
            "Squiggle", "Stroke Eraser", "Precision Eraser", "Text",
    };

    private static final String[] IMAGE_FILES = {
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/PencilTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/StrokeEraserTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/PrecisionEraserTool.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/TextTool.png",
    };

    /**
     * Constructs a new ToolChooserPopUp with the specified paint model.
     *
     * @param model The PaintModel that maintains the application state
     * @throws FileNotFoundException If any of the tool icons cannot be loaded
     */
    public ToolChooserPopUp(PaintModel model) throws FileNotFoundException {
        this.model = model;
        this.toolPopup = createToolPopup();
    }

    /**
     * Toggles the visibility of the popup menu.
     * If the popup is currently visible, it will be hidden.
     * If it's hidden, it will be shown anchored to the specified button.
     *
     * @param sourceButton The ToggleButton that triggered the popup
     */
    public void togglePopup(ToggleButton sourceButton) {
        if (!hidePopup()) {
            toolPopup.show(sourceButton,
                    sourceButton.localToScreen(0, 0).getX(),
                    sourceButton.localToScreen(0, 0).getY() + sourceButton.getHeight());
        }
    }

    /**
     * Hides the popup if it is currently visible.
     *
     * @return true if the popup was visible and is now hidden, false if it was already hidden
     */
    public boolean hidePopup() {
        if (toolPopup.isShowing()) {
            toolPopup.hide();
            return true;
        }
        return false;
    }

    /**
     * Creates and configures a popup window containing drawing tools.
     * The popup includes a grid of tool buttons and a label, styled with a white background
     * and drop shadow effect.
     *
     * @return A configured Popup containing the tool selection interface
     * @throws FileNotFoundException if any of the tool icon image files cannot be found
     */
    private Popup createToolPopup() throws FileNotFoundException {
        Popup popup = new Popup();

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: white; -fx-border-color: lightgray; " +
                "-fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);" +
                "-fx-border-radius: 3px;");

        FlowPane shapesPane = new FlowPane(5, 5);
        shapesPane.setStyle("-fx-border-color: lightgray; -fx-border-radius: 3px;");
        shapesPane.setPadding(new Insets(5));
        shapesPane.setPrefWrapLength(175);

        for (int i = 0; i < TOOL_IDS.length; i++) {
            Button shapeButton = createToolButton(TOOL_IDS[i], IMAGE_FILES[i]);
            shapesPane.getChildren().add(shapeButton);
        }

        Label label = new Label("Tools");
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: #666465; -fx-text-alignment: center;");
        label.setPadding(new Insets(2, 11, 2, 11));

        container.getChildren().addAll(shapesPane, label);

        popup.getContent().add(container);
        return popup;
    }

    /**
     * Creates and configures a tool button with an icon image.
     * The button is styled transparently with hover effects and connected to the action handler.
     *
     * @param id The identifier for the tool button, used to determine its function
     * @param imageFile The file path to the icon image to be displayed on the button
     * @return A configured Button with the specified icon and behavior
     * @throws FileNotFoundException if the specified image file cannot be found
     */
    private Button createToolButton(String id, String imageFile) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(imageFile);
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
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
     * Handles tool selection events when a tool button is clicked.
     * Updates the model's mode to the selected tool and hides the tool popup.
     *
     * @param event The ActionEvent triggered by clicking a tool button
     */
    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String command = clickedButton.getId();

        this.model.removeSelectionTool();

        model.setMode(command);
        toolPopup.hide();
    }
}
