package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

public class TextEditorDialog {

    private Dialog<Void> dialog;
    private PaintPanel paintPanel;
    private Text displayedText;

    private TextField textField;
    private ComboBox fontChooser;
    private ComboBox sizeChooser;
    private ToggleButton boldButton;
    private ToggleButton italicButton;
    private ToggleButton underlineButton;
    private ToggleButton strikethroughButton;

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

    private void updateFont() {
        this.displayedText.setFont(this.getSelectedFont());
        this.paintPanel.getModel().notifyChange();
    }

    private void setBoxes() {
        // font chooser menu
        this.fontChooser = new ComboBox<>();
        // get all fonts
        String[] fontNames = Font.getFamilies().toArray(new String[0]);
        this.fontChooser.getItems().addAll(fontNames);
        this.fontChooser.setValue(fontNames[0]);  // default font
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
        this.boldButton = new ToggleButton("B");
        boldButton.setStyle("-fx-font-weight: bold;");
        boldButton.setOnAction(e -> {
            this.updateFont();
        });

        this.italicButton = new ToggleButton("I");
        italicButton.setStyle("-fx-font-style: italic;");
        italicButton.setOnAction(e -> {
            this.updateFont();
        });

        this.underlineButton = new ToggleButton("U");
        underlineButton.setStyle("-fx-underline: true;");
        underlineButton.setOnAction(e -> {
            this.displayedText.setUnderline(this.underlineButton.isSelected());
            this.paintPanel.getModel().notifyChange();
        });

        this.strikethroughButton = new ToggleButton("S");
        strikethroughButton.setStyle("-fx-strikethrough: true;");
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

    public Font getSelectedFont() {
        FontWeight weight = boldButton.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = italicButton.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;

        return Font.font((String) this.fontChooser.getValue(), weight, posture,
                Integer.parseInt((String) this.sizeChooser.getValue()));
    }

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
