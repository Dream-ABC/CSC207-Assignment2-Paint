package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;

public class TextEditorDialog extends Dialog {

    private Text text;
    private ComboBox fontChooser;
    private ComboBox sizeChooser;
    private CheckBox bold;
    private CheckBox italic;
    private CheckBox underline;
    private CheckBox strikethrough;

    public TextEditorDialog(Text text) {
        setBoxes();
    }

    private void setBoxes() {

        this.fontChooser = new ComboBox();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        this.fontChooser.setValue(fontNames[0]);  // default font
        this.fontChooser.getItems().addAll(fontNames);

        this.sizeChooser = new ComboBox();
        this.sizeChooser.setValue(12);  // default size
        this.sizeChooser.getItems().addAll(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72);

//        // default text font
//        javafx.scene.text.Text text = new javafx.scene.text.Text("Sample Text");
//        text.setFont(javafx.scene.text.Font.font("Microsoft YaHei UI", 20));

        // font chooser comboBox
        this.fontChooser = new ComboBox<>();
        this.fontChooser.getItems().addAll("Microsoft YaHei UI", "Arial", "Verdana", "Courier New");
        this.fontChooser.setValue("Microsoft YaHei UI");
        this.fontChooser.setOnAction(e -> {
            this.text.setFont(javafx.scene.text.Font.font((String) fontChooser.getValue(), this.text.getFont().getSize()));
        });

        // size chooser comboBox
        this.sizeChooser = new ComboBox<>();
        this.sizeChooser.getItems().addAll("8", "10", "12", "14", "16", "18", "20");
        this.sizeChooser.setValue("8");
        this.sizeChooser.setOnAction(e -> {
            this.text.setFont(Font.font(this.text.getFont().getFamily(), Integer.parseInt((String) this.sizeChooser.getValue())));
        });

        // font style buttons
        ToggleButton boldButton = new ToggleButton("B");
        boldButton.setStyle("-fx-font-weight: bold;");
        boldButton.setOnAction(e -> {
            if (boldButton.isSelected()) {
                text.setStyle("-fx-font-weight: bold;");
            } else {
                text.setStyle("-fx-font-weight: normal;");
            }
        });

        ToggleButton italicButton = new ToggleButton("I");
        italicButton.setStyle("-fx-font-style: italic;");
        italicButton.setOnAction(e -> {
            if (italicButton.isSelected()) {
                text.setStyle("-fx-font-style: italic;");
            } else {
                text.setStyle("-fx-font-style: normal;");
            }
        });

        ToggleButton underlineButton = new ToggleButton("U");
        underlineButton.setStyle("-fx-underline: true;");
        underlineButton.setOnAction(e -> {
            if (underlineButton.isSelected()) {
                text.setUnderline(true);
            } else {
                text.setUnderline(false);
            }
        });

        // 创建一个水平布局
        HBox hbox = new HBox(10, fontComboBox, sizeComboBox, boldButton, italicButton, underlineButton);
        hbox.setStyle("-fx-padding: 10;");
    }

    public void display() {
        this.setTitle("Text Editor");

        HBox hbox = new HBox(10, fontChooser, sizeChooser, bold, italic, underline, strikethrough);
        this.getDialogPane().setContent(hbox);
    }
}
