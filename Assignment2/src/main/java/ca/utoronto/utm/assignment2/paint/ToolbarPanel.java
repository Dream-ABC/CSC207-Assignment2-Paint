package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ToolbarPanel extends GridPane {
    private final Map<String, ToggleButton> toolButtons;
    private final Map<String, VBox> buttonContainers;
    private ShapeChooserPopUp shapeChooserPopUp;
    private ToolChooserPopUp toolChooserPopUp;
    private ToolbarController toolbarController;
    private ColorChooserPopup colorChooserPopup;


    // Constants
    private static final String[] BUTTON_IDS = {
            "Selection", "Tools", "Shapes", "Colours",
    };

    private static final String[] IMAGE_FILES = {
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/RectangularSelectionLarge.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Tools.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Shapes.png",
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/Colors.png",
    };

    private static final String ARROW_IMAGE_FILE =
            "src/main/java/ca/utoronto/utm/assignment2/Assets/theme-light/DownArrowModifier.png";

    public ToolbarPanel(PaintModel model) throws FileNotFoundException {
        this.toolButtons = new HashMap<>();
        this.buttonContainers = new HashMap<>();
        this.shapeChooserPopUp = new ShapeChooserPopUp(model);
        this.toolChooserPopUp = new ToolChooserPopUp(model);
        this.colorChooserPopup = new ColorChooserPopup(model);
        this.toolbarController = new ToolbarController(model, toolButtons, toolChooserPopUp ,shapeChooserPopUp, colorChooserPopup);

        this.setStyle("-fx-background-color: #fcf8f7");
        initializeToolbar();
    }

    private void initializeToolbar() throws FileNotFoundException {
        int col = 0;
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            String buttonId = BUTTON_IDS[i];

            VBox buttonContent = createButtonContent(i);

            ToggleButton button = createToggleButton(buttonId, buttonContent);
            toolButtons.put(buttonId, button);

            VBox container = createButtonContainer(button, buttonId);
            buttonContainers.put(buttonId, container);

            this.add(container, col++, 0);

            if (i < BUTTON_IDS.length - 1) {
                this.add(createSeparator(), col++, 0);
            }
        }
    }

    private VBox createButtonContent(int index) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(IMAGE_FILES[index]);
        ImageView icon = new ImageView(new Image(input));

        FileInputStream arrowInput = new FileInputStream(ARROW_IMAGE_FILE);
        ImageView downArrow = new ImageView(new Image(arrowInput));
        downArrow.setPreserveRatio(true);
        downArrow.setFitHeight(6);

        VBox buttonContent = new VBox(2);
        buttonContent.setAlignment(Pos.CENTER);
        buttonContent.setSpacing(8);
        buttonContent.getChildren().addAll(icon, downArrow);
        return buttonContent;
    }

    private ToggleButton createToggleButton(String buttonId, VBox content) {
        ToggleButton button = new ToggleButton();
        button.setGraphic(content);
        button.setId(buttonId);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        button.setOnAction(toolbarController);

        setupButtonHoverEffects(button);

        return button;
    }

    private void setupButtonHoverEffects(ToggleButton button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; " +
                    "-fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
        });

        button.setOnMouseExited(e -> {
            if (button.isSelected()) {
                button.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; -fx-border-color: lightgray; -fx-padding: 9;");
            } else {
                button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
            }
        });
    }

    private VBox createButtonContainer(ToggleButton button, String buttonId) {
        Label label = new Label(buttonId);
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: #666465; -fx-text-alignment: center;");
        label.setPadding(new Insets(0, 11, 0, 11));

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(14, 0, 6, 0));
        container.setSpacing(10);
        container.getChildren().addAll(button, label);

        return container;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setPrefWidth(1);
        separator.setPrefHeight(40);
        separator.setPadding(new Insets(4, 0, 4, 3));
        return separator;
    }

    public ToggleButton getToolButton(String buttonId) {
        return toolButtons.get(buttonId);
    }

    public VBox getButtonContainer(String buttonId) {
        return buttonContainers.get(buttonId);
    }

}