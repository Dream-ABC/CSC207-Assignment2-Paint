package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
import java.util.List;

/**
 * The TextEditorDialog class is responsible for creating and displaying a dialog
 * that allows users to edit text properties such as font type, size, and style
 * (bold, italic, underline, and strikethrough). The dialog is integrated with
 * a PaintPanel object, enabling real-time updates to the displayed text.
 */
public class TextEditorDialog {

    private final Dialog<Void> dialog;
    private final PaintPanel paintPanel;
    private final Text displayedText;

    private TextField textField;
    private ComboBox fontChooser;
    private ComboBox sizeChooser;
    private ToggleButton boldButton;
    private ToggleButton italicButton;
    private ToggleButton underlineButton;
    private ToggleButton strikethroughButton;

    /**
     * Creates a new TextEditorDialog for editing a given Text object.
     *
     * @param paintPanel    a panel that manages graphical elements and their settings
     * @param displayedText the Text object that will be edited within the dialog
     */
    public TextEditorDialog(PaintPanel paintPanel, Text displayedText) {
        this.paintPanel = paintPanel;
        this.displayedText = displayedText;
        this.displayedText.setColor(this.paintPanel.getColor());

        this.dialog = new Dialog<>();
        // close dialog by "X" button or "Enter" button
        Window window = this.dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        this.dialog.getDialogPane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                window.hide();
            }
        });
    }

    /**
     * Determines if a specific font supports both bold and italic styles.
     *
     * @param fontName the name of the font to be checked for bold and italic support
     * @return returns true if the font supports both bold and italic styles, false otherwise
     */
    private boolean supportsBoldAndItalic(String fontName) {
        Font boldItalicFont = Font.font(fontName, FontWeight.BOLD, FontPosture.ITALIC, 12);

        return boldItalicFont.getStyle().contains("Bold") && boldItalicFont.getStyle().contains("Italic");
    }

    /**
     * Updates the font of the displayed text to the font currently selected by the user
     * in the font chooser elements and notifies the paint panel model of the change.
     */
    private void updateFont() {
        this.displayedText.setFont(this.getSelectedFont());
        this.paintPanel.getModel().notifyChange();
    }

    /**
     * Initializes the font chooser, size chooser, font style buttons (bold, italic, underline, strikethrough),
     * and the text field. Sets up event handlers for each component of UI to update the displayed text and notify
     * the changes whenever a user interaction occurs.
     */
    private void setBoxes() {
        // font chooser menu
        this.fontChooser = new ComboBox<>();

        // get all fonts that supports bold and italic
        List<String> supportedFonts = Font.getFamilies().stream()
                .filter(this::supportsBoldAndItalic)
                .toList();

        this.fontChooser.getItems().addAll(supportedFonts);
        this.fontChooser.setValue("Arial");  // default font
        this.fontChooser.setOnAction(e -> {
            this.updateFont();
        });

        // size chooser menu
        this.sizeChooser = new ComboBox<>();
        this.sizeChooser.getItems().addAll("8", "9", "10", "11", "12", "14", "16", "18", "20",
                "22", "24", "26", "28", "36", "48", "72");
        this.sizeChooser.setValue("12");  // default size
        this.sizeChooser.setOnAction(e -> {
            this.updateFont();
        });

        // font style buttons
        this.boldButton = new ToggleButton();
        Image image = new Image("file:src/main/java/ca/utoronto/utm/assignment2/images/bold.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        imageView.setPreserveRatio(true);
        boldButton.setGraphic(imageView);
        boldButton.setPrefWidth(20);
        boldButton.setPrefHeight(20);
        boldButton.setOnAction(e -> {
            this.updateFont();
        });

        this.italicButton = new ToggleButton();
        image = new Image("file:src/main/java/ca/utoronto/utm/assignment2/images/italic.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(13);
        imageView.setFitWidth(13);
        imageView.setPreserveRatio(true);
        italicButton.setGraphic(imageView);
        italicButton.setPrefWidth(20);
        italicButton.setPrefHeight(20);
        italicButton.setOnAction(e -> {
            this.updateFont();
        });

        this.underlineButton = new ToggleButton();
        image = new Image("file:src/main/java/ca/utoronto/utm/assignment2/images/underline.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setPreserveRatio(true);
        underlineButton.setGraphic(imageView);
        underlineButton.setPrefWidth(20);
        underlineButton.setPrefHeight(20);
        underlineButton.setOnAction(e -> {
            this.displayedText.setUnderline(this.underlineButton.isSelected());
            this.paintPanel.getModel().notifyChange();
        });

        this.strikethroughButton = new ToggleButton();
        image = new Image("file:src/main/java/ca/utoronto/utm/assignment2/images/strikethrough.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        imageView.setPreserveRatio(true);
        strikethroughButton.setGraphic(imageView);
        strikethroughButton.setPrefWidth(20);
        strikethroughButton.setPrefHeight(20);
        strikethroughButton.setOnAction(e -> {
            this.displayedText.setStrikethrough(this.strikethroughButton.isSelected());
            this.paintPanel.getModel().notifyChange();
        });

        // text field and listener
        this.textField = new TextField();
        textField.setOnKeyTyped(event -> {
            String userInput = textField.getText();
            this.displayedText.setText(userInput);
            this.paintPanel.getModel().notifyChange();
        });
    }

    /**
     * Retrieves the currently selected font based on the user's choices.
     *
     * @return the Font object that represents the selected font, weight, posture, and size
     */
    public Font getSelectedFont() {
        FontWeight weight = boldButton.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = italicButton.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;

        return Font.font((String) this.fontChooser.getValue(), weight, posture,
                Integer.parseInt((String) this.sizeChooser.getValue()));
    }

    /**
     * Displays the text editor dialog, initializing all components and laying them
     * out in the dialog window. The method sets up the font chooser, size chooser,
     * style buttons (bold, italic, underline, strikethrough), and the text field,
     * placing them in appropriate layout containers. It then displays the dialog
     * to the user and waits for user interaction.
     */
    public void display() {
        // init all buttons, choosers and text field
        this.setBoxes();

        // put all choosers and buttons in one line
        HBox hbox = new HBox(10, this.fontChooser, this.sizeChooser,
                boldButton, italicButton, underlineButton, strikethroughButton);
        hbox.setStyle("-fx-padding: 10;");

        // put text field in another line
        VBox vbox = new VBox(10, hbox, textField);

        // show dialog
        this.dialog.setTitle("Text Editor");
        this.dialog.getDialogPane().setContent(vbox);
        this.dialog.showAndWait();
    }
}
