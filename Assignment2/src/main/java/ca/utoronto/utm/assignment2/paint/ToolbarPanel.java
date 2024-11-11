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

/**
 * The main toolbar panel for the paint application.
 * This panel contains buttons for selecting tools, shapes, and colors,
 * and manages the associated popup menus for each button.
 */
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

    /**
     * Constructs a new ToolbarPanel with the specified paint model.
     * Initializes all toolbar buttons, popups, and their controllers.
     *
     * @param model The PaintModel that maintains the application state
     * @throws FileNotFoundException If any of the button icons cannot be loaded
     */
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

    /**
     * Initializes the toolbar by creating and arranging all button components.
     * For each button ID in BUTTON_IDS, this method:
     * 1. Creates the button content (icon and down arrow)
     * 2. Creates the toggle button
     * 3. Creates a container for the button and its label
     * 4. Adds the button to the toolbar with a separator between buttons
     *
     * The buttons are arranged horizontally with separators between them.
     * Each button is stored in both toolButtons and buttonContainers maps for later access.
     *
     * @throws FileNotFoundException If any button icon images cannot be loaded
     */
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

    /**
     * Creates the visual content for a toolbar button.
     * The content consists of:
     * - An icon loaded from IMAGE_FILES
     * - A down arrow indicator loaded from ARROW_IMAGE_FILE
     *
     * Both images are arranged vertically in a VBox with centered alignment
     * and appropriate spacing.
     *
     * @param index The index of the button/image in the BUTTON_IDS/IMAGE_FILES arrays
     * @return A VBox containing the button's icon and down arrow
     * @throws FileNotFoundException If either the main icon or arrow icon cannot be loaded
     */
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

    /**
     * Creates a styled ToggleButton with the specified ID and content.
     * The button is configured with:
     * - Transparent background
     * - Custom padding
     * - Hover effects
     * - Action event handler
     *
     * The button's appearance changes on hover and selection states through
     * setupButtonHoverEffects().
     *
     * @param buttonId The ID to assign to the button (from BUTTON_IDS)
     * @param content The VBox containing the button's visual content
     * @return A configured ToggleButton ready for use in the toolbar
     */
    private ToggleButton createToggleButton(String buttonId, VBox content) {
        ToggleButton button = new ToggleButton();
        button.setGraphic(content);
        button.setId(buttonId);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        button.setOnAction(toolbarController);

        setupButtonHoverEffects(button);

        return button;
    }

    /**
     * Configures hover and selection effects for a toolbar button.
     * When the mouse enters the button:
     * - Adds a light gray border
     * - Adjusts padding to maintain size
     *
     * When the mouse exits:
     * - Maintains border if button is selected
     * - Removes border if button is not selected
     * - Restores original padding
     *
     * @param button The ToggleButton to configure with hover effects
     */
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

    /**
     * Creates a container for a toolbar button and its label.
     * The container includes:
     * - The button itself
     * - A label with the button's ID as text
     * Both elements are arranged vertically with centered alignment.
     *
     * The container is styled with:
     * - Centered alignment
     * - Custom padding
     * - Spacing between button and label
     * - Styled label text
     *
     * @param button The ToggleButton to place in the container
     * @param buttonId The ID of the button, used as the label text
     * @return A VBox containing the button and its label
     */
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

    /**
     * Creates a vertical separator for use between toolbar buttons.
     * The separator is configured with:
     * - Vertical orientation
     * - Fixed width (1px)
     * - Fixed height (40px)
     * - Custom padding
     *
     * This creates a visual division between toolbar buttons while
     * maintaining consistent spacing.
     *
     * @return A configured Separator instance
     */
    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setPrefWidth(1);
        separator.setPrefHeight(40);
        separator.setPadding(new Insets(4, 0, 4, 3));
        return separator;
    }
}