package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;

public class TextEditorDialog extends Dialog {

    private PaintPanel paintPanel;
    private Text text;
    private ComboBox fontChooser;
    private ComboBox sizeChooser;
    private ToggleButton boldButton;
    private ToggleButton italicButton;
    private ToggleButton underlineButton;
    private ToggleButton strikethroughButton;

    public TextEditorDialog(Text text, PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
        this.text = text;
        setBoxes();
    }

    private void setBoxes() {

//        // default text font
//        javafx.scene.text.Text text = new javafx.scene.text.Text("Sample Text");
//        text.setFont(javafx.scene.text.Font.font("Microsoft YaHei UI", 20));

        // font chooser comboBox
        this.fontChooser = new ComboBox<>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        this.fontChooser.getItems().addAll(fontNames);
        this.fontChooser.setValue("Microsoft YaHei UI");  // default font
        this.fontChooser.setOnAction(e -> {
            this.text.setFont(javafx.scene.text.Font.font((String) fontChooser.getValue(),
                    this.text.getFont().getSize()));
        });

        // size chooser comboBox
        this.sizeChooser = new ComboBox<>();
        this.sizeChooser.getItems().addAll("8", "9", "10", "11", "12", "14", "16", "18", "20",
                "22", "24", "26", "28", "36", "48", "72");
        this.sizeChooser.setValue("8");  // default size
        this.sizeChooser.setOnAction(e -> {
            this.text.setFont(Font.font(this.text.getFont().getFamily(),
                    Integer.parseInt((String) this.sizeChooser.getValue())));
        });

        // font style buttons
        this.boldButton = new ToggleButton("B");
        boldButton.setStyle("-fx-font-weight: bold;");
        boldButton.setOnAction(e -> {
            if (boldButton.isSelected()) {
                text.setStyle("-fx-font-weight: bold;");
            } else {
                text.setStyle("-fx-font-weight: normal;");
            }
        });

        this.italicButton = new ToggleButton("I");
        italicButton.setStyle("-fx-font-style: italic;");
        italicButton.setOnAction(e -> {
            if (italicButton.isSelected()) {
                text.setStyle("-fx-font-style: italic;");
            } else {
                text.setStyle("-fx-font-style: normal;");
            }
        });

        this.underlineButton = new ToggleButton("U");
        underlineButton.setStyle("-fx-underline: true;");
        underlineButton.setOnAction(e -> {
            if (underlineButton.isSelected()) {
                text.setUnderline(true);
            } else {
                text.setUnderline(false);
            }
        });

        this.strikethroughButton = new ToggleButton("S");
        underlineButton.setStyle("-fx-strikethrough: true;");
        underlineButton.setOnAction(e -> {
            if (underlineButton.isSelected()) {
                text.setStrikethrough(true);
            } else {
                text.setStrikethrough(false);
            }
        });
    }

    public void display() {
        this.setTitle("Text Editor");

        // put all choosers and buttons together
        HBox hbox = new HBox(10, this.fontChooser, this.sizeChooser,
                boldButton, italicButton, underlineButton, strikethroughButton);
        hbox.setStyle("-fx-padding: 10;");

        this.getDialogPane().setContent(hbox);
        this.show();
    }
}
